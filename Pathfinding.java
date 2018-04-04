import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * classe et fenetre principale du projet
 * */
public class Pathfinding extends Fenetre implements MouseListener, MouseMotionListener{
	
	// attributs de la fenetre
    private BufferedImage fond; // image de fond de la fenêtre
    private BufferedImage arrierePlan; // arrière plan du buffer
    
    private Objet[][] quadrillage; // le tableau d'objet représentant le quadrillage de la fenêtre principale
    private PtDepart start; // le point de départ
    private PtArrivee goal; // le point d'arrivée
    private Mur mur; // mur correspondant à un nouveau mur créé
    private Ralentissement ralentisseur; // ralentissement correspondant à un nouveau ralentissement créé
    private Interface interface1; // l'interface permettant d'interagir avec la fenêtre principale
    private Chemin chemin; // Liste de cases représentant le chemin à afficher
    private NoWay noway; // Fenêtre d'erreur en cas d'impossibilité de rejoindre la destination
    private Warning warning; // Fenêtre d'avertissement pour l'algorithme matriciel
	
	private Graphics buffer; // Buffer graphique pour éviter le scintillement de l'affichage

	private boolean ptDepartFixe; // booleen déterminant si le point de départ a été fixé
	private boolean ptArriveeFixe; // booleen déterminant si le point d'arrivée a été fixé
	private boolean cheminAffiche; // booleen déterminant si le chemin est en train d'être affiché ou non
	private boolean parcourueAffiche; // booleen déterminant si les possibilités de chemin sont affichées (mode reflexion uniquement)
	private boolean warningAffiche; // booleen déterminant si la fenêtre warning a été affichée ou non (ne doit s'afficher qu'une seule fois)
	private boolean refreshBoolean; // booleen déterminant si les obstacles doivent être remis à jour (notamment pour un changement de thème)
	
	private boolean DjikstraManhattanEnCours; // booleen déterminant si l'algorithme Dijkstra en mode reflexion est en cours
   	private boolean DjikstraManhattanTermine; // booleen déterminant si l'algorithme Dijkstra en mode reflexion est juste terminé
   	private boolean DjikstraManhattanCompletEnCours; // booleen déterminant si l'algorithme Dijkstra en mode temps est en cours
   	private boolean DjikstraManhattanCompletTermine; // booleen déterminant si l'algorithme Dijkstra en mode temps est juste terminé
   	
   	private boolean AStarEnCours; // booleen déterminant si l'algorithme A* en mode reflexion est en cours
   	private boolean AStarTermine; // booleen déterminant si l'algorithme A* en mode reflexion est juste terminé
   	private boolean AStarCompletEnCours; // booleen déterminant si l'algorithme A* en mode temps est en cours
   	private boolean AStarCompletTermine; // booleen déterminant si l'algorithme A* en mode temps est juste terminé
   	
   	private boolean MatrixCompletEnCours; // booleen déterminant si l'algorithme matriciel est en cours
   	private boolean MatrixCompletTermine; // booleen déterminant si l'algorithme matriciel est juste terminé
   	
   	private long tempsAlgo; // stockera le temps de déroulement de l'algorithme
   	private long debutAlgo; // stockera le temps écoulé depuis le début du programme au lancement de l'algo
   	
   	private Dijkstra dijkstra; // attributs nécessaires pour le fonctionnement de Dijkstra
   	private Arc parcours;
   	
   	private AStar astar; // attributs nécessaires pour le fonctionnement de A*
   	
	private String theme; // Chaine de caractère contenant le thème sélectionné
	private String themePreced; // Chaine contenant le thème lors de la dernière utilisation du timer pour vérifier si le thème a changé
	
	private MatrixPower matrix; // attributs nécessaires pour le fonctionnement de l'algo matriciel
    
    /**
     * Constructeur
     * 
     * @param l Largeur de la fenetre
     * @param h Hauteur de la fenetre
     * */
    public Pathfinding(int l, int h){
		super(l, h); // On crée une fenêtre de taille l*h
		
		theme = "classique"; // on charge le thème classique par défaut
		
		noway = new NoWay(); // on initialise les fenêtres secondaires
		warning = new Warning();
		
		try {
            fond = ImageIO.read(new File(theme+"/fond.png")); // chargement de l'image de fond
        }
        catch(Exception err){
            System.out.println("fichier introuvable !");
            System.exit(0);
        }
        
        // création et initialisation du tableau d'objets (vide au départ)
        quadrillage = new Objet [40][31];
        for (int i = 0; i < quadrillage.length; i ++)
        {
			for (int j = 0; j < quadrillage[0].length; j ++)
			{
				quadrillage[i][j] = null;
			}
		}
		
		//initialisation des paramètres de la fenêtre
		setLayout(null);
		setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//création de la fenêtre d'interface
		interface1 = new Interface(300, 400);
		
		//initialisation du buffer
		arrierePlan = new BufferedImage(LARGEUR_FENETRE,HAUTEUR_FENETRE,BufferedImage.TYPE_INT_RGB);
		buffer = arrierePlan.getGraphics();
		
		//initialisation du timer graphique
		timer = new javax.swing.Timer(TPS_TIMER_MS, this);
		timer.start();
		temps = 0;
		
		//initialisation du timer gérant les différents algorithmes
		int periodeMilliseconde = 30;
		java.util.Timer timerAlgo = new java.util.Timer();
		
		//initialisation de la classe contenant la méthode run executé à chaque top-timer
		class Cadencement extends TimerTask {
            
            public void run () {
				//Si l'algorithme Dijkstra en reflexion est en cours
                if (DjikstraManhattanEnCours) {		
					if (parcours == null) {	// Tant qu'aucun arc décrivant le chemin du point de départ a l'arrivée n'est renvoyé, les itérations du graphe dijkstra s'effectue periodiquement, de façon séquencée par le timer
						parcours = dijkstra.getGraphe().itererGraphe();
						dijkstra.addParcouru(dijkstra.getGraphe().getParcouru());		// On stocke le point nouvellement parcouru pour l'affichage
					} else if (parcours.getOrigine() != null) {		// Quand un chemin est renvoyé : 
						dijkstra.removeLastParcouru();				// on efface l'arrivée des points parcourus pour ne pas effacer l'arrivée du tableau d'objet
						Noeud destination = parcours.getDestination();
						while(parcours.getPrecedent() != null) {		// On parcourt la suite d'arc issu du dernier arc parcouru ...
							parcours = parcours.getPrecedent();
							destination = parcours.getDestination();
							Chemin result = dijkstra.getChemin();
							result.ajoutFin(new Case (destination.getX() * 25, destination.getY() * 25, null));		// ... et on construit le chemin complet
							dijkstra.setChemin(result);
						}
						chemin = dijkstra.getChemin();
						cheminAffiche = true;			 // On considère ensuite le chemin comme prêt à être affiché
						DjikstraManhattanEnCours = false;// Et l'execution de l'algorithme comme terminée
						DjikstraManhattanTermine = true;
					} else {				
						chemin = new Chemin (null);		// Un arc d'origine null est renvoyé par la methode d'itération si aucun chemin n'existe entre le départ et l'arrivée
						noway.setAlwaysOnTop(true);		// On affiche donc la fenêtre associée
						noway.setVisible(true);
						DjikstraManhattanEnCours = false;	// Et l'execution de l'alogirhtme est tout de même terminée
						DjikstraManhattanTermine = true;
					}
				} 
				// Si l'alogirhtme Dijkstra en temps est en cours : on execute de la façon la plus rapide les itérations du graphe
				else if (DjikstraManhattanCompletEnCours) {	
					debutAlgo = System.nanoTime();		// On stocke le temps de départ
					while (parcours == null) {	// On itère jusqu'à ce qu'un chemin soit renvoyé
						parcours = dijkstra.getGraphe().itererGraphe();
					}
					tempsAlgo = (System.nanoTime() - debutAlgo) / 1000000;		// On calcule le temps total d'execution
					if (parcours.getOrigine() != null) {		// On traite comme pour le Dijkstra en reflexion les resultats, sans stocker et afficher les points parcourus
						Noeud destination = parcours.getDestination();
						while(parcours.getPrecedent() != null) {
							parcours = parcours.getPrecedent();
							destination = parcours.getDestination();
							Chemin result = dijkstra.getChemin();
							result.ajoutFin(new Case (destination.getX() * 25, destination.getY() * 25, null));
							dijkstra.setChemin(result);
						}
						chemin = dijkstra.getChemin();
						cheminAffiche = true;
						DjikstraManhattanCompletEnCours = false;
						DjikstraManhattanCompletTermine = true;
					} else {
						chemin = new Chemin (null);
						noway.setAlwaysOnTop(true);
						noway.setVisible(true);
						DjikstraManhattanCompletEnCours = false;
						DjikstraManhattanCompletTermine = true;
					} 
				} 
				// Appliqué si l'algorithme A* en temps en cours
				else if (AStarCompletEnCours) {
					chemin = null;
					debutAlgo = System.nanoTime();		// On stocke le temps de départ
					while (!(astar.getListeOuverte().isEmpty()) && chemin == null) {	// On itere le graphe tant qu'un chemin n'est pas renvoyé ou que toutes possibilités ont été testées
						chemin = astar.iterer();
					}
					tempsAlgo = (System.nanoTime() - debutAlgo) / 1000000;

					if (chemin != null) {
								cheminAffiche = true;	// Si un chemin est renvoyé, on le considère comme affichable
								AStarCompletEnCours = false;// Et l'execution de l'alogirhtme est terminée
								AStarCompletTermine = true;
						
					} else {					// Sinon, on affiche la fenêtre associée à une absence de solution du probleme
						chemin = new Chemin(null);
						noway.setAlwaysOnTop(true);
						noway.setVisible(true);
						AStarCompletEnCours = false;	// Et l'execution algorithme est tout de même terminée
						AStarCompletTermine = true;
					}
				} 
				// Si l'algorithme A* en reflexion est en cours
				else if (AStarEnCours) {
					if (!(astar.getListeOuverte().isEmpty()) && chemin == null) {	// Tant que l'iteration est possible ou qu'on a trouvé un chemin, on itere le graphe
						chemin = astar.iterer();
					} else if (chemin == null){		// Si aucun chemin n'existe, on affichage la fenetre associée
						chemin = new Chemin(null);
						noway.setAlwaysOnTop(true);
						noway.setVisible(true);
						AStarEnCours = false;	// Et l'execution de l'algorithme est terminée
						AStarTermine = true;
					} else {			// Si un chemin existe et qu'il a été trouvé, 
						cheminAffiche = true;	// on considère ce chemin comme affichable
						AStarEnCours = false;
						AStarTermine = true;	// Et l'execution de l'algorithme est terminée
						
					}
				}
				// Si l'algorithme Matriciel est en cours
				else if (MatrixCompletEnCours){
					debutAlgo = System.currentTimeMillis();	// On stocke le temps de départ
					chemin = matrix.solve();	// On résout le probleme grâce à l'alogirhtme
					cheminAffiche = true;	// On affiche le chemin
					MatrixCompletEnCours = false;
					MatrixCompletTermine = true;	// On considère l'execution de l'algorithme comme terminée
					tempsAlgo = System.currentTimeMillis() - debutAlgo; // Et on calcule le temps total d'execution
				}
            }
        }

		timerAlgo.schedule(new Cadencement(), 1000, periodeMilliseconde);
		
		// initialisation des booleens
		ptDepartFixe = false;
		ptArriveeFixe = false;
		cheminAffiche = false;
		warningAffiche = false;
		
		DjikstraManhattanEnCours = false;
		DjikstraManhattanTermine = false;
		DjikstraManhattanCompletEnCours = false;
		DjikstraManhattanCompletTermine = false;
		
		AStarEnCours = false;
		AStarTermine = false;
		AStarCompletEnCours = false;
		AStarCompletTermine = false;
		
		MatrixCompletEnCours = false;
		MatrixCompletTermine = false;
		
		chemin = null;
		dijkstra = null;
	}
	
	/**
	 * Met a jour la fenetre principale
	 * 
	 * @param g Objet graphique de la fenetre
	 * */
	public void paint(Graphics g){
		if (buffer != null)
		{
			buffer.drawImage(fond, 0, 0, this); // chargement de l'image de fond dans le buffer
			for (int i = 0; i < quadrillage.length; i ++)
			{
				for (int j = 0; j < quadrillage[0].length; j ++)
				{
					if (quadrillage[i][j] != null) // si la case du quadrillage est non vide ...
					{
						quadrillage[i][j].dessine(buffer, this); // ... chargement de l'objet contenu dans le buffer
					}
				}
			}
		g.drawImage(arrierePlan, 0, 0, this); // chargement du buffer sur l'écran
		}
	}
	/**
	 * Methode appelee a chaque tick du timer
	 * 
	 * @param e Evenement caracteristique de l'action effectuee
	 * */
	public void actionPerformed(ActionEvent e){
		temps += TPS_TIMER_MS;
		// Selection du thème
		if (interface1.getThemeClassiqueSelected()){
			theme = "classique";
		}
		else if (interface1.getThemePirateSelected()){
			theme = "pirate";
		}
		else if (interface1.getThemeEspaceSelected()){
			theme = "espace";
		}
		if (!(theme.equals(themePreced))){ // Si le nouveau thème est différent du précédent ...
			refreshBoolean = true; // on recharge les obstacles au prochain refresh
		}
		themePreced = theme; // themePreced est remis à jour
		if (interface1.getAlgoSelected() == 2 && warningAffiche == false){ // Si on sélectionne l'algorithme matriciel et que c'est la première fois ...
			warning.setVisible(true); // ... on affiche la fenêtre d'avertissement
			warningAffiche = true;
		}
		if (interface1.getClearAsked() && !DjikstraManhattanEnCours && !DjikstraManhattanCompletEnCours && !AStarEnCours && !AStarCompletEnCours && !MatrixCompletEnCours) // si une demande de clear a été faite et que aucun algorithme n'est en cours ...
		{
			ptDepartFixe = false; // ... les pts de départ et d'arrivée ne sont plus fixés ...
			ptArriveeFixe = false;
			if (cheminAffiche) // si un chemin est affiché ...
				clearCheminFromQuadrillage(); // ... on efface le chemin du quadrillage
			if (parcourueAffiche){ // si une possibilité de chemin est affichée ...
				if (DjikstraManhattanTermine) {
					clearParcouruFromQuadrillage(dijkstra); // ... on l'efface du quadrillage
					dijkstra = null;
				} else if (AStarTermine) {
					clearParcouruFromQuadrillage(astar); // ... on l'efface du quadrillage
					astar = null;
				}
			}
			for (int i = 0; i < quadrillage.length; i ++)
			{
				for (int j = 0; j < quadrillage[0].length; j ++)
				{
					quadrillage[i][j] = null; // ... et on vide le quadrillage
				}
			}
			chemin = null;
			interface1.setClearAsked(false); // le clear a été effectué, on peut en redemander un nouveau
		}
		else if (interface1.getClearAsked() && (DjikstraManhattanEnCours || DjikstraManhattanCompletEnCours || AStarEnCours || AStarCompletEnCours || MatrixCompletEnCours)){ // si une demande de clear a été faite et qu'un algorithme est en cours ...
			interface1.setClearAsked(false); // impossible de clear mais possibilité de la faire plus tard
		}
		if (interface1.getGoAsked() && !DjikstraManhattanEnCours && !DjikstraManhattanCompletEnCours && !AStarEnCours && !AStarCompletEnCours && !MatrixCompletEnCours) // si on veut executer un algorithme et que aucun algorithme n'est en cours ...
		{
			if (cheminAffiche) // si un chemin est affiché ...
			{
				clearCheminFromQuadrillage(); // ... on efface le chemin du quadrillage
			}
			if (parcourueAffiche) { // si une possibilité de chemin est affichée ...
				if (DjikstraManhattanTermine) {
					clearParcouruFromQuadrillage(dijkstra); // ... on l'efface du quadrillage
				}
				else if (AStarTermine){
					clearParcouruFromQuadrillage(astar); // ... on l'efface du quadrillage
				}
			}
			if (interface1.getAfficheReflexionSelected()) { // Si le mode reflexion a été sélectionné ...
				if (ptDepartFixe && ptArriveeFixe) // ... on vérifie d'abord que les points de départ et d'arrivée sont bien fixés pour pouvoir calculer le chemin
				{
					switch(interface1.getAlgoSelected()) // on regarde ensuite quel algorithme a été sélectionné
					{
					case 0:
						parcours = null;
						dijkstra = new Dijkstra(quadrillage); // on initialise l'algorithme dijkstra
						DjikstraManhattanEnCours = true;
					break;
					case 1:
						astar = new AStar(quadrillage, start, goal); // on initialise l'algorithme A*
						AStarEnCours = true;
						chemin = null;
					break;
					default:
					break;
					}
				}
			} else if (interface1.getAfficheTempsSelected()) { // Si le mode temps a été sélectionné ...
				if (ptDepartFixe && ptArriveeFixe) // ... on vérifie d'abord que les points de départ et d'arrivée sont bien fixés pour pouvoir calculer le chemin
				{
					switch(interface1.getAlgoSelected()) // on regarde ensuite quel algorithme a été sélectionné
					{
					case 0:
						parcours = null;
						dijkstra = new Dijkstra(quadrillage); // on initialise l'algorithme dijkstra
						DjikstraManhattanCompletEnCours = true;
						
					break;
					case 1:
						astar = new AStar(quadrillage, start, goal); // on initialise l'algorithme A*
						AStarCompletEnCours = true;
					break;
					case 2:
						matrix = new MatrixPower(quadrillage, start, goal); // on initialise l'algorithme matriciel
						MatrixCompletEnCours = true;
					break;
					default:
					break;
					}
				}
			}
			interface1.setGoAsked(false); // la commande go est réinitialisée
		}
		else if (interface1.getGoAsked() && (DjikstraManhattanEnCours || DjikstraManhattanCompletEnCours || AStarEnCours || AStarCompletEnCours || MatrixCompletEnCours)){ // si on veut executer un algorithme et qu'un algorithme est en cours ...
			interface1.setGoAsked(false); // la commande go est réinitialisée
		}
		if(interface1.getRefreshAsked()==true){ // Demande de refresh ...
			interface1.setRefreshAsked(false);
			refreshBoolean=true; // ... boolean pour refresh les obstacles
		}
		if (interface1.getAlgoSelected() == 2){
			for (int i = 0; i < quadrillage.length; i ++)
			{
				for (int j = 0; j < quadrillage[0].length; j ++)
				{ // si l'algorithme matriciel est selectionne, on enleve les ralentissements existants
					if (quadrillage[i][j] instanceof Ralentissement){
						quadrillage[i][j] = null;
					}
				}
			}
		}
		refresh(); // s'adapte aux changements de thème
		repaint(); // met à jour la fenêtre en fonction du contenu de quadrillage
	}
	
	/**
	 * Remet a jour l'affichage en fonction du theme selectionne
	 * */
	public void refresh(){
		int nbCoups = 0; // stockera le nombre de coups nécessaires afin d'arriver à destination
		if (ptDepartFixe){ // création d'un nouveau point de départ au même endroit (pour peu qu'il ait été créé)
			int x = start.getX();
			int y = start.getY();
			start = new PtDepart(x, y, theme);
			majQuadrillage(x/25, y/25, start);
		}
		if (ptArriveeFixe){ // création d'un nouveau point d'arrivée au même endroit (pour peu qu'il ait été créé)
			int x = goal.getX();
			int y = goal.getY();
			goal = new PtArrivee(x, y, theme);
			majQuadrillage(x/25, y/25, goal);
		}
		if (cheminAffiche){
			int i = 1;
			while (chemin.getElement(i) != null)
			{
				chemin.getElement(i).setImage(theme); // remet à jour le chemin avec sa nouvelle image
				i++;
			}
			nbCoups = i;
			setCheminToQuadrillage();
		}
		for (int i = 0; i < quadrillage.length; i ++)
		{
			for (int j = 0; j < quadrillage[0].length; j ++)
			{ // remet à jour les différents objets avec les nouvelles images
				if (quadrillage[i][j] instanceof Ralentissement){
					quadrillage[i][j] = new Ralentissement(i*25, j*25, theme);
				}
				else if (quadrillage[i][j] instanceof Mur && refreshBoolean){
					quadrillage[i][j] = new Mur(i*25, j*25, theme, quadrillage);
				}
				else if (quadrillage[i][j] instanceof caseParcourue){
					quadrillage[i][j] = new caseParcourue(i*25, j*25, theme);
				}
			}
		}
		refreshBoolean=false;
		 // met à jour le quadrillage avec ce que renvoient les algorithmes en mode reflexion
		if (DjikstraManhattanEnCours) {
			setParcouruToQuadrillage(dijkstra);
		} else if (AStarEnCours) {
			setParcouruToQuadrillage(astar);
		}
		if (DjikstraManhattanCompletTermine || AStarCompletTermine || MatrixCompletTermine) { // si les algos en mode temps sont terminés ...
			interface1.setTempsAlgo(tempsAlgo, nbCoups); // ... on affiche le temps necessaire et le nombre de coups
		}
		try{
			fond = ImageIO.read(new File(theme+"/fond.png")); // on charge le nouveau fond
		}
		catch(Exception err){
            System.out.println("fichier introuvable !");
            System.exit(0);
        }
	}
	
	public void mouseExited(MouseEvent event){
		
	}
	
	public void mouseEntered(MouseEvent event){
		
	}
	
	public void mouseReleased(MouseEvent event){
		
	}
	
	/**
	 * Appelee au clic de la souris
	 * */
	public void mousePressed(MouseEvent event){
		if (!DjikstraManhattanEnCours && !DjikstraManhattanCompletEnCours && !AStarEnCours && !AStarCompletEnCours && !MatrixCompletEnCours) // on effectue une action avec la souris dans la fenêtre principale si aucun algorithme n'est en cours pour éviter d'interférer avec son déroulement
		{
			afficherClic(event); // gestion du clic de la souris
		}
		
	}
	
	public void mouseClicked(MouseEvent event){
		
	}
	
	public void mouseMoved(MouseEvent event){
		
	}
	
	/**
	 * Appelee quand le clic de la souris est maintenu enfonce puis deplacee
	 * */
	public void mouseDragged(MouseEvent event){
		if (!DjikstraManhattanEnCours && !DjikstraManhattanCompletEnCours && !AStarEnCours && !AStarCompletEnCours && !MatrixCompletEnCours)
		{
			afficherClic(event); // gestion du clic de la souris
		}
	}
	
	/**
	 * Envoie le chemin calcule dans le quadrillage en vue d'etre affiche par la suite
	 * */
	public void setCheminToQuadrillage(){
		int i = 1;
		while (chemin.getElement(i) != null)
		{
			quadrillage[chemin.getElement(i).getX()/25][chemin.getElement(i).getY()/25] = chemin.getElement(i);
			i++;
		}
		cheminAffiche = true;
	}
	
	/**
	 * Efface le chemin du quadrillage
	 * */
	public void clearCheminFromQuadrillage(){
		int i = 1;
			while (chemin.getElement(i) != null)
			{
				quadrillage[chemin.getElement(i).getX()/25][chemin.getElement(i).getY()/25] = null;
				i++;
			}
			chemin = null;
			cheminAffiche = false;
	}
	
	/**
	 * Met a jour une case du quadrillage avec un objet envoye en parametre
	 * 
	 * @param i Premier indice de la case a mettre a jour
	 * @param j Deuxième indice de la case a mettre a jour
	 * @param o Objet a placer a cet emplacement
	 * */
	public void majQuadrillage(int i, int j, Objet o){
		quadrillage[i][j] = o;
	}
	
	/**
	 * Dessine sur la fenetre principale en fonction de la selection de l'interface et des clics de l'utilisateur
	 * 
	 * @param event Evenement caracteristique de la souris
	 * */
	public void afficherClic(MouseEvent event){
		if (cheminAffiche) // si un chemin est affiché ...
		{
			clearCheminFromQuadrillage(); // ... on efface le chemin du quadrillage
		} 
		if (parcourueAffiche) { // si une possibilité de chemin est affichée ...
			if (DjikstraManhattanTermine) {
				clearParcouruFromQuadrillage(dijkstra); // ... on l'efface du quadrillage
				DjikstraManhattanTermine = false;
				dijkstra = null;
			} else if (AStarTermine) {
				clearParcouruFromQuadrillage(astar); // ... on l'efface du quadrillage
				AStarTermine = false;
				astar = null;
			}
		}
		if (isMouseOnCurrentWindow(event) && !(interface1.getAfficheReflexionSelected() && interface1.getAlgoSelected() == 2)) // si la souris n'est pas en dehors de la fenêtre et si l'algorthme matrice n'est pas selectionné en mode reflexion, on peut interagir avec la fenêtre
		{
			int x = 0;
			int y = 0;
			//Formules permettant de définir les coordonnées d'un coin supérieur gauche d'une case en fonction de celles de la souris au moment du clic
			x = event.getX() - (event.getX()%25);
			y = (event.getY()-25) - ((event.getY()-25)%25);
			switch (interface1.getSelection()) // on teste quel type d'objet a été sélectionné
			{
				case 1: // si c'est un pt de départ
					if (ptDepartFixe == false) // on regarde si le pt de départ a déjà été fixé
					{
						ptDepartFixe = true;
					}
					else if (ptDepartFixe)
					{
						quadrillage[start.getX()/25][start.getY()/25] = null; // si le point de départ avait déjà été défini, on le retire de son ancien emplacement
					}
					start = new PtDepart(x, y, theme); // création du nouveau point de départ
					if (quadrillage[x/25][y/25] instanceof PtArrivee) // on teste si l'on ne remplace pas un point d'arrivée ...
					{
						ptArriveeFixe = false; // ... si c'est le cas, on le définit comme non fixé
					}
					quadrillage[x/25][y/25] = start; // on place le point de départ sur le quadrillage
				break;
				case 2: // si c'est un pt d'arrivée
					if (ptArriveeFixe == false) // on regarde si le pt d'arrivée a déjà été fixé
					{
						ptArriveeFixe = true;
					}
					else if (ptArriveeFixe)
					{
						quadrillage[goal.getX()/25][goal.getY()/25] = null; // si le point d'arrivée avait déjà été défini, on le retire de son ancien emplacement
					}
					goal = new PtArrivee(x, y, theme); // création du nouveau point d'arrivée
					if (quadrillage[x/25][y/25] instanceof PtDepart) // on teste si l'on ne remplace pas un point de départ ...
					{
						ptDepartFixe = false; // ... si c'est le cas, on le définit comme non fixé
					}
					quadrillage[x/25][y/25] = goal; // on place le point d'arrivée sur le quadrillage
				break;
				case 3: // si c'est un mur
					mur = new Mur(x, y, theme, quadrillage); // on créé un nouveau mur
					if (quadrillage[x/25][y/25] instanceof PtDepart) // on teste si l'on ne remplace pas un point de départ ...
					{
						ptDepartFixe = false; // ... si c'est le cas, on le définit comme non fixé
					}
					else if (quadrillage[x/25][y/25] instanceof PtArrivee) // on teste si l'on ne remplace pas un point d'arrivée ...
					{
						ptArriveeFixe = false; // ... si c'est le cas, on le définit comme non fixé
					}
					quadrillage[x/25][y/25] = mur; // on place le mur sur le quadrillage
					if (!(theme.equals("classique")) && !(theme.equals("espace"))){ // si le thème n'est pas le thème classique ou spatial
						// on remet à jour les éventuels murs adjacents pour garder une cohérence dans l'affichage des obstacles
						if (x/25 > 0){
							if (quadrillage[(x-25)/25][y/25] instanceof Mur){
								quadrillage[(x-25)/25][y/25] = new Mur(x-25, y, theme, quadrillage);
							}
						}
						if (y/25 > 0){
							if (quadrillage[x/25][(y-25)/25] instanceof Mur){
								quadrillage[x/25][(y-25)/25] = new Mur(x, y-25, theme, quadrillage);
							}
						}
						if (x/25 < quadrillage.length-1){
							if (quadrillage[(x+25)/25][y/25] instanceof Mur){
								quadrillage[(x+25)/25][y/25] = new Mur(x+25, y, theme, quadrillage);
							}
						}
						if (y/25 < quadrillage[0].length-1){
							if (quadrillage[x/25][(y+25)/25] instanceof Mur){
								quadrillage[x/25][(y+25)/25] = new Mur(x, y+25, theme, quadrillage);
							}
						}
					}
				break;
				case 4: // si c'est un ralentissement
					ralentisseur = new Ralentissement(x, y, theme); // on créé un nouveau ralentissement
					if (quadrillage[x/25][y/25] instanceof PtDepart) // on teste si l'on ne remplace pas un point de départ ...
					{
						ptDepartFixe = false; // ... si c'est le cas, on le définit comme non fixé
					}
					else if (quadrillage[x/25][y/25] instanceof PtArrivee) // on teste si l'on ne remplace pas un point d'arrivée ...
					{
						ptArriveeFixe = false; // ... si c'est le cas, on le définit comme non fixé
					}
					quadrillage[x/25][y/25] = ralentisseur; // on place le ralentissement sur le quadrillage
				break;
				case 5: // si on veut enlever un onjet du quadrillage
					boolean nouveauxMurs = false; // booleen servant à mettre à jour les murs adjacents à un mur retiré
					if (quadrillage[x/25][y/25] instanceof PtDepart) // si on supprime un pt de départ
					{
						ptDepartFixe = false; // on le définit comme non fixé
					}
					else if (quadrillage[x/25][y/25] instanceof PtArrivee) // si on supprime un pt d'arrivée
					{
						ptArriveeFixe = false; // on le définit comme non fixé
					}
					else if (quadrillage[x/25][y/25] instanceof Mur) // si on supprime un mur
					{
						nouveauxMurs = true; // on peut remettre à jour ses voisins
					}
					quadrillage[x/25][y/25] = null; // on supprime l'objet de l'emplacement selectionné
					if (nouveauxMurs && (!(theme.equals("classique")))){ // si on a supprimé un mur et que le thème n'est pas classique
						// on met à jour les murs adjacents au mur supprimé afin de garder une cohérence dans l'affichage des obstacles
						if (x/25 > 0){
							if (quadrillage[(x-25)/25][y/25] instanceof Mur){
								quadrillage[(x-25)/25][y/25] = new Mur(x-25, y, theme, quadrillage);
							}
						}
						if (y/25 > 0){
							if (quadrillage[x/25][(y-25)/25] instanceof Mur){
								quadrillage[x/25][(y-25)/25] = new Mur(x, y-25, theme, quadrillage);
							}
						}
						if (x/25 < quadrillage.length-1){
							if (quadrillage[(x+25)/25][y/25] instanceof Mur){
								quadrillage[(x+25)/25][y/25] = new Mur(x+25, y, theme, quadrillage);
							}
						}
						if (y/25 < quadrillage[0].length-1){
							if (quadrillage[x/25][(y+25)/25] instanceof Mur){
								quadrillage[x/25][(y+25)/25] = new Mur(x, y+25, theme, quadrillage);
							}
						}
					}
					cheminAffiche = false;
				break;
				default:
				break;
			}
		}
	}
	
	/**
	 * Place dans le quadrillage les possibilites de chemin calculees par l'algorithme Dijkstra
	 * 
	 * @param algo Objet Dijkstra representant l'algorithme en cours d'execution
	 * */
	public void setParcouruToQuadrillage (Dijkstra algo) {
		if (algo.pointsParcourus.size() > 0) {
			for (int i = 0; i < algo.pointsParcourus.size(); i++) {
				Noeud parcouru = (Noeud)algo.pointsParcourus.get(i);
				this.quadrillage[parcouru.getX()][parcouru.getY()] = new caseParcourue (parcouru.getX() * 25, parcouru.getY() * 25, theme);
			}
			parcourueAffiche = true;
		}
    }
    
    /**
     * Place dans le quadrillage les possibilites de chemin calculees par l'algorithme A*
     * 
     * @param algo Objet AStar representant l'algorithme en cours d'execution
     * */
    public void setParcouruToQuadrillage (AStar algo) {
		if (!(algo.getListeFermee().isEmpty())) {
			for (int i = 1; i < algo.getListeFermee().size(); i++) {
				Noeud parcouru = (Noeud)algo.getListeFermee().get(i);
				this.quadrillage[parcouru.getX()][parcouru.getY()] = new caseParcourue (parcouru.getX() * 25, parcouru.getY() * 25, theme);
			}
			parcourueAffiche = true;
		}
    }
    /**
     * Enleve de l'affichage les possibilites de chemin calculees par l'algorithme Dijkstra
     * 
     * @param algo Objet Dijkstra representant l'algorithme en cours d'execution
     * */
    public void clearParcouruFromQuadrillage (Dijkstra algo) {
        for (int i = 0; i < algo.pointsParcourus.size(); i++) {
            Noeud parcouru = (Noeud)algo.pointsParcourus.get(i);
            this.quadrillage[parcouru.getX()][parcouru.getY()] = null;
        }
        parcourueAffiche = false;
    }
    
    /**
     * Enleve de l'affichage les possibilites de chemin calculees par l'algorithme A*
     * 
     * @param algo Objet AStar representant l'algorithme en cours d'execution
     * */
    public void clearParcouruFromQuadrillage (AStar algo) {
        for (int i = 1; i < algo.getListeFermee().size(); i++) {
            Noeud parcouru = (Noeud)algo.getListeFermee().get(i);
            this.quadrillage[parcouru.getX()][parcouru.getY()] = null;
        }
        parcourueAffiche = false;
    }
	
	/**
	 * Methode principale du programme
	 * 
	 * @param args Parametres de la console
	 * */
	public static void main (String[] args){
		new Pathfinding(1000, 800);
	}
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenetre permettant d'interagir avec la fenetre principale
 * */
public class Interface extends Fenetre{
    
    private JButton depart; // Bouton de selection du point de départ
    private JButton arrivee; // Bouton de selection du point d'arrivée
    private JButton obstacle; // Bouton de selection des murs
    private JButton ralentissement; // Bouton de selection des ralentissements
    private JButton remove; // Bouton de selection de la "gomme"
	private JButton go; // Bouton permettant de lancer un algorithme
	private JButton clear; // Bouton permettant de vider le contenu de la fenêtre principale
	private JButton refresh; // Bouton permettant de rafraichir l'affichage et de charger de nouveaux obstacles aléatoires (sauf en classique)
	
	private JRadioButton afficheTemps; // Bouton à cocher permettant de sélectionner le mode temps
	private JRadioButton afficheReflexion; // Bouton à cocher permettant de sélectionner le mode reflexion
	private JRadioButton themeClassique; // Bouton à cocher permettant de sélectionner le thème classique
	private JRadioButton themePirate; // Bouton à cocher permettant de sélectionner le thème pirate
	private JRadioButton themeEspace; // Bouton à cocher permettant de sélectionner le thème espace
	
	private ButtonGroup groupe; // Groupe de boutons regroupant afficheTemps et afficheReflexion (deux boutons dans un même groupe ne peuvent pas être cochés en même temps)
	private ButtonGroup groupeTheme; // Groupe de boutons regroupant themeClassique, themePirate, themePlaine et themeIce
    
    private JComboBox FenetreSelection; // Fenêtre de selection de l'algorithme
    
    private JLabel affichage; // JLabel contenant l'affichage du temps et du nombre de coups nécessaires
    private JLabel theme; // JLabel contenant l'entête de sélection du thème
    
    private JPanel conteneurBoutons; // conteneur regroupant tous les boutons d'interface avec le fenêtre principale
    private JPanel conteneurBoutons2; // conteneur regroupant les boutons go et clear réalisant des actions particulières
    private JPanel conteneurChoix; // conteneur avec la fenêtre de selection de l'algorithme
    private JPanel conteneurAffichage; // conteneur avec le JLabel affichage
    private JPanel conteneurCheckBoxes; // conteneur regroupant les boutons de selection du mode de fonctionnement des algorithmes
    private JPanel conteneurThemes; // conteneur regroupant les boutons de selection du thème
    
    // création de différents layouts pour les conteneurs et la fenêtre principale
    private GridLayout layout;
    private GridLayout layout3;
    private GridLayout layout4;
    
    private FlowLayout layout2;
    
    // création de différentes couleurs pour communiquer avec l'utilisateur dans l'interface
    private Color defaut;
    private Color select;
    private Color colorBoutonGo;
    private Color colorBoutonClear;
    private Color notEnable;
    private Color colorBoutonRefresh;
    
    private static String[] choix; // tableau de chaine de caractères contenant la liste des algorithmes disponibles pour la JComboBox
    
    private int selection; // représente le bouton sélectionné (entre point de départ, point d'arrivée, mur, ...)
    private int nbDeplacements; // nb de coups nécessaires à l'algorithme pour aller à sa destination
    
    private boolean clearAsked; // booleen indiquant si un clear a été demandé par l'utilisateur
    private boolean goAsked; // booleen indiquant si l'utilisateur souhaite lancer un algorithme
    private boolean refreshAsked; // booleen indiquant si un refresh a été demandé par l'utilisateur
    
    private long tempsAlgo; // le temps mis par l'algorithme pour calculer le chemin jusqu'à sa destination
    
    /**
     * Constructeur
     * 
     * @param l Largeur de la fenetre
     * @param h Hauteur de la fenetre
     * */
    public Interface(int l, int h)
    {
		// création de la fenêtre
		super(l, h);
		
		// initialisation des layouts
		layout = new GridLayout(0,2,10,10);
		layout2 = new FlowLayout();
		layout3 = new GridLayout(0,2,0,0);
		layout4 = new GridLayout(0,2,10,10);
		
		// initialisation des choix possibles
		choix = new String[3];
		choix[0] = "algorithme Djikstra";
		choix[1] = "algorithme A*";
		choix[2] = "algorithme matrices";
		
		// création des JPanels
		conteneurBoutons = new JPanel();
		conteneurBoutons2 = new JPanel();
		conteneurChoix = new JPanel();
		conteneurAffichage = new JPanel();
		conteneurCheckBoxes = new JPanel();
		conteneurThemes = new JPanel();
		
		// affectation des layouts désirés à chaque JPanel et à la fenêtre
		conteneurBoutons.setLayout(layout);
		conteneurBoutons2.setLayout(layout4);
		conteneurChoix.setLayout(layout2);
		conteneurCheckBoxes.setLayout(layout);
		conteneurThemes.setLayout(layout3);
		setLayout(layout2);
		
		
		// initialisation des couleurs
		select = new Color(0, 100, 255);
		defaut = new Color(200, 200, 200);
		colorBoutonGo = new Color(0, 255, 0);
		colorBoutonClear = new Color(255, 10, 10);
		notEnable = new Color(255, 0, 0);
		colorBoutonRefresh = new Color(255, 255, 0);
		
		// création des boutons
		depart  = new JButton("Depart");
		arrivee  = new JButton("Arrivee");
		obstacle  = new JButton("Obstacle");
		ralentissement = new JButton("Ralentissement");
		remove = new JButton("Remove");
		go = new JButton("Go");
		clear = new JButton("Clear");
		refresh= new JButton("Change Sprites");
		
		// création et initialisation de la JComboBox
		FenetreSelection = new JComboBox(choix);
		
		// création et initialisation des JLabel
		affichage = new JLabel("<html>temps necessaire :  <br>nb de coups :   </html>");
		theme = new JLabel("Selection du theme : ");
		
		// création et initialisation des JRadioButton
		afficheTemps = new JRadioButton("temps", true);
		afficheReflexion = new JRadioButton("reflexion");
		themeClassique = new JRadioButton("classique", true);
		themePirate = new JRadioButton("pirate");
		themeEspace = new JRadioButton("espace");
		
		// création des groupes de JRadioButton et affectation des JRadionButton à son groupe
		groupe = new ButtonGroup();
		groupe.add(afficheTemps);
		groupe.add(afficheReflexion);
		
		groupeTheme = new ButtonGroup();
		groupeTheme.add(themeClassique);
		groupeTheme.add(themePirate);
		groupeTheme.add(themeEspace);
		
		// affectation des couleurs aux boutons
		depart.setBackground(defaut);
		arrivee.setBackground(defaut);
		obstacle.setBackground(defaut);
		ralentissement.setBackground(defaut);
		remove.setBackground(defaut);
		go.setBackground(colorBoutonGo);
		clear.setBackground(colorBoutonClear);
		refresh.setBackground(colorBoutonRefresh);
		
		// ajout des différents écouteurs
		depart.addActionListener(this);
		arrivee.addActionListener(this);
		obstacle.addActionListener(this);
		ralentissement.addActionListener(this);
		remove.addActionListener(this);
		go.addActionListener(this);
		clear.addActionListener(this);
		afficheTemps.addActionListener(this);
		afficheReflexion.addActionListener(this);
		refresh.addActionListener(this);
		
		// affectation des différents éléments à leurs conteneurs respectifs
		conteneurBoutons.add(depart);
		conteneurBoutons.add(arrivee);
		conteneurBoutons.add(obstacle);
		conteneurBoutons.add(ralentissement);
		conteneurBoutons.add(remove);
		
		conteneurBoutons2.add(go);
		conteneurBoutons2.add(clear);
		conteneurBoutons2.add(refresh);
		conteneurChoix.add(FenetreSelection);
		conteneurCheckBoxes.add(afficheTemps);
		conteneurCheckBoxes.add(afficheReflexion);
		conteneurAffichage.add(affichage);
		conteneurThemes.add(theme);
		conteneurThemes.add(themeClassique);
		conteneurThemes.add(themePirate);
		conteneurThemes.add(themeEspace);
		add(conteneurBoutons);
		add(conteneurBoutons2);
		add(conteneurChoix);
		add(conteneurCheckBoxes);
		add(conteneurAffichage);
		add(conteneurThemes);
		
		// initialisation des paramètres de la fenêtre
		setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Interface pathfinding");
		setVisible(true);
		
		selection = 0; // on ne selectionne rien par défaut
		nbDeplacements = 0;
		
		clearAsked = false;
		goAsked = false;
		
		tempsAlgo = 0;
	}
	
	/**
	 * Methode appelee a chaque tick du timer
	 * 
	 * @param event Evenement caracteristique de l'action effectuee
	 * */
	public void actionPerformed(ActionEvent event){
		temps += TPS_TIMER_MS;
		if (event.getSource() == depart && selection != 1) // si on clique sur départ et si il n'est pas déjà sélectionné
		{
			// les autres boutons reprennent leur couleur normale	
			arrivee.setBackground(defaut);
			obstacle.setBackground(defaut);
			if (FenetreSelection.getSelectedIndex() == 2){ // cas particulier du ralentissement indisponible en mode reflexion pour l'algorithme matriciel
				ralentissement.setBackground(notEnable);
			}
			else
				ralentissement.setBackground(defaut);
			remove.setBackground(defaut);
			depart.setBackground(select); // le bouton sélectionné prend une couleur différente
			selection = 1;
		}
		else if (event.getSource() == arrivee && selection != 2) // si on clique sur arrivee et si il n'est pas déjà sélectionné
		{
			// les autres boutons reprennent leur couleur normale
			depart.setBackground(defaut);
			obstacle.setBackground(defaut);
			if (FenetreSelection.getSelectedIndex() == 2){ // cas particulier du ralentissement indisponible en mode reflexion pour l'algorithme matriciel
				ralentissement.setBackground(notEnable);
			}
			else
				ralentissement.setBackground(defaut);
			remove.setBackground(defaut);
			arrivee.setBackground(select); // le bouton sélectionné prend une couleur différente
			selection = 2;
		}
		else if (event.getSource() == obstacle && selection != 3) // si on clique sur mur et si il n'est pas déjà sélectionné
		{
			// les autres boutons reprennent leur couleur normale
			depart.setBackground(defaut);
			arrivee.setBackground(defaut);
			if (FenetreSelection.getSelectedIndex() == 2){ // cas particulier du ralentissement indisponible en mode reflexion pour l'algorithme matriciel
				ralentissement.setBackground(notEnable);
			}
			else
				ralentissement.setBackground(defaut);
			remove.setBackground(defaut);
			obstacle.setBackground(select); // le bouton sélectionné prend une couleur différente
			selection = 3;
		}
		else if (event.getSource() == ralentissement && selection != 4 && FenetreSelection.getSelectedIndex() != 2) // si on clique sur mur et si il n'est pas déjà sélectionné et si l'algorithme matriciel n'est pas sélectionné
		{
			// les autres boutons reprennent leur couleur normale
			depart.setBackground(defaut);
			arrivee.setBackground(defaut);
			obstacle.setBackground(defaut);
			remove.setBackground(defaut);
			ralentissement.setBackground(select); // le bouton sélectionné prend une couleur différente
			selection = 4;
		}
		else if (event.getSource() == remove && selection != 5) // si on clique sur remove et si il n'est pas déjà sélectionné
		{
			// les autres boutons reprennent leur couleur normale
			depart.setBackground(defaut);
			arrivee.setBackground(defaut);
			obstacle.setBackground(defaut);
			if (FenetreSelection.getSelectedIndex() == 2){ // cas particulier du ralentissement indisponible en mode reflexion pour l'algorithme matriciel
				ralentissement.setBackground(notEnable);
			}
			else
				ralentissement.setBackground(defaut);
			remove.setBackground(select); // le bouton sélectionné prend une couleur différente
			selection = 5;
		}
		else if (event.getSource() == go && goAsked == false) // si on clique sur go
		{
			goAsked = true;
		}
		else if (event.getSource() == clear && clearAsked == false) // si on clique sur clear
		{
			clearAsked = true;
		}
		else if (event.getSource() == refresh && refreshAsked == false) // si on clique sur refresh
		{
			refreshAsked = true;
		}
		if (FenetreSelection.getSelectedIndex() == 2){
			ralentissement.setBackground(notEnable);
		}
	}
	
	/**
	 * Indique le bouton selectionne
	 * 
	 * @return Indice du bouton selectionne dans la partie superieure de la fenetre
	 * */
	public int getSelection()
	{
		return selection;
	}
	
	/**
	 * Indique si l'utilisateur veut lancer un algorithme
	 * 
	 * @return true si on a clique sur go
	 * */
	public boolean getGoAsked()
	{
		return goAsked;
	}
	
	/**
	 * Indique si l'utilisateur veut effacer l'affichage
	 * 
	 * @return true si on a clique sur clear
	 * */
	public boolean getClearAsked()
	{
		return clearAsked;
	}
	
	/**
	 * Indique si l'utilisateur veut recharger les images des obstacles car selectionnees aleatoirement
	 * 
	 * @return true si on a clique sur refresh
	 * */
	public boolean getRefreshAsked()
	{
		return refreshAsked;
	}
	
	/**
	 * Modifie la valeur de clearAsked selon celle entree en parametre
	 * 
	 * @param clearAsked Valeur a assigner a clearAsked
	 * */
	public void setClearAsked(boolean clearAsked)
	{
		this.clearAsked = clearAsked;
	}
	
	/**
	 * Modifie la valeur de goAsked selon celle entree en parametre
	 * 
	 * @param goAsked Valeur a assigner a goAsked
	 * */
	public void setGoAsked(boolean goAsked)
	{
		this.goAsked = goAsked;
	}
	
	/**
	 * Modifie la valeur de refreshAsked selon celle entree en parametre
	 * 
	 * @param refreshAsked Valeur a assigner a refreshAsked
	 * */
	public void setRefreshAsked(boolean refreshAsked)
	{
		this.refreshAsked = refreshAsked;
	}
	
	/**
	 * Indique quel algorithme est selectionne
	 * 
	 * @return L'indice de l'algorithme selectionne
	 * */
	public int getAlgoSelected()
	{
		return FenetreSelection.getSelectedIndex();
	}
	
	/**
	 * Indique si le mode temps est selectionne
	 * 
	 * @return true si le mode temps est selectionne
	 * */
	public boolean getAfficheTempsSelected(){
		return afficheTemps.isSelected();
	}
	
	/**
	 * Indique si le mode reflexion est selectionne
	 * 
	 * @return true si le mode reflexion est selectionne
	 * */
	public boolean getAfficheReflexionSelected() {
		return afficheReflexion.isSelected();
	}
	
	/**
	 * Indique si le theme classique est selectionne
	 * 
	 * @return true si le theme classique est selectionne
	 * */
	public boolean getThemeClassiqueSelected(){
		return themeClassique.isSelected();
	}
	
	/**
	 * Indique si le theme pirate est selectionne
	 * 
	 * @return true si le theme pirate est selectionne
	 * */
	public boolean getThemePirateSelected(){
		return themePirate.isSelected();
	}
	
	/**
	 * Indique si le theme espace est selectionne
	 * 
	 * @return true si le theme espace est selectionne
	 * */
	public boolean getThemeEspaceSelected(){
		return themeEspace.isSelected();
	}
	
	/**
	 * Met a jour le temps et le nombre de coups necessaires dans l'affichage de la fenetre d'interface
	 * 
	 * @param tempsAlgo Temps necessaire a l'algorithme pour calculer le dernier chemin
	 * @param nbDeplacements Nombre de deplacements necessaires pour parcourir le dernier chemin
	 * */
	public void setTempsAlgo(long tempsAlgo, int nbDeplacements){
		this.tempsAlgo = tempsAlgo;
		this.nbDeplacements = nbDeplacements;
		affichage.setText("<html>temps necessaire : "+this.tempsAlgo+" ms<br>nb de coups : "+this.nbDeplacements+"</html>"); // on met à jour le JLabel affichant le temps et le nombre de déplacements
	}
	
}

/**
 * Classe des noeuds utilises pour l'algorithme A*
 * */
public class NoeudAStar extends Noeud{
	
	private double heuristique;
	private double dx;
	private double dy;
	private double distance;
	private int cout;
	private double nbCoups;
	private NoeudAStar parent;
	
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param p Definit si le noeud a ete parcouru ou non
	 * @param o Definit si le noeud est en fait un obstacle
	 * @param r Definit si le noeud est en fait un ralentissement
	 * @param depart Definit si le noeud est le noeud de depart
	 * @param goal Noeud d'arrivee
	 * */
	public NoeudAStar(int x, int y, boolean p, boolean o, boolean r, boolean depart, Noeud goal){
		super(x, y, p, o, r);
		dx = Math.abs(goal.getX() - this.x);
		dy = Math.abs(goal.getY() - this.y);
		distance = Math.sqrt(dx*dx+dy*dy);
		
		if (ralentissement)
		{
			cout = 2;
		}
		else if (depart)
		{
			cout = 0;
		}
		else
		{
			cout = 1;
		}
		heuristique = distance + cout;
		parent = null;
		nbCoups = 0;
	}
	
	/**
	 * Renvoie le noeud precedent le noeud actuel dans le chemin
	 * 
	 * @return Parent du neoud actuel
	 * */
	public NoeudAStar getParent(){
		return parent;
	}
	
	/**
	 * Renvoie l'heuristique du noeud
	 * 
	 * @return Heuristique du noeud
	 * */
	public double getHeuristique(){
		return heuristique;
	}
	
	/**
	 * Renvoie le cout necessaire pour rejoindre le noeud depuis un noeud adjacent
	 * 
	 * @return Cout du noeud
	 * */
	public int getCout(){
		return cout;
	}
	
	/**
	 * Renvoie le nombre de coups qui ont ete necessaires pour arriver jusqu'a ce noeud
	 * 
	 * @return Nombre de coups pour atteindre ce noeud depuis le point de depart
	 * */
	public double getNbCoups(){
		return nbCoups;
	}
	
	/**
	 * Definit depuis quel noeud on atteint le noeud actuel
	 * 
	 * @param parent Noeud depuis lequel on accede au noeud actuel
	 * */
	public void setParent(NoeudAStar parent){
		this.parent = parent;
	}
	
	/**
	 * Definit le cout du noeud depuis la valeur passee en parametre
	 * 
	 * @param cout Cout du noeud qui remplacera l'ancien
	 * */
	public void setCout(int cout){
		this.cout = cout;
	}
	
	/**
	 * Modifie la valeur de nbCoups selon celle entree en parametre
	 * 
	 * @param nbCoups Valeur a assigner a nbCoups
	 * */
	public void setNbCoups(double nbCoups){
		this.nbCoups = nbCoups;
	}
	
	/**
	 * Met a jour l'heuristique du noeud
	 * */
	public void majHeuristique(){
		heuristique = distance + nbCoups;
	}
}

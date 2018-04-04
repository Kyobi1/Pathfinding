/**
 * Classe du graphe utilise par l'algorithme A*, herite de Graphe
 * */
public class GrapheAStar extends Graphe{
	
	private NoeudAStar departAStar;
	private NoeudAStar arriveeAStar;
	private NoeudAStar[][] noeudsAStar;
	
	/**
	 * Constructeur
	 * 
	 * @param plateau Tableau d'objets de la fenetre principale
	 * @param depart Point de depart
	 * @param arrivee Point d'arrivee
	 * */
	public GrapheAStar(Objet[][] plateau, Noeud depart, Noeud arrivee){
		super (plateau, depart, arrivee);
		noeudsAStar = new NoeudAStar[this.plateau.length][this.plateau[0].length];
		for (int i = 0; i < this.plateau.length; i++) {
			for (int j = 0; j < this.plateau[0].length; j++) {
                if (this.plateau[i][j] instanceof Mur) {
                    this.noeudsAStar[i][j] = new NoeudAStar (i, j, false, true, false, false, arrivee);
                } else if (this.plateau[i][j] instanceof Ralentissement) {
                    this.noeudsAStar[i][j] = new NoeudAStar (i, j, false, false, true, false, arrivee);
                } else {
                    this.noeudsAStar[i][j] = new NoeudAStar (i, j, false, false, false, false, arrivee);
                }
			}
		}
		departAStar = new NoeudAStar(depart.getX(), depart.getY(), false, false, false, true, arrivee);
		arriveeAStar = new NoeudAStar(arrivee.getX(), arrivee.getY(), false, false, false, false, arrivee);
	}
	
	/**
	 * Renvoie le point de depart du graphe utilise par A*
	 * 
	 * @return Noeud de depart du graphe
	 * */
	public NoeudAStar getDepartAStar(){
		return departAStar;
	}
	
	/**
	 * Renvoie le point d'arrivee du graphe utilise par A*
	 * 
	 * @return Noeud d'arrivee du graphe
	 * */
	public NoeudAStar getArriveeAStar(){
		return arriveeAStar;
	}
	
	/**
	 * Renvoie un noeud situe a une certaine position dans le graphe en fonction des valeurs donnees en parametre
	 * 
	 * @param i Premiere coordonnee necessaire pour identifier un noeud dans le graphe
	 * @param j Deuxieme coordonnee necessaire pour identifier un noeud dans le graphe
	 * 
	 * @return Noeud selectionne a l'interieur du graphe
	 * */
	public NoeudAStar getNoeud(int i, int j){
		return noeudsAStar[i][j];
	}
}

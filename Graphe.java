import java.util.*;
/**
 * Classe mere de tous les graphes utilises
 * */
public class Graphe {
	
	protected Noeud[][] noeuds;
	protected Objet[][] plateau;
	protected Noeud depart;
	protected Noeud arrivee;
    protected Noeud parcouru;
	
	/**
	 * Constructeur
	 * 
	 * @param plateau Tableau d'objets de la fenetre principale
	 * @param depart Point de depart
	 * @param arrivee Point d'arrivee
	 * */
	public Graphe (Objet[][] plateau, Noeud depart, Noeud arrivee) {
		this.plateau = plateau;
		this.noeuds = new Noeud[this.plateau.length][this.plateau[0].length];
		this.noeuds[depart.getX()][depart.getY()] = depart;
		this.depart = depart;
		this.arrivee = arrivee;
	}
    
    /**
     * Indique quel noeud a ete parcouru lors de la derniere iteration
     * 
     * @return Dernier noeud parcouru
     * */
    public Noeud getParcouru () {
        return this.parcouru;
    }
    
    /**
     * Renvoie le point de depart du graphe
     * 
     * @return Noeud de depart du graphe
     * */
    public Noeud getDepart(){
		return depart;
	}
	
	/**
     * Renvoie le point d'arrivee du graphe
     * 
     * @return Noeud d'arrivee du graphe
     * */
	public Noeud getArrivee(){
		return arrivee;
	}
}


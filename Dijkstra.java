import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 * Classe de l'algorithme Dijkstra
 * */
public class Dijkstra {
	
	protected Objet[][] plateau;
	protected GrapheDijkstra graphe;
	protected Chemin result;
    
    protected ArrayList pointsParcourus;
	
	/**
	 * Constructeur
	 * 
	 * @param plateau Tableau d'objets de la fenetre principale
	 * */
	public Dijkstra (Objet[][] plateau){
        this.pointsParcourus = new ArrayList (1);
		this.plateau = plateau;
		Noeud ptDepart = this.trouvePtDepart();
		this.graphe = new GrapheDijkstra (plateau, ptDepart, this.trouvePtArrivee());
		this.result = new Chemin (new Case (ptDepart.getX(), ptDepart.getY(), null));
	}
	
	/**
	 * Trouve le point de depart sur le plateau
	 * 
	 * @return Noeud de depart
	 * */
	public Noeud trouvePtDepart () {
		int[] result = new int[2];
		for (int i = 0; i < plateau.length; i++) {
			for (int p = 0; p < plateau[0].length; p++) {
				if (plateau[i][p] instanceof PtDepart) {return new Noeud (i, p, true, false, false);}
			}
		}
		return new Noeud (-1, -1, false, false, false);
	}
	
	/**
	 * Trouve le point d'arrivee sur le plateau
	 * 
	 * @return Noeud d'arrivee
	 * */
	public Noeud trouvePtArrivee () {
		int[] result = new int[2];
		for (int i = 0; i < plateau.length; i++) {
			for (int p = 0; p < plateau[0].length; p++) {
				if (plateau[i][p] instanceof PtArrivee) {return new Noeud (i, p, false, false, false);}
			}
		}
		return new Noeud (-1, -1, false, false, false);
	}
	
	/**
	 * Renvoie le plateau correspondant à l'affichage principal
	 * 
	 * @return Tableau d'objets enregistre par l'algorithme
	 * */
	public Objet[][] getPlateau () {
		return this.plateau;
	}
	
	/**
	 * Renvoie le graphe caracteristique du plateau pour cet algorithme
	 * 
	 * @return Graphe du plateau utilise par l'algorithme Dijkstra
	 * */
	public GrapheDijkstra getGraphe () {
		return this.graphe;
	}
	
	/**
	 * Renvoie le chemin calcule par l'algortihme
	 * 
	 * @return Chemin calcule avec l'algorithme Dijkstra
	 * */
	public Chemin getChemin () {
		return this.result;
	}
    
    /**
     * Indique quelles sont les possibilites de chemin a parcourir par l'algorithme Dijkstra
     * 
     * @return Liste de tous les noeuds possibles a parcourir
     * */
    public ArrayList getParcouru () {
		return this.pointsParcourus;
	}
	
	/**
	 * Ajoute un noeud a la liste de possibilites de noeus a parcourir
	 * 
	 * @param noeud Noeud a ajouter a la liste
	 * */
	public void addParcouru (Noeud noeud) {
		this.pointsParcourus.add(noeud);
	}
	
	/**
	 * Enleve la denrniere possibilite de noeud a parcourir dans la liste
	 * */
	public void removeLastParcouru () {
		this.pointsParcourus.remove(this.pointsParcourus.size() - 1);
	}
	
	/**
	 * Modifie la valeur de result, le chemin final selon celle entree en parametre
	 * 
	 * @param chemin Valeur a assigner a result
	 * */
	public void setChemin (Chemin chemin) {
		this.result = chemin;
	}
}

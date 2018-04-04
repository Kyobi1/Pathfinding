/**
 * Classe d'un arc servant a relier deux neouds dans l'algorithme dijkstra
 * */
public class Arc {
	
	private Noeud origine;
	private Noeud destination;
	private double poids;
	private boolean parcouru;
	private Arc precedent;
	
	/**
	 * Constructeur
	 * 
	 * @param a Point de depart de l'arc
	 * @param b Point d'arrivee de l'arc
	 * @param p Poids de l'arc (correspond a la distance entre deux noeuds)
	 * @param pre Arc engendrant l'arc actuel
	 * */
	public Arc (Noeud a, Noeud b, double p, Arc pre) {
		this.origine = a;
		this.destination = b;
		this.poids = p;
		this.parcouru = false;
		this.precedent = pre;
	}
	
	/**
	 * Indique le point de depart de l'arc
	 * 
	 * @return Point de depart de l'arc
	 * */
	public Noeud getOrigine () {
		return this.origine;
	}
	
	/**
	 * Indique le point d'arrivee de l'arc
	 * 
	 * @return Point d'arrivee de l'arc
	 * */
	public Noeud getDestination () {
		return this.destination;
	}
	
	/**
	 * Indique le poids de l'arc
	 * 
	 * @return Poids de l'arc
	 * */
	public double getPoids () {
		return this.poids;
	}
	
	/**
	 * Indique si l'arc a ete parcouru
	 * 
	 * @return true si l'arc a ete parcouru
	 * */
	public boolean getParcouru () {
		return this.parcouru;
	}
	
	/**
	 * Indique quel arc a permis d'atteindre celui-ci
	 * 
	 * @return Arc precedent
	 * */
	public Arc getPrecedent () {
		return this.precedent;
	}
	
	/**
	 * Modifie la valeur de parcouru selon celle entree en parametre
	 * 
	 * @param parc Valeur a assigner a parcouru
	 * */
	public void setParcouru (boolean parc) {
		this.parcouru = parc;
	}
}


/**
 * Classe representant un noeud dans un graphe
 * */
public class Noeud extends Case{
	
	protected boolean parcouru;
    protected boolean obstacle;
    protected boolean ralentissement;
	
	/**
	 * Constructeur par defaut
	 * */
	public Noeud () {
		super(-1, -1, null);
		this.parcouru = false;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param p Definit si le noeud a ete parcouru ou non
	 * @param o Definit si le noeud est en fait un obstacle
	 * @param r Definit si le noeud est en fait un ralentissement
	 * */
	public Noeud (int x, int y, boolean p, boolean o, boolean r) {
		super (x, y, null);
		this.parcouru = p;
        this.obstacle = o;
        this.ralentissement = r;
	}
	
	/**
	 * Indique si un noeud est adjacent a un autre
	 * 
	 * @param b Noeud a tester
	 * 
	 * @return true si les deux noeuds sont bien adjacents, false sinon
	 * */
	public boolean adjacent (Noeud b) {
		if ((Math.abs(this.x - b.getX()) == 1 && this.y == b.getY()) || (Math.abs(this.y - b.getY()) == 1 && this.x == b.getX())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Indique si deux noeuds sont egaux
	 * 
	 * @param b Noeud a tester
	 * 
	 * @return true si les deux noeuds sont situes au meme endroit, false sinon
	 * */
	public boolean equals (Noeud b) {
		if (this.x == b.getX() && this.y == b.getY()) {return true;}
		else {return false;}
	}
	
	/**
	 * Indique si le noeud a ete parcouru
	 * 
	 * @return true si le noeud a ete parcouru, false sinon
	 * */
	public boolean getParcouru () {
		return this.parcouru;
	}
	
	
	/**
	 * Modifie la valeur de parcouru selon celle entree en parametre
	 * 
	 * @param parc Valeur a assigner a parcouru
	 * */
	public void setParcouru (boolean parc) {
		this.parcouru = parc;
	}
    
    /**
     * Indique si le noeud est un obstacle
     * 
     * @return true si le noeud est un obstacle, false sinon
     * */
    public boolean getObstacle () {
        return  this.obstacle;
    }
    
    /**
     * Indique si le noeud est un ralentissement
     * 
     * @return true si le noeud est un ralentissement, false sinon
     * */
    public boolean getRalentissement () {
        return this.ralentissement;
    }
}


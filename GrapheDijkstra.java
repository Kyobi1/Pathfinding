import java.util.*;

/**
 * Classe du grpahe utilise par l'algorithme Dijkstra
 * */
public class GrapheDijkstra extends Graphe{		
	private ArrayList arcs;		// On stocke l'ensemble des arcs générés par l'algorithme, parcourus ou non
	
	/**
	 * Constructeur
	 * 
	 * @param plateau Tableau d'objets de la fenetre principale
	 * @param depart Point de depart
	 * @param arrivee Point d'arrivee
	 * */
	public GrapheDijkstra(Objet[][] plateau, Noeud depart, Noeud arrivee){ 		// Initailisation de chaque paramètre
		super(plateau, depart, arrivee);
		for (int i = 0; i < this.plateau.length; i++) {			// On convertit le tableau d'objet en un tableau de noeuds utilisable par l'algorithme Dijkstra
			for (int p = 0; p < this.plateau[0].length; p++) {
                if (this.plateau[i][p] instanceof Mur) {
                    this.noeuds[i][p] = new Noeud (i, p, false, true, false);
                } else if (this.plateau[i][p] instanceof Ralentissement) {
                    this.noeuds[i][p] = new Noeud (i, p, false, false, true);
                } else {
                    this.noeuds[i][p] = new Noeud (i, p, false, false, false);
                }
			}
		}
		this.noeuds[depart.getX()][depart.getY()] = depart;
		this.arcs = new ArrayList(1);
		this.assigneArc(depart, null);		// On effectue une première assignation des arcs possibles (les 8 arcs allant du point de départ aux 8 cases environnantes)
	}
	
	/**
	 * Definit les nouveaux arcs possibles a partir d'un noeud
	 * 
	 * @param noeudChoisi Noeud a partir duquel on construit les nouveaux arcs
	 * @param arcChoisi Arc precedent qui a permis d'atteindre le noeudChoisi
	 * */
	public void assigneArc (Noeud noeudChoisi, Arc arcChoisi) {	// Permet à chaque itération de définir les nouveaux arcs générés par le choix de parcourir une case
			for (int i = 0; i < this.plateau.length; i++) {
				for (int p = 0; p < this.plateau[0].length; p++) {
					if (this.noeuds[i][p].adjacent(noeudChoisi) && !this.noeuds[i][p].getParcouru() && !this.noeuds[i][p].getObstacle()) {
						if (this.noeuds[i][p].getRalentissement()) {
                            this.arcs.add(new Arc(noeudChoisi, this.noeuds[i][p], 2, arcChoisi));
                        } else {
                            this.arcs.add(new Arc(noeudChoisi, this.noeuds[i][p], 1, arcChoisi));
                        }
					}
                    if (Math.abs(this.noeuds[i][p].getX() - noeudChoisi.getX()) == 1 && Math.abs(this.noeuds[i][p].getY() - noeudChoisi.getY()) == 1 && !this.noeuds[i][p].getObstacle()) {
                        if (this.noeuds[i][p].getRalentissement()) {
                            this.arcs.add(new Arc(noeudChoisi, this.noeuds[i][p], 2 * Math.sqrt(2), arcChoisi));
                        } else {
                            this.arcs.add(new Arc(noeudChoisi, this.noeuds[i][p], Math.sqrt(2), arcChoisi));
                        }
                    }
				}
			}
		
	}
	
	/**
	 * Choisit le meilleur arc possible parmi ceux restants en fonction de son poids
	 * 
	 * @return L'arc de poids le plus faible
	 * */
	public Arc choixArc () {
		double sommePoids = 0;
		double poidsMini = 0;
		boolean done = false;
		Arc result = null;
		for (int i = 0; i < this.arcs.size(); i++) {
			Arc test = (Arc)this.arcs.get(i);
			Arc resultPotentiel = (Arc)this.arcs.get(i);
			Noeud destinationPotentielle = resultPotentiel.getDestination();
			if (!test.getParcouru() && !destinationPotentielle.getParcouru()) {
				sommePoids = test.getPoids();
				while (test.getPrecedent() != null) {
					test = test.getPrecedent();
					sommePoids += test.getPoids();
				}
				if (sommePoids < poidsMini || done == false) {
					poidsMini = sommePoids;
					result = resultPotentiel;
					done = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Met a jour le graphe en ajoutant un nouvel arc reliant deux noeuds
	 * 
	 * @return Arc choisi par l'algorithme pour cette iteration
	 * */
	public Arc itererGraphe () {
		Arc choisi = this.choixArc();
        if (choisi == null) {
            return new Arc (null, null, 0, null);
        }
		parcouru = choisi.getDestination();
		
		this.noeuds[parcouru.getX()][parcouru.getY()].setParcouru(true);
		
		int index = this.arcs.indexOf(choisi);
		this.arcs.remove(index);
		choisi.setParcouru(true);
		this.arcs.add(choisi);
		
		this.assigneArc(parcouru, choisi);
		
		if (parcouru.equals(this.arrivee)) {return choisi;}
		
		return null;
	}
}

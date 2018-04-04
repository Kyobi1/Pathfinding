import java.util.LinkedList;
/**
 * Classe de l'algorithme A*
 * */
public class AStar{
	
	private LinkedList <NoeudAStar> listeFermee; // La liste fermée de l'algorithme A* contient tous les noeuds étudiés dans la recherche du meilleur chemin possible
	private LinkedList <NoeudAStar> listeOuverte; // La liste ouverte de l'algorithme A* contient la liste des noeuds possibles restant éventuellement à parcourir
	private GrapheAStar graphe; // Le graphe contenant toutes les informations concernant le quadrillage
	private Objet[][] quadrillage; // quadrillage représentant l'affichage
	private NoeudAStar cur; // Le noeud correspondant au noeud courant traité à chaque nouvelle itération de A*
	
	/**
	 * Constructeur
	 * 
	 * @param plateau Tableau d'objets de la fenetre principale
	 * @param depart Point de depart
	 * @param arrivee Point d'arrivee
	 * */
	public AStar(Objet[][] plateau, PtDepart depart, PtArrivee arrivee){
		graphe = new GrapheAStar(plateau, new Noeud(depart.getX()/25, depart.getY()/25, false, false, false), new Noeud(arrivee.getX()/25, arrivee.getY()/25, false, false, false)); // on initialise le graphe
		listeFermee = new LinkedList();
		listeOuverte = new LinkedList();
		listeOuverte.add(graphe.getDepartAStar()); // La liste ouverte est initialisée avec le premier noeud à parcourir : le point de départ
		quadrillage = plateau;
		cur = listeOuverte.get(0); // premier noeud initialisé au point de départ
		cur.setParent(null); // pas de noeud précédent le point de départ
	}
	/**
	 * Itere l'algorithme A* a l'etape suivante
	 * 
	 * @return null tant que le chemin n'est pas fini de calculer ou renvoie le plus court chemin du point de depart au pint d'arrivee
	 * */
	public Chemin iterer() {
		tri(); // La liste ouverte est d'abord triée selon le poids de chaque noeud qu'elle contient
		cur = listeOuverte.get(0); // On choisit le noeud de plus faible poids, c'est-à-dire le premier de la liste
		reorganize(); // On supprime l'élément choisi de la liste ouverte et on réorganise celle-ci
		if (cur.getX() == graphe.getArriveeAStar().getX() && cur.getY() == graphe.getArriveeAStar().getY()) // si le noeud courant n'est autre que le point d'arrivée
		{
			cur = cur.getParent(); // on n'inclut pas le point d'arrivée dans le chemin final
			Chemin result = new Chemin(new Case(cur.getX()*25, cur.getY()*25, null)); // le chemin retourné par l'algorithme
			while (cur.getParent()!= null) // tant que le noeud courant a un parent (tant que l'on n'est pas revenu au point de départ)
			{
				result.ajoutFin(new Case(cur.getX()*25, cur.getY()*25, null)); // On ajoute le noeud courant au chemin final
				cur = cur.getParent(); // on récupère le noeud précédent
			}
			return result; // on renvoie le chemin final
		}
		if (cur.getX() > 0 && cur.getY() > 0) // si l'on ne se trouve pas dans le coin supérieur gauche
		{
			traiterNoeud(graphe.getNoeud(cur.getX()-1,cur.getY()-1)); // on traite le noeud voisin situé sur la diagonale haut-gauche
		}
		if (cur.getX() > 0) // si l'on ne se trouve pas sur la colonne de gauche
		{
			traiterNoeud(graphe.getNoeud(cur.getX()-1,cur.getY())); // on traite le noeud voisin situé à gauche
		}
		if (cur.getX() > 0 && cur.getY() < quadrillage[0].length-1) // si l'on ne se trouve pas dans le coin inférieur gauche
		{
			traiterNoeud(graphe.getNoeud(cur.getX()-1,cur.getY()+1)); // on traite le noeud voisin situé sur la diagonale bas-gauche
		}
		if (cur.getY() > 0) // si l'on ne se trouve pas sur la ligne du haut
		{
			traiterNoeud(graphe.getNoeud(cur.getX(),cur.getY()-1)); // on traite le noeud voisin situé au dessus
		}
		if (cur.getY() < quadrillage[0].length-1) // si l'on ne se trouve pas sur la ligne du bas
		{
			traiterNoeud(graphe.getNoeud(cur.getX(),cur.getY()+1)); // on traite le noeud voisin situé en dessous
		}
		if (cur.getX() < quadrillage.length-1 && cur.getY() > 0)  // si l'on ne se trouve pas dans le coin supérieur droit
		{
			traiterNoeud(graphe.getNoeud(cur.getX()+1,cur.getY()-1)); // on traite le noeud voisin situé sur la diagonale haut-droite
		}
		if (cur.getX() < quadrillage.length-1) // si l'on ne se trouve pas sur la colonne de droite
		{
			traiterNoeud(graphe.getNoeud(cur.getX()+1,cur.getY())); // on traite le noeud voisin situé à droite
		}
		if (cur.getX() < quadrillage.length-1 && cur.getY() < quadrillage[0].length-1) // si l'on ne se trouve pas dans le coin inférieur droit
		{
			traiterNoeud(graphe.getNoeud(cur.getX()+1,cur.getY()+1)); // on traite le noeud voisin situé sur la diagonale bas-droite
		}
		listeFermee.add(graphe.getNoeud(cur.getX(),cur.getY())); // on ajoute le noeud courant à la liste fermée
		
		return null; // ne retourne rien tant que le chemin n'est pas complet ou que l'on sait qu'il n'y a pas de chemin possible
	}
	
	/**
	 * Trie la liste ouverte par ordre d'heuristique croissante
	 * */
	public void tri(){
		if (!(listeOuverte.isEmpty()))
		{
			for (int i = 0; i < listeOuverte.size()-1; i ++){ // on trie la liste ouverte par order croissant d'heuristique de chaque noeud à l'intérieur
				if (listeOuverte.get(i).getHeuristique() > listeOuverte.get(i+1).getHeuristique())
				{
					NoeudAStar n = listeOuverte.get(i);
					listeOuverte.set(i, listeOuverte.get(i+1));
					listeOuverte.set(i+1, n);
					i = -1;
				}
			}
		}
	}
	
	/**
	 * Supprime le premier element de la liste ouverte et decale tous les autres
	 * */
	public void reorganize(){
		for (int i = 0; i < listeOuverte.size()-1; i ++){
			listeOuverte.set(i, listeOuverte.get(i+1));
		}
		listeOuverte.remove(listeOuverte.size()-1);
	}
	
	/**
	 * Traitement d'un noeud voisin du noeud courant par l'algorithme A*
	 * 
	 * @param noeud Le noeud etudie
	 * */
	public void traiterNoeud(NoeudAStar noeud){
		if (!(noeud.getObstacle())) // on teste le noeud situé sur la diagonale haut-gauche sauf si c'est un obstacle
		{
			if (Math.abs(noeud.getX() - cur.getX()) == 1 && Math.abs(noeud.getY() - cur.getY()) == 1) // si le noeud etudie est situe sur une diagonale
			{
				if (!(listeOuverte.contains(noeud)) && !(listeFermee.contains(noeud))) // si aucune des listes ne contient le noeud étudié
				{
					// on indique le nombre de déplacement qui ont été nécessaires pour atteindre ce noeud : pour cela, on prend le nombre de déplacements nécéssaires pour atteindre le noeud courant et on y ajoute le coût de déplacement jusqu'au prochain noeud plus un coefficient caractérisitique de la diagonale
					noeud.setNbCoups(cur.getNbCoups()+noeud.getCout()+Math.abs((noeud.getCout()-Math.sqrt(2))));
					noeud.majHeuristique();
					listeOuverte.add(noeud); // on peut ajouter ce noeud à la liste ouverte
					noeud.setParent(cur); // on indique que le parent du noeud ajouté est le noeud courant
				}
				// sinon si le noeud se trouve dans des deux listes, si le nombre de coups nécéssaires pour l'atteindre par le noeud actuel est inférieur que son nombre de coups actuel, cela signifie que l'on a trouvé un meilleur chemin pour l'atteindre
				else if (noeud.getNbCoups() > cur.getNbCoups()+noeud.getCout()+Math.abs((noeud.getCout()-Math.sqrt(2))))
				{
					// on met à jour le noeud, son nombre de coups (donc son heuristique) et son parent
					noeud.setNbCoups(cur.getNbCoups()+noeud.getCout()+Math.abs((noeud.getCout()-Math.sqrt(2))));
					noeud.majHeuristique();
					noeud.setParent(cur);
				}
			}
			else
			{
				if (!(listeOuverte.contains(noeud)) && !(listeFermee.contains(noeud))) // si aucune des listes ne contient le noeud étudié
				{
					// on indique le nombre de déplacement qui ont été nécessaires pour atteindre ce noeud : pour cela, on prend le nombre de déplacements nécéssaires pour atteindre le noeud courant et on y ajoute le coût de déplacement jusqu'au prochain noeud
					noeud.setNbCoups(cur.getNbCoups()+noeud.getCout());
					noeud.majHeuristique();
					listeOuverte.add(noeud); // on peut ajouter ce noeud à la liste ouverte
					noeud.setParent(cur); // on indique que le parent du noeud ajouté est le noeud courant
				}
				// sinon si le noeud se trouve dans des deux listes, si le nombre de coups nécéssaires pour l'atteindre par le noeud actuel est inférieur que son nombre de coups actuel, cela signifie que l'on a trouvé un meilleur chemin pour l'atteindre
				else if (noeud.getNbCoups() > cur.getNbCoups()+noeud.getCout())
				{
					// on met à jour le noeud, son nombre de coups (donc son heuristique) et son parent
					noeud.setNbCoups(cur.getNbCoups()+noeud.getCout()+Math.abs((noeud.getCout()-Math.sqrt(2))));
					noeud.majHeuristique();
					noeud.setParent(cur);
				}
			}
		}
	}
	
	/**
	 * Renvoie la liste ouverte de l'algorithme A*
	 * 
	 * @return La liste ouverte
	 * */
	public LinkedList getListeOuverte () {
		return listeOuverte;
	}
	
	/**
	 * Renvoie la liste fermee de l'algorithme A*
	 * 
	 * @return La liste fermee
	 * */
	public LinkedList getListeFermee () {
		return listeFermee;
	}
}

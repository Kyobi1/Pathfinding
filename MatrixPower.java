import java.util.ArrayList;
/**
 * Classe de l'algorithme des puissances de matrices
 * */
public class MatrixPower{
	
	private int [][] adjacence; // matrice d'adjacence initiale nécéssaire pour modéliser les possibilités de déplacement entre deux cases
	private int[][] adjacence2; // matrice d'adjacence qui contiendra à chaque itération le nouveau résultat
	private int l; // largeur du quadrillage
	private int h; // hauteur du quadrillage
	
	private PtDepart start; // point de départ
	private PtArrivee goal; // point d'arrivée
	private Chemin chemin; // chemin final
	private ListeMatrice stockage; // stockage de toutes les matrices d'adjacence qui seront calculées au fur et à mesure
	private int puissance; // puissance à laquelle est élevée la matrice d'adjacence initiale
	
	/**
	 * Constructeur
	 * 
	 * @param quadrillage Tableau d'objets de la fenetre principale
	 * @param start Point de depart
	 * @param goal Point d'arrivee
	 * */
	public MatrixPower(Objet[][] quadrillage, PtDepart start, PtArrivee goal){
		l = quadrillage.length;
		h = quadrillage[0].length;
		adjacence = new int [l*h][l*h];
		initialisation(quadrillage);
		this.start = start;
		this.goal = goal;
		chemin = new Chemin(new Case(start.getX(), start.getY(), null));
		adjacence2 = adjacence;
		stockage = new ListeMatrice();
		puissance = 0;
	}
	
	/**
	 * Initialise la matrice d'adjacence
	 * 
	 * @param quadrillage Tableau d'objets de la fenetre principale
	 * */
	public void initialisation(Objet[][] quadrillage){
		/* on parcourt l'ensemble du quadrillage afin d'intialiser la matrice d'adjacence initiale
		 * Le but est de tester les relations entre deux cases : si on peut aller d'une case d'indice a vers une autre d'indice b, on met la case de la matrice d'adjacence d'indice [a][b] à 1, sinon, à 0
		 * */
		for (int i = 0; i < l; i ++)
		{
			for (int j = 0; j < h; j ++)
			{
				if (i > 0 && j > 0)
				{
					if (quadrillage[i-1][j-1] instanceof Mur || quadrillage[i][j] instanceof Mur)
					{
						adjacence[l*j + i][l*(j-1) + (i-1)] = 0;
					}
					else
					{
						adjacence[l*j + i][l*(j-1) + (i-1)] = 1;
					}
				}
				if (i > 0)
				{
					if (quadrillage[i-1][j] instanceof Mur || quadrillage[i][j] instanceof Mur)
					{
						adjacence[l*j + i][l*j + (i-1)] = 0;
					}
					else
					{
						adjacence[l*j + i][l*j + (i-1)] = 1;
					}
				}
				if (i > 0 && j < h-1)
				{
					if (quadrillage[i-1][j+1] instanceof Mur || quadrillage[i][j] instanceof Mur)
					{
						adjacence[l*j + i][l*(j+1) + (i-1)] = 0;
					}
					else
					{
						adjacence[l*j + i][l*(j+1) + (i-1)] = 1;
					}
				}
				if (j > 0)
				{
					if (quadrillage[i][j-1] instanceof Mur || quadrillage[i][j] instanceof Mur)
					{
						adjacence[l*j + i][l*(j-1) + i] = 0;
					}
					else
					{
						adjacence[l*j + i][l*(j-1) + i] = 1;
					}
				}
				if (j < h-1)
				{
					if (quadrillage[i][j+1] instanceof Mur || quadrillage[i][j] instanceof Mur)
					{
						adjacence[l*j + i][l*(j+1) + i] = 0;
					}
					else
					{
						adjacence[l*j + i][l*(j+1) + i] = 1;
					}
				}
				if (i < l-1 && j > 0)
				{
					if (quadrillage[i+1][j-1] instanceof Mur || quadrillage[i][j] instanceof Mur)
					{
						adjacence[l*j + i][l*(j-1) + (i+1)] = 0;
					}
					else
					{
						adjacence[l*j + i][l*(j-1) + (i+1)] = 1;
					}
				}
				if (i < l-1)
				{
					if (quadrillage[i+1][j] instanceof Mur || quadrillage[i][j] instanceof Mur)
					{
						adjacence[l*j + i][l*j + (i+1)] = 0;
					}
					else
					{
						adjacence[l*j + i][l*j + (i+1)] = 1;
					}
				}
				if (i < l-1 && j < h-1)
				{
					if (quadrillage[i+1][j+1] instanceof Mur || quadrillage[i][j] instanceof Mur)
					{
						adjacence[l*j + i][l*(j+1) + (i+1)] = 0;
					}
					else
					{
						adjacence[l*j + i][l*(j+1) + (i+1)] = 1;
					}
				}
			}
		}
	}
	
	/**
	 * Indique le chemin le plus court d'un point a un autre par une methode utilisant des produits de matrices
	 * 
	 * @return Le chemin le plus court entre le point de depart et le point d'arrivee
	 * */
	public Chemin solve() {
		boolean next;
		while (adjacence2[l*(start.getY()/25) + start.getX()/25][l*(goal.getY()/25) + goal.getX()/25] == 0) // tant que l'on ne trouve aucune possibilité d'aller du point de départ au point d'arrivée
		{
			if (puissance >= 700) // si l'on a dépassé la taille du plus grand chemin possible
			{
				return new Chemin(new Case(start.getX(), start.getY(), null)); // pas de chemin possible
			}
			else
			{
				stockage.ajoutFin(new Matrice(adjacence2, null)); // on stocke la nouvelle matrice dans la liste (on en aura besoin pour remonter le chemin)
				adjacence2 = prodmat(adjacence2, adjacence); // on multiplie adjacence2 et adjacence ce qui revient à mettre adjacence à la puissance i
				puissance ++;
			}
		}
		// une fois que l'on sait qu'un chemin existe
		for (int i = 0; i < puissance; i ++) // on parcourt tout le stockage de matrice
		{
			next = false; // next sert à passer à la case suivante une fois que l'on a trouvé la case que l'on cherchait
			adjacence2 = stockage.getElement(puissance-i-1).getMatrice();
			// on cherche quel case est la case précédente dans le trajet que l'on a obtenu
			if (chemin.getElement(i).getY() > 0 && next == false)
			{
				if (adjacence2[l*(chemin.getElement(i).getY()/25-1) + chemin.getElement(i).getX()/25][l*(goal.getY()/25) + goal.getX()/25] != 0)
				{
					chemin.ajoutFin(new Case(chemin.getElement(i).getX(), chemin.getElement(i).getY()-25, null));
					next = true;
				}
			}
			if (chemin.getElement(i).getX() > 0 && next == false)
			{
				if (adjacence2[l*(chemin.getElement(i).getY()/25) + chemin.getElement(i).getX()/25-1][l*(goal.getY()/25) + goal.getX()/25] != 0)
				{
					chemin.ajoutFin(new Case(chemin.getElement(i).getX()-25, chemin.getElement(i).getY(), null));
					next = true;
				}
			}
			if (chemin.getElement(i).getX()/25 < l-1 && next == false)
			{
				if (adjacence2[l*(chemin.getElement(i).getY()/25) + chemin.getElement(i).getX()/25+1][l*(goal.getY()/25) + goal.getX()/25] != 0)
				{
					chemin.ajoutFin(new Case(chemin.getElement(i).getX()+25, chemin.getElement(i).getY(), null));
					next = true;
				}
			}
			if (chemin.getElement(i).getY()/25 < h-1 && next == false)
			{
				if (adjacence2[l*(chemin.getElement(i).getY()/25+1) + chemin.getElement(i).getX()/25][l*(goal.getY()/25) + goal.getX()/25] != 0)
				{
					chemin.ajoutFin(new Case(chemin.getElement(i).getX(), chemin.getElement(i).getY()+25, null));
					next = true;
				}
			}
			if (chemin.getElement(i).getX() > 0 && chemin.getElement(i).getY() > 0 && next == false)
			{
				
				if (adjacence2[l*(chemin.getElement(i).getY()/25-1) + chemin.getElement(i).getX()/25-1][l*(goal.getY()/25) + goal.getX()/25] != 0)
				{
					chemin.ajoutFin(new Case(chemin.getElement(i).getX()-25, chemin.getElement(i).getY()-25, null));
					next = true;
				}
			}
			if (chemin.getElement(i).getX()/25 < l-1 && chemin.getElement(i).getY()/25 > 0 && next == false)
			{
				if (adjacence2[l*(chemin.getElement(i).getY()/25-1) + chemin.getElement(i).getX()/25+1][l*(goal.getY()/25) + goal.getX()/25] != 0)
				{
					chemin.ajoutFin(new Case(chemin.getElement(i).getX()+25, chemin.getElement(i).getY()-25, null));
					next = true;
				}
			}
			if (chemin.getElement(i).getX() > 0 && chemin.getElement(i).getY()/25 < h-1 && next == false)
			{
				if (adjacence2[l*(chemin.getElement(i).getY()/25+1) + chemin.getElement(i).getX()/25-1][l*(goal.getY()/25) + goal.getX()/25] != 0)
				{
					chemin.ajoutFin(new Case(chemin.getElement(i).getX()-25, chemin.getElement(i).getY()+25, null));
					next = true;
				}
			}
			if (chemin.getElement(i).getX()/25 < l-1 && chemin.getElement(i).getY()/25 < h-1 && next == false)
			{
				if (adjacence2[l*(chemin.getElement(i).getY()/25+1) + chemin.getElement(i).getX()/25+1][l*(goal.getY()/25) + goal.getX()/25] != 0)
				{
					chemin.ajoutFin(new Case(chemin.getElement(i).getX()+25, chemin.getElement(i).getY()+25, null));
					next = true;
				}
			}
		}
		adjacence = null;
		adjacence2 = null;
		stockage = null;
		return chemin;
	}
	
	/**
	 * Effectue un produit matriciel de deux matrices
	 * 
	 * @param A La premiere matrice a multiplier
	 * @param B La seconde matrice a multiplier
	 * 
	 * @return Le produit des deux matrices entrees en parametres
	 * */
	int[][] prodmat(int [][]A, int [][]B){
		
		int [][]C = new int [A.length][B[0].length];
		for (int i=0;i<A.length;i++)
		{
			for (int j=0;j<B[0].length;j++)
			{
				C[i][j]=0;
				for (int k=0;k<A[0].length;k++)
				{
					C[i][j]+=A[i][k]*B[k][j];
				}
			}
		}
		return C;
	}
}

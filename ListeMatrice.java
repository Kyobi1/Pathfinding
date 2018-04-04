/**
 * Classe permettant de stocker des matrices sous forme de liste
 * */
public class ListeMatrice{
	private Matrice root;
	
	/**
	 * Constructeur par defaut
	 * */
	public ListeMatrice(){
		root = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param matrice Premiere matrice de la liste
	 * */
	public ListeMatrice(Matrice matrice){
		root = matrice;
	}
	
	/**
	 * Renvoie un certain element de la liste en fonction de la valeur entree en parametre
	 * 
	 * @param n Indice de l'element a renvoyer
	 * 
	 * @return Nieme element de la liste
	 * */
	public Matrice getElement(int n){
		Matrice cur = root;
		for (int i = 0; i < n; i ++){
			cur = cur.getNext();
		}
		return cur;
	}
	
	/**
	 * Ajoute un element en fin de liste
	 * 
	 * @param matrice Element a ajouter en fin de liste
	 * */
	public void ajoutFin(Matrice matrice){
		if (root == null)
		{
			root = matrice;
		}
		else
		{
			Matrice cur = root;
			while (cur.getNext() != null){
				cur = cur.getNext();
			}
			cur.setNext(matrice);
		}
		matrice.setNext(null);
	}
}

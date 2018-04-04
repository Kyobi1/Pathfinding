/**
 * Classe du chemin calcule par les algorithmes
 * */
public class Chemin{
	private Case root;
	
	/**
	 * Constructeur
	 * 
	 * @param root Premiere case du chemin
	 * */
	public Chemin(Case root){
		this.root = root;
	}
	
	/**
	 * Renvoie la nieme case du chemin
	 * 
	 * @param n Nieme element a renvoyer
	 * 
	 * @return Nieme case du chemin
	 * */
	public Case getElement(int n){
		Case cur = root;
		for (int i = 0; i < n; i ++)
		{
			if (cur != null)
				cur = cur.getNext();
		}
		return cur;
	}
	
	/**
	 * Ajoute une case en bout de chemin
	 * 
	 * @param ajout Case à ajouter en fin de chemin
	 * */
	public void ajoutFin(Case ajout){
		Case cur = root;
		while (cur.getNext() != null)
		{
			cur = cur.getNext();
		}
		cur.setNext(ajout);
		ajout.setNext(null);
	}
	
	/**
	 * Indique la premiere case du chemin
	 * 
	 * @return Premiere case du chemin
	 * */
	public Case getRoot () {
		return this.root;
	}
}

/**
 * Classe d'un element de ListeMatrice
 * */
public class Matrice{
	private int[][] matrice;
	private Matrice next;
	
	/**
	 * Constructeur
	 * 
	 * @param matrice Tableau d'entiers a stocker
	 * @param next Matrice suivante dans la liste
	 * */
	public Matrice(int[][]matrice, Matrice next){
		this.matrice = matrice;
		this.next = next;
	}
	
	/**
	 * Renvoie la matrice d'entiers stockee dans cette instance
	 * 
	 * @return Matrice d'entiers de cet objet
	 * */
	public int[][] getMatrice(){
		return matrice;
	}
	
	/**
	 * Renvoie la matrice suivante dans la liste
	 * 
	 * @return Matrice suivante dans la liste
	 * */
	public Matrice getNext(){
		return next;
	}
	
	/**
	 * Modifie la valeur de matrice selon celle entree en parametre
	 * 
	 * @param matrice Valeur a assigner a matrice
	 * */
	public void setMatrice(int[][] matrice){
		this.matrice = matrice;
	}
	
	/**
	 * Modifie la valeur de next selon celle entree en parametre
	 * 
	 * @param next Valeur a assigner a next
	 * */
	public void setNext(Matrice next){
		this.next = next;
	}
}

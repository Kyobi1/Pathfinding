/**
 * Classe des possibilites de chemin calculees en mode reflexion
 * */
public class caseParcourue extends Objet{
	
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param theme Indique quelle image charger selon le theme selectionne
	 * */
	public caseParcourue(int x, int y, String theme){
		super(theme+"/caseParcourue.png", x, y);
	}
}

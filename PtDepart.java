/**
 * Classe du point de depart
 * */
public class PtDepart extends Objet{
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param theme Definit dans quel dossier chercher les images a charger
	 * */
	public PtDepart(int x, int y, String theme){
		super(theme+"/ptDepart.png", x, y);
	}
}

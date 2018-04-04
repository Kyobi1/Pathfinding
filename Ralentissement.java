/**
 * Classe du ralentissement
 * */
public class Ralentissement extends Objet{
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param theme Definit dans quel dossier chercher les images a charger
	 * */
	public Ralentissement(int x, int y, String theme){
		super(theme+"/ralentissement/ralentissement.png", x, y);
	}
}

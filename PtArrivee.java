/**
 * Classe du point d'arrivee
 * */
public class PtArrivee extends Objet{
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param theme Definit dans quel dossier chercher les images a charger
	 * */
	public PtArrivee(int x, int y, String theme){
		super(theme+"/ptArrivee.png", x, y);
	}
}

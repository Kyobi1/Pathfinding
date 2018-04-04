import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
/**
 * Classe representant une case du chemin calcule par l'un des algorithme
 * */
public class Case extends Objet{
	
	protected Case next;
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param next Case suivante dans le chemin
	 * */
	public Case(int x, int y, Case next){
		super("classique/chemin.png", x, y);
		this.next = next;
	}
	
	/**
	 * Indique la case suivant la case actuelle
	 * 
	 * @return Case suivante
	 * */
	public Case getNext(){
		return next;
	}
	
	/**
	 * Modifie la valeur de next selon celle entree en parametre
	 * 
	 * @param next Valeur a assigner a next
	 * */
	public void setNext(Case next){
		this.next = next;
	}
	
	/**
	 * Modifie la valeur de image selon le theme entre en parametre
	 * 
	 * @param theme Theme indiquant quelle image charger
	 * */
	public void setImage(String theme){
		try{
			image = ImageIO.read(new File(theme+"/chemin.png"));
		}
		catch(Exception err){
            System.out.println("fichier introuvable !");
            System.exit(0);
        }
	}
}

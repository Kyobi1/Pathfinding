import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
/**
 * Classe abstraite mere de tous les objets affiches sur la fenetre principale
 * */
public abstract class Objet{
	protected BufferedImage image;
	protected int x;
	protected int y;
	protected int l;
	protected int h;
	protected String theme;
	
	/**
	 * Constructeur
	 * 
	 * @param nomFichier Nom du fichier a charger
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * */
	public Objet(String nomFichier, int x, int y){
		try {
             image= ImageIO.read(new File(nomFichier));
             this.x = x;
             this.y = y;
             l = image.getWidth(null);
             h = image.getHeight(null);
        }
        catch(Exception err){
            System.out.println(nomFichier+" introuvable !");
            System.exit(0);
        }
	}
	
	/**
	 * Renvoie la coordonnee en x de l'objet
	 * 
	 * @return Coordonnee horizontale de la case sur l'ecran en pixels
	 * */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Renvoie la coordonnee en y de l'objet
	 * 
	 * @return Coordonnee verticale de la case sur l'ecran en pixels
	 * */
	public int getY(){
		return this.y;
	}
	
	/**
	 * Renvoie la largeur de l'objet
	 * 
	 * @return Largeur de l'image de l'objet en pixels
	 * */
	public int getL(){
		return this.l;
	}
	
	/**
	 * Renvoie la hauteur de l'objet
	 * 
	 * @return Hauteur de l'image de l'objet en pixels
	 * */
	public int getH(){
		return this.h;
	}
	
	/**
	 * Definit la coordonnee en x de l'objet
	 * 
	 * @param x Valeur a assigner a x
	 * */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * Definit la coordonnee en y de l'objet
	 * 
	 * @param y Valeur a assigner a y
	 * */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Dessine l'objet actuel sur une fenetre passe en parametre
	 * 
	 * @param g Objet graphique avec lequel dessiner
	 * @param jf Fenetre sur laquelle dessiner
	 * */
	public void dessine(Graphics g, JFrame jf) {
        g.drawImage(image,x,y+25,jf);
    }
}

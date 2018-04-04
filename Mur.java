import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;

/**
 * Classe des objets representant les obstacles sur la fenetre principale
 * */
public class Mur extends Objet{
	
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param theme Definit dans quel dossier chercher les images a charger
	 * */
	public Mur (int x, int y, String theme){
		super(theme+"/mur/mur.png", x, y);
	}
	
	/**
	 * Constructeur
	 * 
	 * @param x Coordonnee horizontale de la case sur l'ecran en pixels
	 * @param y Coordonnee verticale de la case sur l'ecran en pixels
	 * @param theme Definit dans quel dossier chercher les images a charger
	 * @param quadrillage Tableau d'objets de la fenetre principale
	 * */
	public Mur (int x, int y, String theme, Objet[][] quadrillage){
		super(theme+"/mur/mur.png", x, y);
		if (!theme.equals("classique") && !theme.equals("espace")) // Si le theme n'est pas classique ou espace
		{
			int x1 = x/25;
			int y1 = y/25;
			boolean haut = false;
			boolean bas = false;
			boolean gauche = false;
			boolean droite = false;
			
			int n= (int)Math.round(Math.random()*1.9+0.5); // parametre aleatoire afin de selectionner une image aleatoirement parmi celles disponibles
			
			if (x1 > 0){
				if (quadrillage[x1-1][y1] instanceof Mur){
					gauche = true;
				}
			}
			if (y1 > 0){
				if (quadrillage[x1][y1-1] instanceof Mur){
					haut = true;
				}
			}
			if (x1 < quadrillage.length-1){
				if (quadrillage[x1+1][y1] instanceof Mur){
					droite = true;
				}
			}
			if (y1 < quadrillage[0].length-1){
				if (quadrillage[x1][y1+1] instanceof Mur){
					bas = true;
				}
			}
			// on definit si le mur actuel possede des murs adjacents et leur position par rapport a celui-ci
			if (!haut && !bas && !gauche && !droite)
			{
				setImage(theme+"/mur/mur"+n+".png");
			}
			else if (!haut && !bas && !gauche && droite){
				setImage(theme+"/mur1/mur"+n+".png");
			}
			else if (!haut && !bas && gauche && !droite){
				setImage(theme+"/mur2/mur"+n+".png");
			}
			else if (!haut && bas && !gauche && !droite){
				setImage(theme+"/mur3/mur"+n+".png");
			}
			else if (haut && !bas && !gauche && !droite){
				setImage(theme+"/mur4/mur"+n+".png");
			}
			else if (!haut && !bas && gauche && droite){
				setImage(theme+"/mur5/mur"+n+".png");
			}
			else if (!haut && bas && !gauche && droite){
				setImage(theme+"/mur6/mur"+n+".png");
			}
			else if (haut && !bas && !gauche && droite){
				setImage(theme+"/mur7/mur"+n+".png");
			}
			else if (!haut && bas && gauche && !droite){
				setImage(theme+"/mur8/mur"+n+".png");
			}
			else if (haut && !bas && gauche && !droite){
				setImage(theme+"/mur9/mur"+n+".png");
			}
			else if (haut && bas && !gauche && !droite){
				setImage(theme+"/mur10/mur"+n+".png");
			}
			else if (!haut && bas && gauche && droite){
				setImage(theme+"/mur11/mur"+n+".png");
			}
			else if (haut && !bas && gauche && droite){
				setImage(theme+"/mur12/mur"+n+".png");
			}
			else if (haut && bas && !gauche && droite){
				setImage(theme+"/mur13/mur"+n+".png");
			}
			else if (haut && bas && gauche && !droite){
				setImage(theme+"/mur14/mur"+n+".png");
			}
			else if (haut && bas && gauche && droite){
				setImage(theme+"/mur15/mur"+n+".png");
			}
			// En fonction de la position des murs adjacents, on charge differentes images pour garder une coherence des environnments
		}
		else if (theme.equals("espace")){
			int n= (int)(Math.random()*3+1)+3;
			setImage(theme+"/mur/mur"+n+".png");
		}
		
	}
	
	/**
	 * Modifie l'image d'un obstacle
	 * 
	 * @param nomFichier Nom du fichier a charger
	 * */
	public void setImage(String nomFichier){
		try{
			image = ImageIO.read(new File(nomFichier));
		}
		catch(Exception err){
            System.out.println("fichier introuvable !");
            System.exit(0);
        }
	}
}

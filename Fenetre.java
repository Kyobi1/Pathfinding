import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Classe abstraite de laquelle heritent toutes les fenetres
 * */
public abstract class  Fenetre extends JFrame implements ActionListener{
	
	protected final int LARGEUR_FENETRE;
    protected final int HAUTEUR_FENETRE;
    protected Graphics buffer;
    
    protected final int TPS_TIMER_MS = 100;
	protected Timer timer;
	protected int temps;
    
    /**
     * Constructeur
     * 
     * @param l Largeur de la fenetre
     * @param h Hauteur de la fenetre
     * */
    public Fenetre(int l, int h)
    {
		LARGEUR_FENETRE = l;
		HAUTEUR_FENETRE = h;
	}
	
	/**
	 * Indique si la souris se trouve dans la zone de la fenetre
	 * 
	 * @param event Evenement caracteristique de la souris
	 * 
	 * @return true si la souris se trouve au dessus de la fenetre, false sinon
	 * */
	public boolean isMouseOnCurrentWindow(MouseEvent event){
		if (event.getX() >= 0 && event.getX() < LARGEUR_FENETRE && event.getY() >= 25 && event.getY() < HAUTEUR_FENETRE)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

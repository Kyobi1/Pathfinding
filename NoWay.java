import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe de la fenetre indiquant qu'il n'existe aucun chemin possible
 * */
public class NoWay extends Fenetre{
	
	private JButton ok;
	private JLabel message;
	private GridLayout layout;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private Color rouge;
	
	/**
	 * Constructeur
	 * */
	public NoWay(){
		super(250, 120);
		
		rouge = new Color(255, 0, 0);
		
		layout = new GridLayout(0,1, 0, -20);
		setLayout(layout);
		
		setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
		setLocation(300, 300);
		
		message = new JLabel("Pas de chemin possible");
		message.setForeground(rouge);
		
		ok = new JButton("OK");
		ok.addActionListener(this);
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		panel1.add(message);
		panel2.add(ok);
		
		add(panel1);
		add(panel2);
		
		setVisible(false);
	}
	
	/**
	 * Methode appelee a chaque tick du timer
	 * 
	 * @param event Evenement caracteristique de l'action effectuee
	 * */
	public void actionPerformed(ActionEvent event){
		if (event.getSource() == ok){
			dispose();
		}
	}
	
}

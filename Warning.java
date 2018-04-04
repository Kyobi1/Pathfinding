import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Classe de la fenetre d'avertissement concernant l'utilisation de l'algorithme matriciel
 * */
public class Warning extends Fenetre{
	
	private JLabel message;
	private JButton ok;
	private GridLayout layout;
	
	private JPanel panel1;
	private JPanel panel2;
	
	/**
	 * Constructeur par defaut
	 * */
	public Warning(){
		super(510, 180);
		
		layout = new GridLayout(0,1, 0, -20);
		setLayout(layout);
		
		setLocation(300, 300);
		
		setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
		
		ok = new JButton("OK");
		ok.addActionListener(this);
		
		message = new JLabel("<html> Avertissement : l'utilisation de cet algorithme donnera un resultat satisfaisant<br>mais le temps d'execution peut prendre jusqu'a plusieurs HEURES<br> pour de longs chemins. Il est donc conseille de l'utiliser pour des chemins tres courts.<br>Ne fonctionne pas en mode Reflexion et ne comprend pas de ralentissements.<br><br> Lire le fichier READ ME pour en savoir plus.</html>");
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		panel1.add(message);
		panel1.add(ok);
		
		add(panel1);
		
		setVisible(false);
	}
	
	/**
	 * Methode appelee a chaque tick du timer
	 * 
	 * @param event Evenement caracteristique de l'action effectuee
	 * */
	public void actionPerformed(ActionEvent event){
		if (event.getSource() == ok){
			this.dispose();
		}
	}
}

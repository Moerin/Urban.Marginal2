package vue;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controleur.Controle;
import controleur.Global;

import java.awt.Cursor;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import outils.son.Son;

public class ChoixJoueur extends JFrame implements Global {

	//--- propriétés privées : objets graphiques ---
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel lblFond = null;
	private JLabel lblPrecedent = null;
	private JLabel lblSuivant = null;
	private JLabel lblGO = null;
	private JTextField txtPseudo = null;
	private JLabel lblPersonnage = null;
	
	//--- autres propriétés privées ---
	private int numPerso ;
	private Controle controle ;
	private Son precedent ;
	private Son suivant ;
	private Son go ;
	private Son welcome ;
	
	//--- Constructeur ---
	public ChoixJoueur(Controle controle) {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		initialize();
		this.controle = controle ;
		txtPseudo.requestFocus() ;
		// affichage du premier personnage
		this.numPerso = 1 ;
		this.affichePerso() ;
		// les sons de la frame
		this.precedent = new Son(SONPRECEDENT) ;
		this.suivant = new Son(SONSUIVANT) ;
		this.go = new Son(SONGO) ;
		this.welcome = new Son(SONWELCOME) ;
		this.welcome.play() ;
	}
	
	//--- affichage du joueur ---
	private void affichePerso () {
		this.lblPersonnage.setIcon(new ImageIcon(PERSO+this.numPerso
				+MARCHE+"1d"+DROITE+EXTIMAGE)) ;
	}
	
	//--- clic sur la flèche de gauche ---
	private void lblPrecedent_clic () {
		this.precedent.play() ;
		this.numPerso = (this.numPerso + NBPERSOS + 1) % NBPERSOS + 1 ;
		this.affichePerso() ;
	}
	
	//--- clic sur la flèche de droite ---
	private void lblSuivant_clic () {
		this.suivant.play() ;
		this.numPerso = this.numPerso % NBPERSOS + 1 ;
		this.affichePerso() ;
	}
	
	//--- clic sur le bouton GO ---
	private void lblGO_clic () {
		if (this.txtPseudo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Le pseudo est obligatoire") ;
			this.txtPseudo.requestFocus() ;
		}else{
			this.go.play() ;
			this.controle.evenementVue(this, PSEUDO+SEPARE+this.txtPseudo.getText()
					+SEPARE+this.numPerso) ;
		}
	}
	
	//--- changement de l'aspect de la souris ---
	private void souris_doigt () {
		jContentPane.setCursor(new Cursor(Cursor.HAND_CURSOR)) ;
	}
	private void souris_normale () {
		jContentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)) ;
	}

	//************************************************************************
	// Methodes de la construction de la frame
	//************************************************************************
	private void initialize() {
		this.setSize(408, 302);
		this.setContentPane(getJContentPane());
		this.setTitle("Choix");
	}

	//--- Le panel principal ---
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblPersonnage = new JLabel();
			lblPersonnage.setBounds(new Rectangle(141, 114, 123, 122));
			lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
			lblPersonnage.setHorizontalTextPosition(SwingConstants.CENTER);
			lblPersonnage.setText("");
			lblGO = new JLabel();
			lblGO.setBounds(new Rectangle(315, 197, 56, 57));
			lblGO.setText("");
			lblGO.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseExited(java.awt.event.MouseEvent e) {    
					souris_normale() ;
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					souris_doigt() ;
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					lblGO_clic() ;
				}
			});
			lblSuivant = new JLabel();
			lblSuivant.setBounds(new Rectangle(300, 143, 28, 45));
			lblSuivant.setText("");
			lblSuivant.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(java.awt.event.MouseEvent e) {    
					souris_normale() ;
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					souris_doigt() ;
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					lblSuivant_clic() ;
				}
			});
			lblPrecedent = new JLabel();
			lblPrecedent.setBounds(new Rectangle(60, 143, 36, 52));
			lblPrecedent.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(java.awt.event.MouseEvent e) {    
					souris_normale() ;
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					souris_doigt() ;
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					lblPrecedent_clic() ;
				}
			});
			lblFond = new JLabel();
			lblFond.setBounds(new Rectangle(0, 0, 400, 275));
			lblFond.setIcon(new ImageIcon(FONDCHOIX)) ;
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblPersonnage, null);
			jContentPane.add(lblFond, null);
			jContentPane.add(lblPrecedent, null);
			jContentPane.add(lblSuivant, null);
			jContentPane.add(lblGO, null);
			jContentPane.add(getTxtPseudo(), null);
		}
		return jContentPane;
	}

	//--- txtPseudo ---
	private JTextField getTxtPseudo() {
		if (txtPseudo == null) {
			txtPseudo = new JTextField();
			txtPseudo.setBounds(new Rectangle(142, 247, 120, 22));
		}
		return txtPseudo;
	}

}

package modele;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import outils.connexion.Connection;

import controleur.Global;

public class Boule extends Objet implements Global {

	//--- propri�t�s ---
	private JeuServeur jeuServeur ;
	
	//--- constructeur ---
	public Boule (JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur ;
		// cr�ation de la boule
		super.label = new Label(Label.nbLabel++, new JLabel()) ;
		super.label.getJLabel().setHorizontalAlignment(SwingConstants.CENTER) ;
		super.label.getJLabel().setVerticalAlignment(SwingConstants.CENTER) ;
		super.label.getJLabel().setBounds(new Rectangle(0, 0, L_BOULE, H_BOULE)) ;
		super.label.getJLabel().setIcon(new ImageIcon(BOULE)) ;
		this.label.getJLabel().setVisible(false) ;
		// int�gration de la boule dans l'ar�ne
		this.jeuServeur.nouveauLabelJeu(super.label) ;
	}
	
	//--- tire la boule ---
	public void tireBoule (Joueur attaquant, ArrayList<Mur> lesmurs, 
			Hashtable<Connection, Joueur> lesjoueurs) {
		// positionnement de la boule au d�part
		if (attaquant.getOrientation()==GAUCHE) {
			super.posX = attaquant.posX - L_BOULE - 1 ;
		}else{
			super.posX = attaquant.posX + L_PERSO + 1 ;
		}
		super.posY = attaquant.posY + H_PERSO/2 ;
		// la boule est lanc�e
		new Attaque (attaquant, this.jeuServeur, lesmurs, lesjoueurs) ;
	}
	
}

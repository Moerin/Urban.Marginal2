package modele;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

public class Mur extends Objet implements Global {

	//--- Constructeur ---
	public Mur () {
		super.posX = (int) Math.round(Math.random() * (L_ARENE - L_MUR)) ;
		super.posY = (int) Math.round(Math.random() * (H_ARENE - H_MUR)) ;
		// -1 car pas besoin de numérotation spécifique pour les murs
		super.label = new Label(-1, new JLabel()) ;
		super.label.getJLabel().setHorizontalAlignment(SwingConstants.CENTER) ;
		super.label.getJLabel().setVerticalAlignment(SwingConstants.CENTER) ;
		super.label.getJLabel().setBounds(new Rectangle(super.posX, super.posY, L_MUR, H_MUR)) ;
		super.label.getJLabel().setIcon(new ImageIcon(MUR)) ;
	}
	
}

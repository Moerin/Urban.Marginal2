package modele;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

import outils.connexion.Connection;

import controleur.Global;

public class Attaque extends Thread implements Global {

	//--- propriétés ---
	private Joueur attaquant ;			// celui qui lance la boule
	private JeuServeur jeuServeur ;		// pour accéder à l'envoi
	private ArrayList<Mur> lesmurs ;	// la collection de murs
	private Hashtable<Connection, Joueur> lesjoueurs ;	// dictionnaire de joueurs
	
	//--- constructeur ---
	public Attaque (Joueur attaquant, JeuServeur jeuServeur, ArrayList<Mur> lesmurs,
			Hashtable<Connection, Joueur> lesjoueurs) {
		this.attaquant = attaquant ;
		this.jeuServeur = jeuServeur ;
		this.lesmurs = lesmurs ;
		this.lesjoueurs = lesjoueurs ;
		super.start() ;
	}
	
	//--- test si la boule touche un autre joueur ---
	private Joueur toucheJoueur () {
		for (Joueur unjoueur : this.lesjoueurs.values()) {
			if (this.attaquant.getBoule().toucheObjet(unjoueur)) {
				return unjoueur ;
			}
		}
		return null ;
	}
	
	//--- collision de la boule avec un mur ---
	private boolean toucheMur () {
		for (Mur unmur : this.lesmurs) {
			if (this.attaquant.getBoule().toucheObjet(unmur)) {
				return true ;
			}
		}
		return false ;
	}
	
	//--- pause ---
	private void Pause (long milli, int nano) {
		try {
			Thread.sleep(milli, nano) ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//--- run ---
	public void run () {
		this.attaquant.affiche(MARCHE, 1) ;
		Boule laboule = this.attaquant.getBoule();
		Joueur victime = null ;
		int orientation = this.attaquant.getOrientation() ;
		// la boule devient visible
		laboule.getLabel().getJLabel().setVisible(true) ;
		// boucle sur l'avancée de la boule
		do {
			// la boule avance
			if (orientation==GAUCHE) {
				laboule.setPosX(laboule.getPosX()-LEPAS) ;
			}else{
				laboule.setPosX(laboule.getPosX()+LEPAS) ;				
			}
			// affichage de la boule
			laboule.getLabel().getJLabel().setBounds(new Rectangle(laboule.getPosX(), 
					laboule.getPosY(), L_BOULE, H_BOULE)) ;
//			Pause(10,0) ;
			// envoi de la nouvelle position
			this.jeuServeur.envoi(laboule.getLabel()) ;
			victime = this.toucheJoueur() ;
		}while (laboule.getPosX()>=0 && laboule.getPosX()<=L_ARENE 
				&& !this.toucheMur() && victime==null) ;
		// si un joueur est touché
		if (victime!=null && !victime.estMort()) {
			this.jeuServeur.envoi(HURT) ;
			victime.perteVie() ;
			this.attaquant.gainVie() ;
			// affichage des images du joueur blessé
			for (int k=1 ; k<=NBETATSBLESSE ; k++) {
				victime.affiche(BLESSE, k) ;
				Pause (80, 0) ;
			}
			// si le joueur meurt
			if (victime.estMort()) {
				this.jeuServeur.envoi(DEATH) ;
				// affichage des images du joueur en train de mourir
				for (int k=1 ; k<=NBETATSMORT ; k++) {
					victime.affiche(MORT, k) ;
					Pause (80, 0) ;
				}				
			}else{
				victime.affiche(MARCHE, 1) ;
			}
			this.attaquant.affiche(MARCHE, 1) ;
		}
		// la boule est à nouveau invisible (envoyé aussi à tout le monde)
		laboule.getLabel().getJLabel().setVisible(false) ;
		this.jeuServeur.envoi(laboule.getLabel()) ;
	}
}

package modele;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import outils.connexion.Connection;

import controleur.Global;

public class Joueur extends Objet implements Global {

	//--- Constantes de classes ---
	private static final int 
		maxvie = 10,			// vie de départ de chaque joueur
		gain = 1,				// nombre de points gagnés à chaque attaque
		perte = 2 ;				// nombre de points perdus à chaque attaque
	
	//--- Propriétés ---
	private String pseudo ;		// pseudo du joueur
	private int numPerso ;		// numéro du personnage sélectionné
	private Label message ;		// le label pour afficher le pseudo et la vie
	private JeuServeur jeuServeur ;
	private int vie ;			// vie restante du joueur
	private int orientation ;	// tourné vers la gauche (0) ou la droite (1)
	private int etape ;			// numéro d'étape dans l'animation
	private Boule boule ;		// la boule d'attaque du joueur
	
	//--- Constructeur ---
	public Joueur (JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur ;
		this.vie = maxvie ;
		this.etape = 1 ;
		this.orientation = DROITE ;
	}
	
	//--- getters ---
	public Label getMessage () {return this.message ;}
	public String getPseudo () {return this.pseudo ;}
	public Boule getBoule () {return this.boule ;}
	public int getOrientation () {return this.orientation ;}
	
	//--- gain et perte de vie lors d'une attaque ---
	public void gainVie () {this.vie += gain ;}
	public void perteVie () {this.vie = Math.max(0, this.vie - perte) ;}
	
	//--- pour savoir si le joueur est mort ---
	public boolean estMort () {return this.vie==0 ;}
	
	//--- setter sur le pseudo et le numéro du personnage ---
	public void initPerso (String pseudo, int numPerso, Hashtable<Connection, Joueur> lesjoueurs, 
			ArrayList<Mur> lesmurs) {
		// récupération du pseudo et du numéro de personnage
		this.pseudo = pseudo ;
		this.numPerso = numPerso ;
		// préparation du label du personnage
		super.label = new Label(Label.nbLabel++, new JLabel()) ;
		super.label.getJLabel().setHorizontalAlignment(SwingConstants.CENTER) ;
		super.label.getJLabel().setVerticalAlignment(SwingConstants.CENTER) ;
		this.jeuServeur.nouveauLabelJeu(super.label) ;
		// préparation du message du personnage
		this.message = new Label(Label.nbLabel++, new JLabel()) ;
		this.message.getJLabel().setHorizontalAlignment(SwingConstants.CENTER) ;
		this.message.getJLabel().setFont(new Font("Dialog", Font.PLAIN, 8)) ;
		this.jeuServeur.nouveauLabelJeu(this.message) ;
		// création de la boule
		this.boule = new Boule(this.jeuServeur) ;
		// calcul de la premiere position aléatoire
		this.premierePosition(lesjoueurs, lesmurs) ;
		// affichage et envoi à tous du personnage (label + message)
		this.affiche(MARCHE,this.etape) ;
		// envoi de la boule à tous
		this.jeuServeur.envoi(this.boule.getLabel()) ;
	}
	
	//--- touche un autre joueur ---
	private boolean toucheJoueur (Hashtable<Connection, Joueur> lesjoueurs) {
		for (Joueur unjoueur : lesjoueurs.values()) {
			// il ne faut pas tester le joueur avec lui-même
			if (!unjoueur.equals(this)) {
				if (super.toucheObjet(unjoueur)) {
					return true ;
				}
			}
		}
		return false ;
	}
	
	//--- touche un mur ---
	private boolean toucheMur (ArrayList<Mur> lesmurs) {
		for (Mur unmur : lesmurs) {
			if (super.toucheObjet(unmur)) {
				return true ;
			}
		}
		return false ;
	}
	
	//--- premier positionnement aléatoire du joueur ---
	private void premierePosition (Hashtable<Connection, Joueur> lesjoueurs, 
			ArrayList<Mur> lesmurs) {
		// définir la taille de l'image pour le calcul des collisions
		this.label.getJLabel().setBounds(new Rectangle(0, 0, L_PERSO, H_PERSO)) ;
		do {
			// calcul aléatoire de la position
			super.posX = (int) Math.round(Math.random() * (L_ARENE - L_PERSO)) ;
			super.posY = (int) Math.round(Math.random() * (H_ARENE - H_PERSO - H_MESSAGE)) ;
		}while (this.toucheMur(lesmurs) || this.toucheJoueur(lesjoueurs)) ;
	}
	
	//--- gestion d'une action reçue ---
	public void action (int action, Hashtable<Connection, Joueur> lesjoueurs, 
			ArrayList<Mur> lesmurs) {
		switch (action) {
			case GAUCHE :
				this.posX = this.deplace(action, this.posX, GAUCHE, -LEPAS, 
						L_ARENE - L_PERSO, lesjoueurs, lesmurs) ;
				break ;
			case DROITE :
				this.posX = this.deplace(action, this.posX, DROITE, LEPAS, 
						L_ARENE - L_PERSO, lesjoueurs, lesmurs) ;
				break ;
			case HAUT :
				this.posY = this.deplace(action, this.posY, this.orientation, 
						-LEPAS, H_ARENE - H_PERSO - H_MESSAGE, lesjoueurs, lesmurs) ;
				break ;
			case BAS :
				this.posY = this.deplace(action, this.posY, this.orientation, 
						LEPAS, H_ARENE - H_PERSO - H_MESSAGE, lesjoueurs, lesmurs) ;
				break ;
			case TIRE :
				if (!this.boule.getLabel().getJLabel().isVisible()) {
					this.jeuServeur.envoi(FIGHT) ;
					this.boule.tireBoule(this, lesmurs, lesjoueurs) ;
				}
				break ;				
		}
		this.affiche(MARCHE, this.etape) ;
	}
	
	//--- gestion du déplacement ---
	private int deplace (int action, int position, int orientation, int lepas, int max, 
			Hashtable<Connection, Joueur> lesjoueurs, ArrayList<Mur> lesmurs) {
		this.orientation = orientation ;
		int ancpos = position ;
		// nouvelle position
		position += lepas ;
		// la position ne doit pas sortir de l'arène
		if (position<0) {position = 0 ;}
		if (position>max) {position = max ;}
		// position affectée aux coordonnées pour ensuite la recherche de collisions
		if (action==GAUCHE || action==DROITE) {
			this.posX = position ;
		}else{
			this.posY = position ;
		}
		// dans le cas d'une collision, retour à l'ancienne position
		if (this.toucheMur(lesmurs) || this.toucheJoueur(lesjoueurs)) {
			position = ancpos ;
		}
		// changement d'étape pour l'animation de la marche
		this.etape = this.etape%NBETATSMARCHE + 1 ;
		return position ;
	}
	
	//--- départ d'un joueur ---
	public void departJoueur () {
		if (super.label!=null) {
			// les 3 labels du joueur sont rendus invisibles
			super.label.getJLabel().setVisible(false) ;
			this.message.getJLabel().setVisible(false) ;
			this.boule.getLabel().getJLabel().setVisible(false) ;
			// les labels invisibles sont envoyés à tous
			this.jeuServeur.envoi(super.label) ;
			this.jeuServeur.envoi(this.message) ;
			this.jeuServeur.envoi(this.boule.getLabel()) ;
		}
	}
	
	//--- affichage du personnage ---
	public void affiche (String etat, int etape) {
		// gestion du personnage
		this.label.getJLabel().setBounds(new Rectangle(this.posX, this.posY, L_PERSO, H_PERSO)) ;
		this.label.getJLabel().setIcon(new ImageIcon(PERSO+this.numPerso+etat+etape
				+"d"+this.orientation+EXTIMAGE)) ;
		// gestion du message
		this.message.getJLabel().setBounds(new Rectangle(this.posX-10, this.posY+H_PERSO, 
				L_PERSO+20, H_MESSAGE)) ;
		this.message.getJLabel().setText(this.pseudo + " : " + this.vie) ;		
		// envoi des Labels du joueur à tous pour que tous les clients les affichent aussi
		this.jeuServeur.envoi(super.label) ;
		this.jeuServeur.envoi(this.message) ;
	}
}

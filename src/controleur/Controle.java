package controleur;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import modele.Label;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;

public class Controle implements Global {

	//--- Propriétés ---
	private EntreeJeu frmEntreeJeu ;
	private Arene frmArene ;
	private ChoixJoueur frmChoixJoueur ;
	private Jeu lejeu ;
	private Connection connection ;
	
	//--- Constructeur ---
	public Controle () {
		this.frmEntreeJeu = new EntreeJeu(this) ;
		this.frmEntreeJeu.setVisible(true) ;
	}
	
	//--- Main ---
	public static void main(String[] args) {
		new Controle() ;
	}
	
	//--- Getter ---
	public Jeu getLejeu () {return this.lejeu ;}
	
	//--- Setter sur la connection ---
	public void setConnection (Connection connection) {
		this.connection = connection ;
		// si c'est un jeu Serveur, il faut lui envoyer de suite la connection du client
		if (this.lejeu instanceof JeuServeur) {
			this.lejeu.setConnection(connection) ;
		}
	}

	//****************************************************************************
	// Evénements provenant de la vue
	//****************************************************************************
	public void evenementVue (Object uneframe, Object info) {
		if (uneframe instanceof EntreeJeu) {evenementEntreeJeu(info) ;} ;
		if (uneframe instanceof ChoixJoueur) {evenementChoixJoueur(info) ;} ;
		if (uneframe instanceof Arene) {evenementArene(info) ;} ;
	}
	
	//--- Réception d'un événement de EntreeJeu (client ou serveur) ---
	private void evenementEntreeJeu (Object info) {
		if ((String)info == "serveur") {
			new ServeurSocket(this, PORT) ;
			this.lejeu = new JeuServeur(this) ;
			// fermeture de la 1ere frame
			this.frmEntreeJeu.dispose() ;
			// création de l'arène
			this.frmArene = new Arene(this, "serveur") ;
			((JeuServeur)this.lejeu).constructionMurs() ;
			this.frmArene.setVisible(true) ;
		}else{
			// si c'est un jeu client, test de la connexion
			if ((new ClientSocket((String)info, PORT, this)).getEtatConnexion()) {
				this.lejeu = new JeuClient(this) ;
				this.lejeu.setConnection(this.connection) ;
				// création de l'arène
				this.frmArene = new Arene(this, "client") ;
				// fermeture de la 1ere frame
				this.frmEntreeJeu.dispose() ;
				// création et affichage de la frame du choix
				this.frmChoixJoueur = new ChoixJoueur(this) ;
				this.frmChoixJoueur.setVisible(true) ;		
			}
		}
	}
	
	//--- Réception d'un événement de ChoixJoueur (client) ---
	private void evenementChoixJoueur (Object info) {
		((JeuClient)lejeu).envoi(info) ;
		this.frmChoixJoueur.dispose() ;
		this.frmArene.setVisible(true) ;
	}
	
	//--- Réception d'un événement de l'Arene (client uniquement) ---
	private void evenementArene (Object info) {
		((JeuClient)this.lejeu).envoi(info) ;
	}
	
	//****************************************************************************
	// Evénements provenant du modele
	//****************************************************************************
	public void evementModele (Object unjeu, String ordre, Object info) {
		if (unjeu instanceof JeuServeur) {evenementJeuServeur(ordre, info) ;} ;
		if (unjeu instanceof JeuClient) {evenementJeuClient(ordre, info) ;} ;
	}
	
	//--- Evénement provenant du serveur ---
	private void evenementJeuServeur (String ordre, Object info) {
		
		// Ajout d'un nouveau mur dans l'arène
		if (ordre == "ajout mur") {
			this.frmArene.ajoutMur((JLabel)info) ;
		}
	
		// Ajout d'un nouveau personnage dans l'arène
		if (ordre == "ajout joueur") {
			this.frmArene.ajoutJoueur((JLabel)info) ;
		}
		
		// Envoi du panel des murs au client
		if (ordre == "envoi panel murs") {
			((JeuServeur)this.lejeu).envoi((Connection)info, this.frmArene.getJpnMurs()) ;
		}
		
		// Ajout d'une phrase dans le chat
		if (ordre == "ajout phrase") {
			this.frmArene.ajoutChat((String)info) ;
			((JeuServeur)this.lejeu).envoi(this.frmArene.getTxtChat().getText()) ;
		}
		
	}
	
	//--- Evénement provenant du client ---
	private void evenementJeuClient (String ordre, Object info) {
		
		// Ajout d'un panel complet de murs dans l'arène
		if (ordre == "ajout panel murs") {
			this.frmArene.ajoutPanelMurs((JPanel)info) ;
		}
		
		// Ajout d'un label du jeu (personnage ou message)
		if (ordre == "ajout joueur") {
			this.frmArene.ajoutmodifJoueur(((Label)info).getNumLabel(), ((Label)info).getJLabel()) ;
		}
		
		// Remplacement de la zone d'affichage de la conversation
		if (ordre == "remplace chat") {
			this.frmArene.remplaceChat((String)info) ;
		}
		
		// Demande de jouer un son ordonné par le serveur
		if (ordre == "son") {
			this.frmArene.joueSon((Integer)info) ;
		}
	}
	
}

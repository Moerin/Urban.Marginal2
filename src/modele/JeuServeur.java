package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import outils.connexion.Connection;
import controleur.Controle;
import controleur.Global;

public class JeuServeur extends Jeu implements Global {

	//--- Proproétés privées ---
	private Hashtable<Connection, Joueur> lesjoueurs = new Hashtable<Connection, Joueur>() ;
	private ArrayList<Mur> lesmurs = new ArrayList<Mur>() ;
	private ArrayList<Joueur> lesJoueursDansLordre = new ArrayList<Joueur>() ;
	
	//--- Constructeur ---
	public JeuServeur (Controle controle) {
		super.controle = controle ;
		Label.nbLabel = 0 ;
	}
	
	//--- Construction des murs ---
	public void constructionMurs () {
		for (int k=0 ; k<NBMURS ; k++) {
			this.lesmurs.add(new Mur()) ;
			this.controle.evementModele(this, "ajout mur", this.lesmurs.get(k).getLabel().getJLabel()) ;
		}
	}

	//--- Nouveau label du jeu (donc ajout dans le panel du jeu) ---
	public void nouveauLabelJeu (Label label) {
		super.controle.evementModele(this, "ajout joueur", label.getJLabel()) ;
	}
	
	//--- Déconnexion d'un des joueurs ---
	@Override
	public void deconnection(Connection connection) {
		// les labels du joueur sont rendus invisibles
		this.lesjoueurs.get(connection).departJoueur() ;
		// le joueur est supprimé du dictionnaire
		this.lesjoueurs.remove(connection) ;
	}

	//--- Réception d'un objet provenant d'un client ---
	@Override
	public void reception(Connection connection, Object info) {
		// traitement de l'information reçue
		String[] infos = ((String)info).split(SEPARE) ;
		switch(Integer.parseInt(infos[0])) {
			case PSEUDO :		// réception du choix du pseudo et du numéro du personnage
				// envoi des murs au nouveau
				this.controle.evementModele(this, "envoi panel murs", connection) ;
				// envoi des anciens joueurs au nouveau
				for (Joueur unjoueur : this.lesJoueursDansLordre) {
					super.envoi(connection, unjoueur.getLabel()) ;
					super.envoi(connection, unjoueur.getMessage()) ;
					super.envoi(connection, unjoueur.getBoule().getLabel()) ;
				}
				// initialisation des labels du personnage et de sa position aléatoire
				this.lesjoueurs.get(connection).initPerso(infos[1], Integer.parseInt(infos[2]),
						lesjoueurs, lesmurs) ;
				this.lesJoueursDansLordre.add(this.lesjoueurs.get(connection)) ;
				// envoi de la phrase de bienvenue (t'chat)
				String laphrase = "***"+this.lesjoueurs.get(connection).getPseudo()
				+" vient de se connecter ***" ;
				this.controle.evementModele(this, "ajout phrase", laphrase) ;
				break ;
			case CHAT :			// réception d'une phrase du t'chat
				laphrase = this.lesjoueurs.get(connection).getPseudo()+">"+infos[1] ;
				this.controle.evementModele(this, "ajout phrase", laphrase) ;
				break ;
			case ACTION :		// réception d'une action (déplacement ou tir)
				if (!this.lesjoueurs.get(connection).estMort()) {
					this.lesjoueurs.get(connection).action(Integer.parseInt(infos[1]), 
							lesjoueurs, lesmurs) ;
				}
		}
		
	}

	//--- reception d'un nouveau joueur ---
	@Override
	public void setConnection(Connection connection) {
		this.lesjoueurs.put(connection, new Joueur(this)) ;
	}

	//--- envoi à tous les clients ---
	public void envoi (Object info) {
		for (Connection unecle : this.lesjoueurs.keySet()) {
			super.envoi(unecle, info) ;
		}
	}
	
}

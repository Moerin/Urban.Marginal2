package modele;

import javax.swing.JPanel;

import controleur.Controle;
import outils.connexion.Connection;

public class JeuClient extends Jeu {

	//--- propri�t�s ---
	private Connection connection ;
	
	//--- Constructeur ---
	public JeuClient (Controle controle) {
		super.controle = controle ;
	}
	
	//--- D�connexion du serveur ---
	@Override
	public void deconnection(Connection connection) {
		System.exit(0) ;
		
	}

	//--- R�ception d'objets provenant du serveur ---
	@Override
	public void reception(Connection connection, Object info) {
		if (info instanceof JPanel) {
			super.controle.evementModele(this, "ajout panel murs", info) ;
		}else{
			if (info instanceof Label) {
				super.controle.evementModele(this, "ajout joueur", info) ;
			}else{
				if (info instanceof String) {
					super.controle.evementModele(this, "remplace chat", info) ;
				}else{
					if (info instanceof Integer) {
						super.controle.evementModele(this, "son", info) ;
					}
				}
			}
		}
		
	}

	//--- r�ception de l'objet de connexion pour �tre en liaison avec le serveur ---
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection ;	
	}
	
	//--- Envoi vers le serveur ---
	public void envoi (Object info) {
		super.envoi(this.connection, info) ;
	}

}

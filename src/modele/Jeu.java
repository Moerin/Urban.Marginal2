package modele;

import outils.connexion.Connection;
import controleur.Controle;

public abstract class Jeu {

	//--- Propriétés ---
	protected Controle controle ;
	
	//--- Récupération de l'objet de connection ---
	public abstract void setConnection (Connection connection) ;
	
	//--- Réception ---
	public abstract void reception (Connection connection, Object info) ;
	
	//--- Envoi ---
	public void envoi (Connection connection, Object info) {
		connection.envoi(info) ;
	}
	
	//--- Déconnection ---
	public abstract void deconnection (Connection connection) ;
	
}

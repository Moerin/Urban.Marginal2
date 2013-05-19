package modele;

import outils.connexion.Connection;
import controleur.Controle;

public abstract class Jeu {

	//--- Propri�t�s ---
	protected Controle controle ;
	
	//--- R�cup�ration de l'objet de connection ---
	public abstract void setConnection (Connection connection) ;
	
	//--- R�ception ---
	public abstract void reception (Connection connection, Object info) ;
	
	//--- Envoi ---
	public void envoi (Connection connection, Object info) {
		connection.envoi(info) ;
	}
	
	//--- D�connection ---
	public abstract void deconnection (Connection connection) ;
	
}

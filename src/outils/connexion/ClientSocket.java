package outils.connexion;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ClientSocket {
	
	//--- Propri�t�s ---
	private boolean connexionOK ;

	//--- Constructeur ---
	public ClientSocket (String ip, int port, Object lerecepteur) {
		this.connexionOK = false ;
		Socket socket ;
		try {
			socket = new Socket (ip, port) ;
			System.out.println("connexion au serveur r�ussie") ;
			this.connexionOK = true ;
			new Connection(socket, lerecepteur) ;
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "serveur non disponible") ;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "probl�me de connexion (IP incorrecte ?)") ;
		}
	}
	
	//--- Getter ---
	public boolean getEtatConnexion () {return this.connexionOK ; }
	
}

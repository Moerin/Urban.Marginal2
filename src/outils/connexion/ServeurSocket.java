package outils.connexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurSocket extends Thread {
	
	//--- propriétés ---
	private Object lerecepteur ;
	private ServerSocket serversocket ;
	
	//--- Constructeur ---
	public ServeurSocket (Object lerecepteur, int port) {
		this.lerecepteur = lerecepteur ;
		try {
			this.serversocket = new ServerSocket(port) ;
		} catch (IOException e) {
			System.out.println("erreur creation socket serveur : "+e) ;
			System.exit(0) ;
		}
		super.start();
	}
	
	//--- Run : à l'écoute de l'arrivée d'un nouveau client ---
	public void run() {
		Socket socket ;
		// boucle d'attente d'un nouveau client
		while (true) {
			try {
				System.out.println("Le serveur attend") ;
				socket = this.serversocket.accept() ;
				System.out.println("un client s'est connecté") ;
				new Connection(socket, this.lerecepteur) ;
			} catch (IOException e) {
				System.out.println("erreur sur l'attente d'un client"+e) ;
				System.exit(0) ;
			}
		}
	}

}

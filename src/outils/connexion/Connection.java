package outils.connexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Connection extends Thread {

	//--- Propriétés ---
	private Object lerecepteur ;
	private ObjectInputStream in ;
	private ObjectOutputStream out ;
	
	//--- Constructeur ---
	public Connection (Socket socket, Object lerecepteur) {
		this.lerecepteur = lerecepteur ;
		// création du canal de sortie
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream()) ;
		} catch (IOException e) {
			System.out.println("erreur de création du canal de sortie"+e) ;
			System.exit(0) ;
		}
		// création du canal d'entrée
		try {
			this.in = new ObjectInputStream(socket.getInputStream()) ;
		} catch (IOException e) {
			System.out.println("erreur de création du canal d'entrée"+e) ;
			System.exit(0) ;
		}
		// démarrage du processus (run)
		super.start() ;
		// envoi de l'objet Connection au récepteur
		((controleur.Controle)lerecepteur).setConnection(this) ;
	}
	
	//--- envoi d'un objet ---
	public synchronized void envoi (Object unobjet) {
		try {
			this.out.reset() ;
			this.out.writeObject(unobjet) ;
			this.out.flush() ;
		} catch (IOException e) {
			System.out.println("erreur out :"+e) ;
		}
	}
	
	//--- Run : écoute les messages entrants provenant de l'ordi distant ---
	public void run() {
		boolean inOk = true ;
		Object reception ;
		// boucle d'attente d'un message de l'ordi distant
		while (inOk) {
			try {
				reception = this.in.readObject() ;				
				// traitement de l'objet reçu
				((controleur.Controle)this.lerecepteur).getLejeu().reception(this, reception) ;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "L'ordi distant s'est déconnecté") ;
				inOk = false ;
				((controleur.Controle)this.lerecepteur).getLejeu().deconnection(this) ;
				try {
					this.in.close() ;
				} catch (IOException e1) {
					System.out.println("erreur de fermeture du in :"+e) ;
				}
			} catch (ClassNotFoundException e) {
				System.out.println("erreur de réception sur le type d'objet :"+e) ;
				System.exit(0) ;
			}
		}
	}
	
}

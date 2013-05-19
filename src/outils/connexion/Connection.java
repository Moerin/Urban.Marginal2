package outils.connexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Connection extends Thread {

	//--- Propri�t�s ---
	private Object lerecepteur ;
	private ObjectInputStream in ;
	private ObjectOutputStream out ;
	
	//--- Constructeur ---
	public Connection (Socket socket, Object lerecepteur) {
		this.lerecepteur = lerecepteur ;
		// cr�ation du canal de sortie
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream()) ;
		} catch (IOException e) {
			System.out.println("erreur de cr�ation du canal de sortie"+e) ;
			System.exit(0) ;
		}
		// cr�ation du canal d'entr�e
		try {
			this.in = new ObjectInputStream(socket.getInputStream()) ;
		} catch (IOException e) {
			System.out.println("erreur de cr�ation du canal d'entr�e"+e) ;
			System.exit(0) ;
		}
		// d�marrage du processus (run)
		super.start() ;
		// envoi de l'objet Connection au r�cepteur
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
	
	//--- Run : �coute les messages entrants provenant de l'ordi distant ---
	public void run() {
		boolean inOk = true ;
		Object reception ;
		// boucle d'attente d'un message de l'ordi distant
		while (inOk) {
			try {
				reception = this.in.readObject() ;				
				// traitement de l'objet re�u
				((controleur.Controle)this.lerecepteur).getLejeu().reception(this, reception) ;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "L'ordi distant s'est d�connect�") ;
				inOk = false ;
				((controleur.Controle)this.lerecepteur).getLejeu().deconnection(this) ;
				try {
					this.in.close() ;
				} catch (IOException e1) {
					System.out.println("erreur de fermeture du in :"+e) ;
				}
			} catch (ClassNotFoundException e) {
				System.out.println("erreur de r�ception sur le type d'objet :"+e) ;
				System.exit(0) ;
			}
		}
	}
	
}

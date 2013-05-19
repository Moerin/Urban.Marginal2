package vue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JTextField;

import controleur.Controle;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EntreeJeu extends JFrame {

	//--- Propriétés des objets graphiques ---
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel lblServeur = null;
	private JButton cmdServeur = null;
	private JTextField txtIP = null;
	private JLabel lblIP = null;
	private JLabel lblClient = null;
	private JButton cmdClient = null;
	private JButton cmdQuitter = null;

	//--- Autres propriétés ---
	private Controle controle ;
	
	//--- Constructeur ---
	public EntreeJeu(Controle controle) {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		this.controle = controle ;
		initialize();
	}

	//--- clic sur le bouton Demarrer ---
	private void cmdServeur_clic () {
		this.controle.evenementVue(this, "serveur") ;
	}

	//--- clic sur le bouton Connecter ---
	private void cmdClient_clic () {
		this.controle.evenementVue(this, this.txtIP.getText()) ;
		// dans le cas où la connexion se passerait mal
		this.txtIP.setText("") ;
		this.txtIP.requestFocus() ;
	}

	//--- clic sur le bouton Quitter ---
	private void cmdQuitter_clic () {
		System.exit(0) ;
	}

//************************************************************************
// Methodes de la construction de la frame
//************************************************************************
	private void initialize() {
		this.setSize(300, 176);
		this.setContentPane(getJContentPane());
		this.setTitle("Urban Marginal");
	}

	//--- Le panel principal ---
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblClient = new JLabel();
			lblClient.setBounds(new Rectangle(7, 46, 268, 28));
			lblClient.setText("Je veux me connecter à un serveur existant :");
			lblIP = new JLabel();
			lblIP.setBounds(new Rectangle(7, 78, 80, 26));
			lblIP.setText("IP serveur :");
			lblServeur = new JLabel();
			lblServeur.setBounds(new Rectangle(7, 7, 173, 28));
			lblServeur.setText("Je veux être un serveur :");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblServeur, null);
			jContentPane.add(getCmdServeur(), null);
			jContentPane.add(getTxtIP(), null);
			jContentPane.add(lblIP, null);
			jContentPane.add(lblClient, null);
			jContentPane.add(getCmdClient(), null);
			jContentPane.add(getCmdQuitter(), null);
		}
		return jContentPane;
	}

	//--- cmdServeur ---
	private JButton getCmdServeur() {
		if (cmdServeur == null) {
			cmdServeur = new JButton();
			cmdServeur.setBounds(new Rectangle(187, 8, 98, 26));
			cmdServeur.setText("Démarrer");
			cmdServeur.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					cmdServeur_clic() ;
				}
			});
		}
		return cmdServeur;
	}

	//--- txtIP ---
	private JTextField getTxtIP() {
		if (txtIP == null) {
			txtIP = new JTextField();
			txtIP.setBounds(new Rectangle(87, 78, 97, 26));
			txtIP.setText("127.0.0.1");
		}
		return txtIP;
	}

	//--- cmdClient ---
	private JButton getCmdClient() {
		if (cmdClient == null) {
			cmdClient = new JButton();
			cmdClient.setBounds(new Rectangle(188, 78, 97, 25));
			cmdClient.setText("Connecter");
			cmdClient.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					cmdClient_clic() ;
				}
			});
		}
		return cmdClient;
	}

	//--- cmdQuitter ---
	private JButton getCmdQuitter() {
		if (cmdQuitter == null) {
			cmdQuitter = new JButton();
			cmdQuitter.setBounds(new Rectangle(187, 122, 98, 23));
			cmdQuitter.setText("Quitter");
			cmdQuitter.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					cmdQuitter_clic() ;
				}
			});
		}
		return cmdQuitter;
	}

}

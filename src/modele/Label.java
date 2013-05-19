package modele;

import java.io.Serializable ;

import javax.swing.JLabel;

public class Label implements Serializable {

	//--- propriété statique ---
	public static int nbLabel ;
	
	//--- prorpiétés privées ---
	private int numLabel ;
	private JLabel jLabel ;
	
	//--- constructeur ---
	public Label (int numLabel, JLabel jLabel) {
		this.numLabel = numLabel ;
		this.jLabel = jLabel ;
	}
	
	//--- Getters ---
	public int getNumLabel () {return this.numLabel ;}
	public JLabel getJLabel () {return this.jLabel ;}
	
}

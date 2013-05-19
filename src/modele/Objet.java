package modele;

public abstract class Objet {

	//--- propriétés ---
	protected int posX, posY ;		// position de l'objet
	protected Label label ;			// le label de l'objet (donc l'image et son numéro)
	
	//--- getters ---
	public int getPosX () {return this.posX ;}
	public int getPosY () {return this.posY ;}
	public Label getLabel () {return this.label ;}
	
	//--- setters ---
	public void setPosX (int posX) {this.posX = posX ;}
	public void setPosY (int posY) {this.posY = posY ;}
	
	//--- Collision de 2 objets ---
	public boolean toucheObjet (Objet objet) {
		// pas de recherche de collision si l'objet ou son label sont null
		if (objet.label==null) {
			return false ;
		}else{
			if (objet.label.getJLabel()==null) {
				return false ;
			}else{
				int l_obj  = objet.label.getJLabel().getWidth() ;
				int h_obj  = objet.label.getJLabel().getHeight() ;
				int l_this = this.label.getJLabel().getWidth() ;
				int h_this = this.label.getJLabel().getHeight() ;
				return (!((this.posX+l_this<objet.posX || this.posX>objet.posX+l_obj) ||
						(this.posY+h_this<objet.posY || this.posY>objet.posY+h_obj))) ;
			}
		}
	}
	
}

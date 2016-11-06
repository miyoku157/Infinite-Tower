package Decor;

public class Portail extends Decor {
	private Portail link;
	private int lien;

	public Portail(float posX, float posY,float _largeur,float _longueur,int _linknumber) {
		super(posX, posY,_longueur,_largeur);
		super.setType("portail");
		lien=_linknumber;
		// TODO Auto-generated constructor stub
	}
	public Portail getLink() {
		return link;
	}

	public void setLink(Portail link) {
		this.link = link;
	}

	/**
	 * @return the lien
	 */
	public int getLien() {
		return lien;
	}
	/**
	 * @param lien the lien to set
	 */
	public void setLien(int lien) {
		this.lien = lien;
	}

}

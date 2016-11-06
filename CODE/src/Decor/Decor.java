package Decor;

import Texture.Texture;

// Cette classe est la classe parente de toutes les classes représentant 
// des éléments de décor.

public abstract class Decor {
	
	// Les attributs communs à tous les éléments de décor : la position sur l'axe 
	// horizontal et sur l'axe vertical.
	private float positionX;
	private float positionY;
	private float longueur;
	private float largeur;
	private Texture texture;
	private String type;
	// Constructeur
	public Decor (float posX, float posY,float _longueur,float _largeur)
	{
		this.setPositionX(posX);
		this.setPositionY(posY);
		this.setLongueur(_longueur);
		this.setLargeur(_largeur);
	}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}
	
	public float getCentreX()
	{
		return positionX + largeur / 2;
	}
	public float getCentreY()
	{
		return positionY + longueur / 2;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the longueur
	 */
	public float getLongueur() {
		return longueur;
	}

	/**
	 * @param longueur the longueur to set
	 */
	public void setLongueur(float longueur) {
		this.longueur = longueur;
	}

	/**
	 * @return the largeur
	 */
	public float getLargeur() {
		return largeur;
	}

	/**
	 * @param largeur the largeur to set
	 */
	public void setLargeur(float largeur) {
		this.largeur = largeur;
	}

	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
}

package Decor;

import Class.Editeur;

// La classe Mur sert à représenter tous les objets qui constituent des obstacles
// physiques bloquants pour la bille.

public class Mur extends Decor
{
	public int Id;
	
	// Constructeur
	public Mur(float posX, float posY, float _longueur, float _largeur,  int _Id)
	{
		super(posX, posY,_longueur,_largeur);
		super.setType("mur");
		this.Id = _Id;
	}
	
	public boolean isNextTo(float posX, float posY)
	{
		boolean result = false;
		
		if(this.getPositionX() - posX <= 1 && this.getPositionY() - posY <= 1)
		{
			result = true;
		}
			
		return result;
	}
	
	public static Mur getById(int _Id)
	{
		for(Decor decor : Editeur.getElements())
		{
			if(decor.getType().equalsIgnoreCase("mur"))
			{
				if(((Mur)decor).Id == _Id)
				{
					return (Mur)decor;
				}
			}
		}
		
		return null;
	}
}

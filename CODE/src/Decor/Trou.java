package Decor;

// La classe Trou sert � repr�senter tous les objets qui constituent des obstacles
// �liminant la bille d�s qu'elle passe dessus. Il est possible de sauter par-dessus
// les trous.

public class Trou extends Decor {
	
	// Attribut d�terminant le rayon du trou

	// Constructeur
	public Trou(float posX, float posY,float _longueur, float _largeur)
	{
		super(posX,posY, _longueur,_largeur);
		super.setType("trou");
	}

	
}

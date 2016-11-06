package Decor;

// La classe Marqueur sert à représenter tous les objets qui indiquent un endroit
// particulier du parcours (départ, arrivée, point de sauvegarde...)

public class Marqueur extends Decor {

	// Attribut déterminant le type de marqueur
	private String type_marqu;
	// Constructeur
	public Marqueur(float posX, float posY,float _longueur, float _largeur, String type)
	{
		super(posX,posY, _longueur,_largeur);
		super.setType("marqueur");
		this.setTypemarq(type);
	}

	public String getTypemarq() {
		return type_marqu;
	}

	public void setTypemarq(String type) {
		this.type_marqu = type;
	}


}

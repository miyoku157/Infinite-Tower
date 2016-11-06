package Texture;

import java.util.Random;

import Class.Niveau;
import Decor.Decor;
import Decor.Marqueur;
import Decor.Mur;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class TextureFactory {

	public static Drawable getTexture(Niveau lvl, Decor decor,Context context){
		String texture;
			if(decor.getType().equalsIgnoreCase("Marqueur")){
				Marqueur marqueur=(Marqueur)decor;
				 texture=lvl.getType()+"_"+marqueur.getTypemarq();		
			}else if (decor.getType().equalsIgnoreCase("Trou")){
				Random rand= new Random();
				 texture=lvl.getType()+"_"+decor.getType()+(rand.nextInt(3)+1);
				 
			/*		Essai pour l'affichage orienté des murs
			 * 
			 * 
			}else if (decor.getType().equalsIgnoreCase("Mur")){
				Mur monMur = (Mur)decor;
				String numero_de_mur = "";
				String schema = monMur.getBordHaut() + monMur.getBordGauche() + monMur.getBordBas() + monMur.getBordDroit();
				
				if(schema == "----"){ numero_de_mur = "1";}
				else if(schema == "M---"){ numero_de_mur = "2";}
				else if(schema == "-M--"){ numero_de_mur = "3";}
				else if(schema == "--M-"){ numero_de_mur = "4";}
				else if(schema == "---M"){ numero_de_mur = "5";}
				else if(schema == "-M-M"){ numero_de_mur = "6";}
				else if(schema == "M-M-"){ numero_de_mur = "7";}
				else if(schema == "-MM-"){ numero_de_mur = "8";}
				else if(schema == "--MM"){ numero_de_mur = "9";}
				else if(schema == "M--M"){ numero_de_mur = "10";}
				else if(schema == "MM--"){ numero_de_mur = "11";}
				else if(schema == "MMMM"){ numero_de_mur = "12";}
				
				texture=lvl.getType()+"_"+decor.getType()+numero_de_mur;
				
			*/
			}else{
				 texture=lvl.getType()+"_"+decor.getType();
			}
			return context.getResources().getDrawable(context.getResources().getIdentifier(texture, "drawable", context.getPackageName()));		
	}
}

package Class;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.jeuandroid.MainActivity;

import Texture.Texture;
import Texture.TextureFactory;
import android.content.Context;
import android.util.Log;

import Decor.Bonus;
import Decor.Decor;
import Decor.Marqueur;
import Decor.Mur;
import Decor.Portail;
import Decor.Sol;
import Decor.Trou;

public class Editeur {
private static List<Decor> elements = new ArrayList<Decor>();
private float x;
private float y;
private Context context;
public Editeur(Context _context){
	context=_context;
}
public void ajoutDecor(Decor _decor){
	elements.add(_decor);
}
public void supprimerDecor(Decor _decor){
	elements.remove(_decor);
}
public static List<Decor> getElements() {
	return elements;
}

public static void setElements(List<Decor> elements) {
	Editeur.elements = elements;
}
public void chargerNiveau(Context context,Niveau niveau){
	Editeur.elements.clear();
	float width=MainActivity.width;
	float height=MainActivity.height;
	width=width/15;
	height=height/10;
	try {
		InputStreamReader objReader = new InputStreamReader(context.getResources().openRawResource(context.getResources().getIdentifier("lvl"+niveau.getNum_lvl(), "raw", context.getPackageName())));
		BufferedReader objBufferReader = new BufferedReader(objReader);
		String strLine;
		while ((strLine = objBufferReader.readLine()) != null) {
			
			testLigne(strLine, niveau);
			y+=Math.round(height);
			x=0;
			
			
	}
	}
	catch (FileNotFoundException objError) {
		Log.i("ExceptionFileNotFound", objError.toString());
	}
	catch (IOException objError) {
		Log.i("ExceptionIO", objError.toString());

	}
}
public void testLigne(String ligne,Niveau niveau){
	char[] tab_ligne=ligne.toCharArray();
	int i = 0;
	int numMur = 0;
	double width=MainActivity.width;
	double height=MainActivity.height;
	width=Math.round(width/15);
	height=Math.round(height/10);
	while(i<tab_ligne.length){

	
		
		 if (tab_ligne[i]=='A'){
			Marqueur arrive= new Marqueur(x,y,(float)height,(float)width,"arrive");
			  Texture text= new Texture(TextureFactory.getTexture(niveau, arrive, context));
			  arrive.setTexture(text);
			  elements.add(arrive);
		}
		else if (tab_ligne[i]=='D'){
			Marqueur depart= new Marqueur(x,y,(float)height,(float)width,"depart");
			Texture text= new Texture(TextureFactory.getTexture(niveau, depart, context));
			depart.setTexture(text);
			  elements.add(depart);

		}
		else if ((int) tab_ligne[i] == 'M'){
			Mur mur= new Mur(x,y,(float)height,(float)width, numMur);
			numMur++;
			Texture text= new Texture(TextureFactory.getTexture(niveau, mur, context));
			mur.setTexture(text);
			elements.add(mur);
			
		}
		else if (tab_ligne[i]=='0'){
			Trou trou= new Trou(x,y,(float)height,(float)width);
			Texture text = new Texture(TextureFactory.getTexture(niveau, trou, context));
			trou.setTexture(text);
			elements.add(trou);
			
		}
		else if ((int) tab_ligne[i] >= 49 && (int) tab_ligne[i] <= 57){
			
			Portail portail1= new Portail(x,y,(float)width,(float)height,tab_ligne[i]);
			Texture text= new Texture (TextureFactory.getTexture(niveau, portail1, context));
			portail1.setTexture(text);
			for (Decor decor:elements){
				if(decor instanceof Portail){
					Portail portail2= (Portail) decor;
					if(portail2.getLien()==portail1.getLien()){
						portail2.setLink(portail1);
						portail1.setLink(portail2);
					}
					decor=portail2;
				}
			}
			elements.add(portail1);
		}
		else if(tab_ligne[i]=='B')
		{
			Sol sol= new Sol(x,y,(float)height,(float)width);
			Texture text = new Texture(TextureFactory.getTexture(niveau, sol, context));
			sol.setTexture(text);
			elements.add(sol);
			Bonus bonus= new Bonus(x,y,(float)height,(float)width);
			text = new Texture(TextureFactory.getTexture(niveau, bonus, context));
			bonus.setTexture(text);
			elements.add(bonus);
		}
		else if(tab_ligne[i]=='B')
		{
			Sol sol= new Sol(x,y,(float)height,(float)width);
			Texture text = new Texture(TextureFactory.getTexture(niveau, sol, context));
			sol.setTexture(text);
			elements.add(sol);
			Bonus bonus= new Bonus(x,y,(float)height,(float)width);
			text = new Texture(TextureFactory.getTexture(niveau, bonus, context));
			bonus.setTexture(text);
			elements.add(bonus);
		}
		else{
			Sol sol= new Sol(x,y,(float)height,(float)width);
			Texture text= new Texture (TextureFactory.getTexture(niveau, sol, context));
			sol.setTexture(text);
			elements.add(sol);
		}
		x+=width;
		
		i++;
	}
}
/**
 * @return the x
 */
public float getX() {
	return x;
}
/**
 * @param x the x to set
 */
public void setX(float x) {
	this.x = x;
}
/**
 * @return the y
 */
public float getY() {
	return y;
}
/**
 * @param y the y to set
 */
public void setY(float y) {
	this.y = y;
}
}

package Class;

import com.example.jeuandroid.MainActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Bille
{
    // Rayon adapté à le taille de l'écran
    public float rayon = 10f*MainActivity.screen;
    private float mX;
    private float mY;
    
    // Objet Paint servant à dessiner la couleur de la bille.
    private Paint couleur;
    
    // Instance unique de la bille
    private static Bille bille;
    
    // Vélocités horizontale et verticale
    private float vel_x = 0;
    private float vel_y = 0;
    
    // Limites de l'écran
    private int height_max = -1;
    private int width_max = -1;
    
    // Vitesse maximale autorisée pour la boule
    private float vit_max = (5.0f*MainActivity.screen);
    
    // Permet à la boule d'accélérer moins vite
    private float F = 10.0f;
    
    // Utilisé pour compenser les rebonds
    private float REBOND = 1.75f;
    
    private RectF hitbox;
    
    private int vies = 0;

    private Bille()
    {
        couleur = new Paint();
        couleur.setColor(Color.RED);
        hitbox = new RectF();
    }
    
    public static Bille getBille()
	{
		if(bille == null)
		{
			bille = new Bille();
		}
		return bille;
	}

    public Paint getCouleur()
    {
        return couleur;
    }
    
    public float getRayon()
    {
    	return rayon;
    }
    
    public float getX()
    {
        return mX;
    }
    
    public float getY()
    {
        return mY;
    }
    
    public int getVies()
    {
        return vies;
    }
    
    public void setVies(int _vies)
    {
        vies = _vies;
    }
    
    public void setPosX(float pPosX)
    {
        mX = pPosX;

        // Si la boule sort du cadre, on rebondit
        if(mX < rayon)
        {
            mX = rayon;
            // Rebondir, c'est changer la direction de la balle
            vel_y = -vel_y / REBOND;
        }
        else if(mX > width_max - rayon)
        {
            mX = width_max - rayon;
            vel_y = -vel_y / REBOND;
        }
    }

    public void setPosY(float pPosY)
    {
        mY = pPosY;
        if(mY < rayon)
        {
            mY = rayon;
            vel_x = -vel_x / REBOND;
        }
        else if(mY > height_max - rayon)
        {
            mY = height_max - rayon;
            vel_x = -vel_x / REBOND;
        }
    }
    
    public void setHeight(int pHeight)
    {
        this.height_max = pHeight;
    }
    public void setWidth(int pWidth)
    {
        this.width_max = pWidth;
    }

    // Mettre à jour les coordonnées de la boule
    public void update(float pX, float pY)
    {
    	vel_x += pX / F;
        if(vel_x > vit_max)
        	vel_x = vit_max;
        if(vel_x < -vit_max)
        	vel_x = -vit_max;
            
        vel_y += pY / F;
        if(vel_y > vit_max)
        	vel_y = vit_max;
        if(vel_y < -vit_max)
        	vel_y = -vit_max;
        
        setPosX(mX + vel_y);
        setPosY(mY + vel_x);
    }
    
    public void updateHitbox()
    {
    	hitbox.set(mX - rayon, mY - rayon, mX + rayon, mY + rayon);
    }
    
    public RectF getHitbox()
    {
    	return hitbox;
    }
    
    // Remet la boule à sa position de départ
    public void reset()
    {
    	vel_x = 0;
        vel_y = 0;
    }
    
    public void Bounce(RectF rect)
    {
    	if(mX <= rect.left)
    	{
    		vel_y = -vel_y / REBOND;
    		mX = rect.left - rayon ;
    	}
    	else if(mY <= rect.top)
    	{
    		vel_x = -vel_x / REBOND;
    		mY = rect.top - rayon ;
    	}
    	else if (mX >= rect.right)
    	{
    		vel_y = -vel_y / REBOND;
    		mX = rect.right + rayon;
    	}
    	else if(mY >= rect.bottom)
    	{
    		vel_x = -vel_x / REBOND;
    		mY = rect.bottom + rayon;
    	}
    }
}

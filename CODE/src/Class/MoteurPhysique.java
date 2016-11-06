package Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.math.*;

import Decor.Bonus;
import Decor.Decor;
import Decor.Marqueur;
import Decor.Mur;
import Decor.Trou;
import Decor.Portail;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.DialogFragment;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.jeuandroid.BtnPerdu;
import com.example.jeuandroid.InGame;
import com.example.jeuandroid.MainActivity;
import com.example.jeuandroid.NextLvlDialogFragment;

public class MoteurPhysique extends SurfaceView implements SurfaceHolder.Callback
{
	// Instance unique du moteur physique
	static MoteurPhysique moteur;

	// Fenêtre de jeu
	private MusicManager manager;
	private Activity activity;
	private Bille bille;
	private TextPaint textPaint;
	private Paint wallPaint;
	private RectF rect;
	private Rect imageRect;
	private Rect colRect;
	private RectF portRect;
	private List<RectF> collisionList;
	
	// Gestion du gameplay
	private boolean bounce = false;
	private RectF CollRectTemp = new RectF();
	private double distanceToCol = 0;
	private double distanceToColTemp = 0;
	private double vectorColX, vectorColY = 0;
	private int[] wallId = new int[2];
	private static boolean billePlacee = false;
	private boolean inPortail = false;
	private int departX, departY;
	private Portail portail_arrivee;
	private Bonus bonus;
	private boolean bonusCol = false;
	private boolean dessin = false;

	// Déclarations des capteurs et du chronomètre
	private SensorManager sensor_manager;
	private Sensor accelerometre;
	private Chronometre chrono;
	public float pos_x, acc_x, vel_x = 0.0f;
	public float pos_y, acc_y, vel_y = 0.0f;
	float F = 0.5f;
	float K = 25.0f;

	private SurfaceHolder mSurfaceHolder;
	private DrawingThread mThread;
	
	public static void setBillePlace(Boolean place)
	{
		billePlacee = place;
	}
	
	private MoteurPhysique(Activity view)
	{
		super(view);
		activity = view;
		bille = Bille.getBille();
		manager= MusicManager.getMusicManager(getContext());
		sensor_manager = (SensorManager) activity.getBaseContext().getSystemService(Service.SENSOR_SERVICE);
		accelerometre = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mThread = new DrawingThread();
		textPaint = new TextPaint();
		textPaint.setTextSize(40*MainActivity.screen);
		textPaint.setColor(Color.LTGRAY);
		textPaint.setTextAlign(Paint.Align.LEFT);
		wallPaint = new Paint();
		wallPaint.setColor(Color.BLUE);
		chrono = Chronometre.getChrono();
		rect = new RectF();
		colRect = new Rect();
		portRect = new RectF();
		
		collisionList = new ArrayList<RectF>();
		
		imageRect = new Rect();
		portail_arrivee = new Portail(F, F, F, F, 0);
		bonus = new Bonus(F, F, F, F);
		
		wallId[0] = -1;
		wallId[1] = -1;
	}

	public static MoteurPhysique getMoteur(Activity activity)
	{
		if(moteur == null)
		{
			moteur = new MoteurPhysique(activity);
		}
		return moteur;
	}

	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		wallId[0] = -1;
		wallId[1] = -1;
		
		// Vision limitée
		canvas.drawColor(Color.BLACK);
		
		dessin = false;
		for(Decor decor : Editeur.getElements())
		{
			dessin = true;
			imageRect.left = (int)decor.getPositionX();
			imageRect.top = (int)decor.getPositionY();

			imageRect.right = (int)(decor.getPositionX() + decor.getLargeur());
			imageRect.bottom = (int)(decor.getPositionY() + decor.getLongueur());

			if((decor.getCentreX() <= bille.getX() + 100*MainActivity.screen && decor.getCentreX() >= bille.getX() - 100*MainActivity.screen)
					&& (decor.getCentreY() <= bille.getY() + 100*MainActivity.screen && decor.getCentreY() >= bille.getY() - 100*MainActivity.screen))
			{
				decor.getTexture().getImage().setBounds(imageRect);
				decor.getTexture().getImage().draw(canvas);
			}
		}
		dessin = false;
		if(bille != null)
		{
			canvas.drawCircle(bille.getX(), bille.getY(), bille.getRayon(), bille.getCouleur());
		}

		canvas.drawText(chrono.getTime(), 0, textPaint.getTextSize(), textPaint);
		canvas.drawText("Vies :" + bille.getVies(), getHeight()-50*MainActivity.screen, textPaint.getTextSize(), textPaint);
	}

	SensorEventListener mSensorEventListener = new SensorEventListener()
	{
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1)
		{

		}

		@Override
		public void onSensorChanged(SensorEvent event)
		{
			// Récupération des valeurs de l'accéléromètre
			acc_x = event.values[0];
			acc_y = event.values[1];
			bille.update(acc_x, acc_y);
			bille.updateHitbox();
			bounce = false;
			collisionList.clear();
			distanceToCol = 10000;
			distanceToColTemp = 0;
			CollRectTemp = new RectF();
			
			if(bonusCol && !dessin)
			{
				Editeur.getElements().remove(bonus);
				bonusCol = false;
			}
			
			for(Decor decor : Editeur.getElements())
			{
				colRect.left = (int)decor.getPositionX();
				colRect.top = (int)decor.getPositionY();

				colRect.right = (int) (decor.getPositionX() + decor.getLargeur());
				colRect.bottom = (int)(decor.getPositionY() + decor.getLongueur());
				if(decor.getType().equalsIgnoreCase("mur"))
				{
					rect.set(colRect);
					if(RectF.intersects(rect, bille.getHitbox()))
					{
						//bounce = true;
						//bille.Bounce(rect);
						//wallId[0] = ((Mur)decor).Id;
						collisionList.add(rect);
					}
					else
					{
						/*if(wallId[0] >= 0)
						{
							if(!Mur.getById(wallId[0]).isNextTo(((Mur)decor).getPositionX(), ((Mur)decor).getPositionY()))
							{
								
							}
							else
							{
								bounce = false;
							}
						}
						else
						{*/
							//bounce = false;
						//}
					}
				}
				else if(decor.getType().equalsIgnoreCase("marqueur"))
				{
					colRect.left = (int)decor.getPositionX() + (int)decor.getLongueur() / 2 - (int)decor.getLongueur()/10;
					colRect.top = (int)decor.getPositionY() + (int)decor.getLargeur() / 2 - (int)decor.getLargeur()/10;

					colRect.right = (int)decor.getPositionX() + (int)decor.getLongueur() / 2 + (int)decor.getLongueur()/10;
					colRect.bottom = (int)decor.getPositionY() + (int)decor.getLargeur() / 2 + (int)decor.getLargeur()/10;

					rect.set(colRect);

					if(((Marqueur)decor).getTypemarq().equals("depart") && !billePlacee)
					{
						departX = (int)decor.getPositionX() + (int)decor.getLongueur() / 2;
						departY = (int)decor.getPositionY() + (int)decor.getLargeur() / 2;
						bille.setPosX(departX);
						bille.setPosY(departY);
						bille.updateHitbox();
						billePlacee = true;
					}
					if(RectF.intersects(rect, bille.getHitbox()))
					{
						if(((Marqueur)decor).getTypemarq().equals("arrive"))
						{
							bille.setPosX(departX);
							bille.setPosY(departY);
							bille.updateHitbox();
							manager.playBruit("arrive");
							stop();
							bille.reset();
							DialogFragment test= new NextLvlDialogFragment();
							test.setCancelable(false);
							test.show(InGame.fragMana, "test");
							
						}
					}
				}
				else if(decor.getType().equalsIgnoreCase("trou"))
				{
					colRect.left = (int)(decor.getPositionX() + decor.getLongueur() / 2 - decor.getLongueur()/5);
					colRect.top = (int)(decor.getPositionY() + decor.getLargeur() / 2 - decor.getLargeur()/5);

					colRect.right = (int)(decor.getPositionX() + decor.getLongueur() / 2 + decor.getLongueur()/5);
					colRect.bottom = (int)(decor.getPositionY() + decor.getLargeur() / 2 + decor.getLargeur()/5);

					rect.set(colRect);

					if(RectF.intersects(rect, bille.getHitbox()))
					{
						bille.setVies(bille.getVies() - 1);
						bille.setPosX(departX);
						bille.setPosY(departY);
						bille.reset();
						manager.playBruit("trou");
						if(bille.getVies()<=0){
							stop();
							reset();
							DialogFragment test= new BtnPerdu();
							test.setCancelable(false);
							test.show(InGame.fragMana, "test");
						}
					}
				}
				else if(decor.getType().equalsIgnoreCase("portail"))
				{
					colRect.left = (int)(decor.getPositionX() + decor.getLargeur() / 2 - decor.getLargeur()/5);
					colRect.top = (int)(decor.getPositionY() + decor.getLongueur() / 2 - decor.getLongueur()/5);

					colRect.right = (int)(decor.getPositionX() + decor.getLargeur() / 2 + decor.getLargeur()/5);
					colRect.bottom = (int)(decor.getPositionY() + decor.getLongueur() / 2 + decor.getLongueur()/5);

					rect.set(colRect);

					if(RectF.intersects(rect, bille.getHitbox()) && !inPortail)
					{
						manager.playBruit("portail");
						portail_arrivee = ((Portail) decor).getLink();
						bille.setPosX((int)(portail_arrivee.getPositionX() + portail_arrivee.getLongueur() / 2));
						bille.setPosY((int)(portail_arrivee.getPositionY() + portail_arrivee.getLargeur() / 2));
						inPortail = true;
						
						portRect.left = (int)(portail_arrivee.getPositionX() + decor.getLargeur() / 2 - decor.getLargeur()/5);
						portRect.top = (int)(portail_arrivee.getPositionY() + decor.getLongueur() / 2 - decor.getLongueur()/5);

						portRect.right = (int)(portail_arrivee.getPositionX() + decor.getLargeur() / 2 + decor.getLargeur()/5);
						portRect.bottom = (int)(portail_arrivee.getPositionY() + decor.getLongueur() / 2 + decor.getLongueur()/5);
					}
					bille.updateHitbox();
					if(!RectF.intersects(portRect, bille.getHitbox()))
					{
						inPortail = false;
					}
				}
				else if(decor.getType().equalsIgnoreCase("bonus") && !bonusCol)
				{
					colRect.left = (int)(decor.getPositionX() + decor.getLargeur() / 2 - decor.getLargeur()/5);
					colRect.top = (int)(decor.getPositionY() + decor.getLongueur() / 2 - decor.getLongueur()/5);

					colRect.right = (int)(decor.getPositionX() +decor.getLargeur() / 2 + decor.getLargeur()/5);
					colRect.bottom = (int)(decor.getPositionY() +decor.getLongueur() / 2 + decor.getLongueur()/5);

					rect.set(colRect);

					if(RectF.intersects(rect, bille.getHitbox()))
					{
						bonus = (Bonus) decor;
						chrono.bonus();
						bonusCol = true;
						manager.playBruit("bonus");
					}
				}
			}
			
			if(!collisionList.isEmpty())
			{
				for(RectF collRect : collisionList)
				{
					vectorColX = bille.getX() - collRect.centerX();
					vectorColY = bille.getY() - collRect.centerY();
					
					distanceToColTemp = Math.sqrt((vectorColX * vectorColX) + (vectorColY * vectorColY));
					
					if(distanceToColTemp < distanceToCol)
					{
						distanceToCol = distanceToColTemp;
						CollRectTemp.set(collRect);
					}
				}
				bille.Bounce(CollRectTemp);
			}
		}

	};

	// Remet à zéro l'emplacement de la boule
	public void reset()
	{
		bille.reset();
		chrono.reset();
		//firstDraw = 0;
		billePlacee = false;
	}

	// Arrête le capteur
	public void stop()
	{
		sensor_manager.unregisterListener(mSensorEventListener, accelerometre);
		chrono.pause();
	}

	// Redémarre le capteur
	public void resume()
	{
		sensor_manager.registerListener(mSensorEventListener, accelerometre, SensorManager.SENSOR_DELAY_GAME);
		chrono.resume();
	}

	@Override
	public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth, int pHeight)
	{

	}

	@Override
	public void surfaceCreated(SurfaceHolder pHolder)
	{
		mThread.keepDrawing = true;
		mThread = new DrawingThread();

		mThread.start();
		// Quand on crée la boule, on lui indique les coordonnées de l'écran
		if(bille != null )
		{
			this.bille.setHeight(getHeight());
			this.bille.setWidth(getWidth());
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder pHolder)
	{
		mThread.keepDrawing = false;
	}

	private class DrawingThread extends Thread
	{
		private boolean keepDrawing = true;

		@SuppressLint("WrongCall")
		public void run()
		{
			Canvas canvas;
			while (keepDrawing)
			{
				canvas = null;

				try
				{
					canvas = mSurfaceHolder.lockCanvas();
					if(canvas!=null)
					{
						synchronized (mSurfaceHolder)
						{
							onDraw(canvas);
						}
					}
				}
				finally {
					if (canvas != null)
						mSurfaceHolder.unlockCanvasAndPost(canvas);
				}

				try
				{
					Thread.sleep(7);
				}
				catch (InterruptedException e)
				{

				}
			}
		}
	}
}
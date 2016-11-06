package com.example.jeuandroid;

import Class.Editeur;
import Class.MoteurPhysique;
import Class.MusicManager;
import Class.Niveau;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;

public class InGame extends FragmentActivity
{
	private boolean pause;
	private MoteurPhysique moteur;
	public MusicManager manager;
	public static int lvl;
	public static final int BOOYAH_DIALOG = 0;
	public static FragmentManager fragMana;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle b = getIntent().getExtras();
        lvl = b.getInt("lvl");
        pause=false;
        String type;
        if (lvl<20){
        	type="space";
        }else{
        	type="dungeon";
        }
        Niveau niveau = new Niveau(0,false,type,lvl);
        Editeur edit= new Editeur(getApplicationContext());
        edit.chargerNiveau(getApplicationContext(), niveau);
		MainActivity.music_ingame=true;
        manager = MusicManager.getMusicManager(getApplicationContext());
        manager.loadBruit(getApplicationContext(), R.raw.trou, "trou");
		manager.loadBruit(getApplicationContext(), R.raw.portail, "portail");
		manager.loadBruit(getApplicationContext(), R.raw.arrive, "arrive");
		manager.loadBruit(getApplicationContext(), R.raw.bonus, "bonus");
        if(niveau.getType().equals("space")){
        	if(manager.getMusic_Playing()!=R.raw.musique_space){
        		manager.PlayMusic(this, R.raw.musique_space);
        	}
        }else{
        	if(manager.getMusic_Playing()!=R.raw.musique_donjon){
        		manager.PlayMusic(this, R.raw.musique_donjon);
        	}
        }
		fragMana=getSupportFragmentManager();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		moteur = MoteurPhysique.getMoteur(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.in_game, menu);
		return true;
	}
	public void onBackPressed(){
		
		Intent setIntent = new Intent(getApplicationContext(),MainActivity.class);
		   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   moteur.reset();
		   startActivity(setIntent);
	}
	@Override
	protected void onStart(){
		super.onStart();

	}
	public static int getlvl(){
		return lvl;
		
	}
	@Override
	protected void onResume(){
		super.onResume();

		if(manager.etatMusic()==false){
			manager.pauseMusic(); 
		}
		if(pause==false){
			moteur.resume();
			setContentView(moteur);
		}else{
			PauseFragment pause= new PauseFragment();
			pause.show(getSupportFragmentManager(), "Pause");
		}
	}
	
	@Override
	protected void onPause(){
		super.onPause();
    	((ViewGroup)moteur.getParent()).removeView(moteur);
		manager.pauseMusic();
		moteur.stop();
		pause=true;
	}
	
	@Override
    protected void onDestroy(){
    	super.onDestroy();
    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);    	
    }
}

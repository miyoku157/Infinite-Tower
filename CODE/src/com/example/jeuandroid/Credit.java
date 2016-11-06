package com.example.jeuandroid;

import Class.Editeur;
import Class.MoteurPhysique;
import Class.MusicManager;
import Class.Niveau;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class Credit extends Activity {
	public MusicManager manager;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		manager=MusicManager.getMusicManager(getApplicationContext());
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		float screen = MainActivity.screen;
		float textsize;
		if (screen > 1.5f) {
			textsize = screen * 0.5f;
		} else if (screen == 1.5f) {
			textsize = screen * 0.75f;
		} else {
			textsize = screen;
		}
		

		

		setContentView(R.layout.activity_credit);
		TextView text=(TextView)findViewById(R.id.cred1);
		text.setTextSize(19*textsize);
		text=(TextView)findViewById(R.id.cred2);
		text.setTextSize(19*textsize);
		text=(TextView)findViewById(R.id.cred3);
		text.setTextSize(19*textsize);
		text=(TextView)findViewById(R.id.cred4);
		text.setTextSize(19*textsize);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	public void onBackPressed(){
		
		Intent setIntent = new Intent(getApplicationContext(),MainActivity.class);
		   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   startActivity(setIntent);
	}
	@Override
	protected void onStart(){
		super.onStart();

	}
	@Override
	protected void onResume(){
		super.onResume();

		if(manager.etatMusic()==false){
			manager.pauseMusic(); 
		}
		
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		manager.pauseMusic();
	}
	
	@Override
    protected void onDestroy(){
    	super.onDestroy();
    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);    	
    }
}

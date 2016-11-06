package com.example.jeuandroid;

import Class.Bille;
import Class.MoteurPhysique;
import Class.MusicManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
public class MainActivity extends Activity
{
	public MusicManager manager;
	public static final String  PREFS_NAME="MyPrefsFile";
	public static float screen;
	public static float height;
	public static float width;
	public static boolean music_ingame=false;
	public static int[] tab_time= new int[40]; 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		int timer=10;
		int i=0;
		for (i=0;i<=39;i++){
			tab_time[i]=timer;
			timer+=10;
		}
		screen=getResources().getDisplayMetrics().density;
		height=getWindowManager().getDefaultDisplay().getHeight();
		width=getWindowManager().getDefaultDisplay().getWidth();
		manager=MusicManager.getMusicManager(getApplicationContext());
		if(manager.checkPlayer()){
			manager.PlayMusic(getApplicationContext(),  R.raw.disco_inferno);

		}else{
			manager.SoundPlayer(getApplicationContext(),  R.raw.disco_inferno);
		}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_main);
		final Button lvl=(Button) findViewById(R.id.Lvl);
		lvl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(getApplicationContext(),Level.class);
				startActivity(i);
			}
		});
		final Button option=(Button) findViewById(R.id.Option);
		option.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),Option.class);
				startActivity(i);
			}
		});
		final Button score=(Button) findViewById(R.id.Score);
		score.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),Score.class);
				startActivity(i);
			}
		});
		final Button credit=(Button) findViewById(R.id.Credit);
		credit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(getApplicationContext(),Credit.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
	   Intent setIntent = new Intent(Intent.ACTION_MAIN);
	   setIntent.addCategory(Intent.CATEGORY_HOME);
	   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   startActivity(setIntent);
	}
	protected void onStop(){
		super.onStop();

	}
	protected void onStart(){
		super.onStart();
		if(music_ingame==true){
			manager.PlayMusic(getApplicationContext(),  R.raw.disco_inferno);
			music_ingame=false;
		}
	}
	protected void onPause(){
		super.onPause();
		
		manager.pauseMusic();

	}
	protected void onResume(){
		super.onResume();
		
		manager.pauseMusic();
	}
	 protected void onDestroy(){
	    	super.onDestroy();
	    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    }
}

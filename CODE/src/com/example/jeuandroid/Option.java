package com.example.jeuandroid;

import Class.MusicManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class Option extends Activity {
	public static final String  PREFS_NAME="MyPrefsFile";
	private static int musique_stop;
	private static int progressNumber;
	public static boolean bruit_mute=false;
	private MusicManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager=MusicManager.getMusicManager(getApplicationContext());
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_option);
		SharedPreferences settings=getSharedPreferences(PREFS_NAME,0);
		Button mute= (Button) findViewById(R.id.Mute);
		mute.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(manager.etatMusic()){
					manager.pauseMusic();
					manager.muteBruit();
					musique_stop=manager.getMusic_Playing();
					bruit_mute=true;
				}else{
					bruit_mute=false;
					manager.pauseMusic();

				}

			}
			
			
		});
		final SeekBar volume =(SeekBar) findViewById(R.id.seekBar1);
		int test=settings.getInt("volumePref", 100);
		volume.setProgress(settings.getInt("volumePref", 100));
		progressNumber=volume.getProgress();
		manager.setVolume(volume.getProgress());
		volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				manager.setVolume(volume.getProgress());
				volume.setProgress(progress);
				progressNumber=volume.getProgress();
			}
		});
	}
	protected void onStart(){
		super.onStart();
		if(manager.etatMusic()==false){
			manager.pauseMusic();
		}
	}
	protected void onPause(){
		super.onPause();
		manager.pauseMusic();

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}
	protected void onStop(){
		super.onStop();
    	SharedPreferences settings=getSharedPreferences(PREFS_NAME,0);
		SharedPreferences.Editor editor=settings.edit();
		editor.putInt("volumePref", progressNumber);
		editor.commit();
    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	 protected void onDestroy(){
	    	super.onDestroy();
	    }
}

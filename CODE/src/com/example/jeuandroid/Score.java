package com.example.jeuandroid;

import Class.MusicManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Score extends Activity {
	public MusicManager manager;
	private ListView listview;
	public static final String  PREFS_NAME="MyPrefsFile";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager=MusicManager.getMusicManager(getApplicationContext());
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_score);
		String[] values=new String[40];
		int i =0;
		for(i=0;i<40;i++){
			SharedPreferences settings=getSharedPreferences(PREFS_NAME,0);
	    	values[i] ="Level "+(i+1)+"		Highscore: "+String.valueOf(settings.getInt("lvl_high_"+i, 0))
	    			+"	 Etoiles : "+String.valueOf(settings.getInt("lvl_etoile"+i, 0))+"/3";
		}
		listview= (ListView) findViewById(R.id.listView1);
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              android.R.layout.simple_list_item_1, values);
         listview.setAdapter(adapter); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}
	 protected void onDestroy(){
	    	super.onDestroy();
	    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
}

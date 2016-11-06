package com.example.jeuandroid;

import Class.MusicManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Level extends Activity {
	public MusicManager manager;
	private int lvl = 1;
	public static final String  PREFS_NAME="MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int i = 0;
		int j = 0;
		float screen = MainActivity.screen;
		double screendiv = 0;
		if (screen == 1.5f) {
			screendiv = 1;
		} else if (screen == 1) {
			screendiv = 1.5;
		} else if (screen == 0.75f) {
			screendiv = 1.75;
		}
		
		manager = MusicManager.getMusicManager(getApplicationContext());
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_level);
		RelativeLayout groupeDeVue = (RelativeLayout) findViewById(R.id.lvllayout);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		TextView label = new TextView(getApplicationContext());
		label.setText("Levels");
		label.setGravity(Gravity.CENTER);
		label.setLayoutParams(layoutParams);
		float textsize;
		if (screen > 1.5f) {
			textsize = screen * 0.5f;
		} else if (screen == 1.5f) {
			textsize = screen * 0.75f;
		} else {
			textsize = screen;
		}
		label.setTextSize(20 * textsize);
		label.setId(50);
		groupeDeVue.addView(label, layoutParams);

		for (i = 0; i < 4; i++) {
			for (j = 0; j < 10; j++) {
				Button bouton = new Button(this);
				RelativeLayout.LayoutParams layoutParamsbutton = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				SharedPreferences settings=getSharedPreferences(PREFS_NAME,0);
		    	boolean enable =settings.getBoolean("lvl"+(lvl-1), false);
		    	if(i==0&&j==0){
		    		enable=true;
		    	}
				if (lvl < 10) {
					bouton.setText("0" + lvl);
				} else {
					bouton.setText("" + lvl);
				}

				bouton.setEnabled(enable);
				bouton.setTextSize(20 * textsize);

				bouton.setOnClickListener(new BtnLvlListener(bouton,
						getApplicationContext()));
				bouton.setId(lvl);

				if (lvl == 1) {
					layoutParamsbutton.addRule(RelativeLayout.BELOW,
							label.getId());
				} else if (lvl == 11) {
					layoutParamsbutton.addRule(RelativeLayout.BELOW, lvl - 1);

				} else if (lvl == 21) {
					layoutParamsbutton.addRule(RelativeLayout.BELOW, lvl - 1);

				} else if (lvl == 31) {
					layoutParamsbutton.addRule(RelativeLayout.BELOW, lvl - 1);

				} else {
					if (lvl >= 1 && lvl <= 10) {

						layoutParamsbutton.addRule(RelativeLayout.ALIGN_BOTTOM,
								1);
						Log.i("jbinl", "" + bouton.getId());
					} else if (lvl >= 11 && lvl <= 20) {
						layoutParamsbutton.addRule(RelativeLayout.ALIGN_BOTTOM,
								11);

					} else if (lvl >= 21 && lvl <= 30) {
						layoutParamsbutton.addRule(RelativeLayout.ALIGN_BOTTOM,
								21);

					} else if (lvl >= 31 && lvl <= 40) {
						layoutParamsbutton.addRule(RelativeLayout.ALIGN_BOTTOM,
								31);

					}
					layoutParamsbutton
							.addRule(RelativeLayout.RIGHT_OF, lvl - 1);

				}

				groupeDeVue.addView(bouton, layoutParamsbutton);
				lvl++;
			}
		}

		setContentView(groupeDeVue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level, menu);
		return true;
	}

	protected void onDestroy() {
		super.onDestroy();
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	protected void onStart() {
		super.onStart();
		if (manager.etatMusic() == false) {
			manager.pauseMusic();
		}
	}

	protected void onPause() {
		super.onPause();
		manager.pauseMusic();

	}

}

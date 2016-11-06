package com.example.jeuandroid;

import Class.Bille;
import Class.Chronometre;
import Class.MoteurPhysique;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class NextLvlDialogFragment extends DialogFragment {
	public static final String  PREFS_NAME="MyPrefsFile";

	public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Chronometre chrono=Chronometre.getChrono();
			int[] time=chrono.getTimeArray();
			int temps_lvl=time[0]+time[1]*60+time[2]*3600;
			int temp_estime=MainActivity.tab_time[InGame.getlvl()];
			SharedPreferences settings=getActivity().getSharedPreferences(PREFS_NAME,0);
	    	int highscore=settings.getInt("lvl_high_"+InGame.getlvl(), 0);
			int score=(temp_estime-temps_lvl)*100;
			MoteurPhysique.setBillePlace(false);

			if (score<0){
				score=0;
			}
			int etoile;
			if(temps_lvl<temp_estime/3){
				etoile=3;
			}else if (temps_lvl<temp_estime/2){
				etoile=2;
			}else if (temps_lvl<temp_estime){
				etoile=1;
			}else{
				etoile=0;
			}
			
			AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());

			SharedPreferences.Editor editor=settings.edit();
			Log.i("info","lvl"+InGame.getlvl());
			editor.putBoolean("lvl"+(InGame.getlvl()+1), true);
			

			editor.commit();
			if(score>highscore){
				editor.putInt("lvl_etoile"+InGame.getlvl(), etoile);
				editor.putInt("lvl_high_"+InGame.getlvl(), score);
			    adb.setTitle("Bravo, vous avez atteint un nouveau record:"+score+" et obtenu "+etoile+ " étoiles");
			}else{
			    adb.setTitle("Vous avez gagné,votre score est de "+score+" et vous avez obtenu "+etoile+ " étoiles");
			}
			editor.commit();
		    adb.setNegativeButton("Retour au menu", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0,
						int arg1) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity().getApplicationContext(),MainActivity.class);
					getActivity().startActivity(i);
				}
		    });	
		    adb.setNeutralButton("Recommencer lvl", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,
							int arg1) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity().getApplicationContext(),InGame.class);
						Bille bille= Bille.getBille();
						chrono.reset();
						bille.setVies(8);
						i.putExtra("lvl", InGame.getlvl());
						getActivity().startActivity(i);
					}
		        });
			 adb.setPositiveButton("Prochain lvl", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,
							int arg1) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity().getApplicationContext(),InGame.class);
						Bille bille= Bille.getBille();
						chrono.reset();
						bille.setVies(8);
						i.putExtra("lvl", InGame.getlvl()+1);
						getActivity().startActivity(i);
					}
		        });
			 
			 return adb.create();
	 }
}

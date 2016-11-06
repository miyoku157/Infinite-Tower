package com.example.jeuandroid;


import Class.Bille;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class BtnPerdu extends DialogFragment {
	 public Dialog onCreateDialog(Bundle savedInstanceState) {
		 AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		    adb.setTitle("Désolé, vous avez perdu");
			 adb.setPositiveButton("Recommencez le niveau", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,
							int arg1) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity().getApplicationContext(),InGame.class);
						Bille bille= Bille.getBille();
						bille.setVies(8);
						i.putExtra("lvl", InGame.getlvl());
						getActivity().startActivity(i);				}
		        });
			 adb.setNegativeButton("Retour au menu", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,
							int arg1) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity().getApplicationContext(),MainActivity.class);
						getActivity().startActivity(i);
					}
			    });				
			 return adb.create();
	 }
}

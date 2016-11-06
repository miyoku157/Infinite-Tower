package com.example.jeuandroid;

import Class.MoteurPhysique;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class PauseFragment extends DialogFragment {
	 public Dialog onCreateDialog(Bundle savedInstanceState) {
		 AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		    adb.setTitle("Le jeu est en pause.");
			 adb.setPositiveButton("Reprendre", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,
							int arg1) {
						// TODO Auto-generated method stub
						MoteurPhysique moteur= MoteurPhysique.getMoteur(getActivity());
				        moteur.resume();
				        getActivity().setContentView(moteur);				}
		        });
			
			 return adb.create();
	 }
}
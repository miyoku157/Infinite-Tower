package com.example.jeuandroid;

import Class.Bille;
import Class.Chronometre;
import Class.MoteurPhysique;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BtnLvlListener implements OnClickListener {
	Button bouton;
	Context context;
    public BtnLvlListener(Button _bouton,Context _context) {
         this.bouton = _bouton;
         this.context=_context;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent viewEntry = new Intent(context,InGame.class);
		viewEntry.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		int lvl=Integer.parseInt((String) bouton.getText())-1;
		Bille bille= Bille.getBille();
		Chronometre chrono= Chronometre.getChrono();
		chrono.reset();
		bille.setVies(8);
		MoteurPhysique.setBillePlace(false);
		viewEntry.putExtra("lvl",lvl);
		context.startActivity(viewEntry);
	}

}

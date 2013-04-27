package com.swcm.geoQuiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class PantallaPrincipal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_principal);
	}

	private AlertDialog confirmDialog(String titulo, final String modoDeJuego) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		CharSequence text = "OK";
		builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (modoDeJuego.equals("capitales")) {
					Intent i = new Intent(PantallaPrincipal.this,
							PantallaJuegoCapitales.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
				}
				if (modoDeJuego.equals("orientacion")) {
					Intent i = new Intent(PantallaPrincipal.this,
							PantallaJuegoOrientacion.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
				}

			}
		});
		return builder.create();
	}

	private void about() {
		Context context = getApplicationContext();
		CharSequence text = "Álvaro Pérez Ramón" + '\n'
				+ "David Portilla Abellán" + '\n' + '\n'
				+ "© Todos los derechos reservados";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
		toast.show();
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.infoButton:
			about();
			break;
		case R.id.scoresButton:
			scores();
			break;
		case R.id.orientacionButton:
			confirmDialog("Pulse OK para empezar", "orientacion").show();
			break;
		case R.id.capitalesButton:
			confirmDialog("Pulse OK para empezar", "capitales").show();
			break;
		case R.id.mapaButton:
			irAlMapa();
		}
	}

	private void scores() {
		Intent i = new Intent(PantallaPrincipal.this,
				PantallaPuntuaciones.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
	}

	private void irAlMapa() {
		Intent i = new Intent(PantallaPrincipal.this, PantallaMapa.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
	}

}

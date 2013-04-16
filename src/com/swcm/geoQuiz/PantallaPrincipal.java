package com.swcm.geoQuiz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class PantallaPrincipal extends Activity {

	/**
	 * TAG para las trazas
	 */
	private static final String TAG = "PantallaPrincipal";

	private static List<Integer> index = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_principal);
	}
	
	private AlertDialog pregunta(String pregunta, String[] respuestas) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(pregunta).setCancelable(false)
				.setItems(respuestas, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// The 'which' argument contains the index position
						// of the selected item
						Log.i(TAG, "valor de which: " + which);
						switch (which) {
						case 0:
							index.add(0);
							break;
						case 1:
							index.add(1);
							break;
						case 2:
							index.add(2);
							break;
						case 3:
							index.add(3);
						}
						mostrarResultado();
					}
				}).create();
		
		return builder.create();
	}
	
	private AlertDialog confirmDialog(String titulo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		CharSequence text = "OK";
		builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	Intent i = new Intent(PantallaPrincipal.this,
						PantallaJuego.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
            }
        });
		return builder.create();
	}

	private void mostrarResultado() {
		Context context = getApplicationContext();
		CharSequence text = "Respuesta elegida: null";
		try {
			text = "Respuesta elegida: " + PantallaPrincipal.index.get(0);
		} catch(IndexOutOfBoundsException e) {}
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
		toast.show();
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
		PantallaPrincipal.index.clear();
		switch (view.getId()) {
		case R.id.infoButton:
			about();
			break;
		case R.id.orientacionButton:
			confirmDialog("Pulse OK para empezar").show();
			break;
		case R.id.capitalesButton:
			break;
		}
	}

	@Override
	public void onBackPressed() {
	}

}

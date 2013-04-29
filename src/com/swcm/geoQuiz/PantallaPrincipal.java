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

/**
 * 
 * @author David Portilla
 * @author Álvaro Pérez
 * @version 29-4-2013
 */
public class PantallaPrincipal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_principal);
	}

	/**
	 * Lanza el juego seleccionado al pulsar OK en el AlertDialog que devuelve
	 * 
	 * @param titulo
	 * @param modoDeJuego
	 * @return AlertDialog
	 */
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

	/**
	 * Información sobre la aplicación y los autores
	 */
	private void about() {
		Context context = getApplicationContext();
		CharSequence text = "Juego de geografía con preguntas sobre orientación y capitales."
				+ '\n'
				+ '\n'
				+ "Para ver las puntuaciones pulse el botón con la estrella"
				+ '\n'
				+ '\n'
				+ "Para modificar el nombre de usuario y borrar las puntuaciones pulse el botón de ajustes (al lado del anterior)."
				+ '\n'
				+ '\n'
				+ "En consultar mapa introduzca dos direcciones y pulse submit. Solo funcionará si ha introducido dos ciudades."
				+ '\n'
				+ '\n'
				+ "Álvaro Pérez Ramón"
				+ '\n'
				+ "David Portilla Abellán"
				+ '\n'
				+ '\n'
				+ "© Todos los derechos reservados";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * onClick para todas los botones de la actividad
	 * 
	 * @param view
	 */
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.infoButton:
			about();
			break;
		case R.id.scoresButton:
			scores();
			break;
		case R.id.settingsButton:
			settings();
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

	/**
	 * Pasa a PantallaAjustes
	 */
	private void settings() {
		Intent i = new Intent(PantallaPrincipal.this, PantallaAjustes.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);

	}

	/**
	 * Pasa a PantallaPuntuaciones
	 */
	private void scores() {
		Intent i = new Intent(PantallaPrincipal.this,
				PantallaPuntuaciones.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
	}

	/**
	 * Pasa a PantallaMapa
	 */
	private void irAlMapa() {
		Intent i = new Intent(PantallaPrincipal.this, PantallaMapa.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
	}

}

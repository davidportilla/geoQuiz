package com.swcm.geoQuiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.swcm.geoQuiz.model.DbOpenHelper;

/**
 * 
 * @author David Portilla
 * @author Álvaro Pérez
 * @version 29-4-2013
 */
public class PantallaAjustes extends Activity {

	private DbOpenHelper db;
	private EditText usuario;

	/**
	 * onCreate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_ajustes);

		usuario = (EditText) findViewById(R.id.editText);

		db = new DbOpenHelper(this);
		db.getReadableDatabase();

	}

	/**
	 * Confirm dialog para borrar las puntuaciones de la base de datos
	 * @param titulo
	 * @return AlertDialog
	 */
	private AlertDialog confirmDialog(String titulo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		CharSequence text = "OK";
		builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// BORRA LA TABLA DE PUNTUACIONES
				db.deleteAllScores();
			}
		});
		return builder.create();
	}

	/**
	 * Guarda el nombre del EditText en sharedPreferences sustityendo al que hubiese
	 * @param view
	 */
	public void onClickUsuario(View view) {
		SharedPreferences prefs = this.getSharedPreferences("com.swcm.geoQuiz",
				Context.MODE_PRIVATE);
		String key = "com.example.app.username";
		prefs.edit().remove(key);
		prefs.edit().putString(key, usuario.getText().toString()).commit();
	}

	/**
	 * Lanza el confirmDialog para borrar las puntuaciones
	 * @param view
	 */
	public void onClickBorrar(View view) {
		confirmDialog("¿Está seguro?").show();
	}

	/**
	 * onPause. Cierra la base de datos
	 */
	@Override
	protected void onPause() {
		super.onPause();
		db.close();
	}

	/**
	 * onResume. Abre la base de datos
	 */
	@Override
	protected void onResume() {
		super.onResume();
		db.getReadableDatabase();
	}

}

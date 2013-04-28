package com.swcm.geoQuiz;

import java.util.List;

import com.swcm.geoQuiz.model.Ciudad;
import com.swcm.geoQuiz.model.DbOpenHelper;

import android.os.Bundle;
import android.util.Log;

public class PantallaJuegoOrientacion extends PantallaJuego {

	private DbOpenHelper db = new DbOpenHelper(this);
	private static final int NUM_PREGUNTAS = 7;
	private static final int NUM_RESPUESTAS = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void crearJuego() {

		db.getReadableDatabase();

		// Obtengo toda la lista de ciudades
		List<Ciudad> ciudades = db.getAllCities();

		// Seguir...........
	}

	@Override
	public int calcularPuntuacion() {
		int n = 0;
		for (Boolean b : aciertos) {
			if (b)
				n++;
		}
		int time = 0;
		for (Integer i : timeForAnswer) {
			time += i;
		}
		Log.i("ACIERTOS", "" + n);
		Log.i("TIEMPO", "" + time);
		return n * 10000 / aciertos.size() / time;
	}

	@Override
	public void guardarPuntuacion() {
		// De momento no implementar
	}

	@Override
	protected void onPause() {
		super.onPause();
		db.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		db.getReadableDatabase();
	}
}

package com.swcm.geoQuiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.swcm.geoQuiz.model.Ciudad;
import com.swcm.geoQuiz.model.DbOpenHelper;
import com.swcm.geoQuiz.model.Puntuacion;

import android.util.Log;

public class PantallaJuegoOrientacion extends PantallaJuego {

	private DbOpenHelper db = new DbOpenHelper(this);
	private static final int NUM_PREGUNTAS = 7;
	private static final int NUM_RESPUESTAS = 5;
	private static final String MODO = "orientacion";

	@Override
	public void crearJuego() {

		db.getReadableDatabase();

		// Obtengo toda la lista de ciudades
		List<Ciudad> ciudades = db.getAllCities();

		for(Ciudad c: ciudades) {
			Log.d("LATLNG", ""+c.getLatitud() + ", "+c.getLongitud());
		}
		
		// Reordeno aleatoriamente
		Collections.shuffle(ciudades);

		// Variable auxiliar
		int aleatorio = 0;

		// Para cada pregunta
		for (int i = 0; i < ciudades.size(); i++) {
			List<Ciudad> opciones = new ArrayList<Ciudad>();
			opciones.clear();

			// Hay que encontrar otras tres respuestas
			int k = 0;
			while (k < NUM_RESPUESTAS) {
				aleatorio = (int) (Math.random() * ciudades.size());
				if (!opciones.contains(ciudades.get(aleatorio))) {
					opciones.add(ciudades.get(aleatorio));
					k++;
				}
				// Log.w("PREGUNTASORIENTACION",
				// ciudades.get(aleatorio).getNombre() +
				// ciudades.get(aleatorio).getLatitud()
				// + ciudades.get(aleatorio).getLongitud());

			}

			// Reordeno las respuestas
			Collections.shuffle(opciones);

			// HASTA AQUÍ FUNCIONA PERFECTO

			// Las meto en respuestas para jugar con ellas
			List<String> respPrevias = new ArrayList<String>();

			for (Ciudad c : opciones) {
				respPrevias.add(c.getNombre());
				Log.d("RESPX", c.getNombre() + c.getLatitud());
			}
			respuestas.add(respPrevias);
			// Compruebo en qué índice está la solución correcta y la guardo
			int solucion = 0;
			//opciones.get(solucion).setCoordenadas(this);
			for (int j = 1; j < opciones.size(); j++) {
				//opciones.get(j).setCoordenadas(this);

				if (!(opciones.get(solucion).getLatitud() > opciones.get(j)
						.getLatitud()))
					solucion = j;

			}

			Log.i("SOLUCION", solucion + "");
			soluciones.add(solucion);

			// Salimos del bucle si ya tenemos las preguntas que queremos
			preguntas.add("¿Qué ciudad está mas al norte?");
			if (preguntas.size() == NUM_PREGUNTAS) {
				break;
			}
		}
		// Log.i("SOLUCION 0", "" + soluciones.get(0));
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

		Log.i("PUNTUACION", "guardando.....");

		String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		db.getWritableDatabase();
		Log.d("Insert: ", "Inserting ..");
		Puntuacion p = new Puntuacion("David", calcularPuntuacion(), MODO,
				today);
		db.addPuntuacion(p);
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

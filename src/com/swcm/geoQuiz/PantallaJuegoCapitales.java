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

public class PantallaJuegoCapitales extends PantallaJuego {

	private DbOpenHelper db = new DbOpenHelper(this);
	private static final int NUM_PREGUNTAS = 7;
	private static final int NUM_RESPUESTAS = 5;

	@Override
	public void crearJuego() {

		db.getReadableDatabase();
		
		// Obtengo toda la lista de ciudades
		List<Ciudad> ciudades = db.getAllCities();
		
		// Reordeno aleatoriamente
		Collections.shuffle(ciudades);
		
		// Creo una lista para guardar las respuestas de cada pregunta y la solucion
		String respuestaCorrecta = "";
		
		// Variable auxiliar
		int aleatorio = 0;
		
		// Para cada pregunta
		for(int i = 0; i < ciudades.size() ; i++) {
			List<String> respuestasPreguntaX = new ArrayList<String>();
			respuestasPreguntaX.clear();
			// Añado la pregunta si la ciudad es capital
			if(ciudades.get(i).isCapital() != 0) {
				preguntas.add("Capital de " + ciudades.get(i).getPais());
				Log.i("PREGUNTA", "Capital de " + ciudades.get(i).getPais());
				// Me quedo la respuesta correcta
				respuestaCorrecta = ciudades.get(i).getNombre();
				Log.i("RESPUESTACORRECTA", ciudades.get(i).getNombre());
				
				// La añado a las posibles respuestas de la pregunta
				respuestasPreguntaX.add(respuestaCorrecta);
				
				// Hay que encontrar otras tres respuestas
				int k = 0;
				while(k < NUM_RESPUESTAS - 1) {
					aleatorio = (int) (Math.random()*ciudades.size());
					if(!respuestasPreguntaX.contains(ciudades.get(aleatorio).getNombre())) {
						respuestasPreguntaX.add(ciudades.get(aleatorio).getNombre());
						Log.w("RESPUESTAEXTRA", ciudades.get(aleatorio).getNombre());
						k++;
					}
				}
								
				// Reordeno las respuestas
				Collections.shuffle(respuestasPreguntaX);
				
				// HASTA AQUÍ FUNCIONA PERFECTO
				
				// Las meto en respuestas para jugar con ellas
				respuestas.add(respuestasPreguntaX);
				
				for(String s: respuestasPreguntaX){
					Log.d("RESPX", s);
				}
				
				// Compruebo en qué índice está la solución correcta y la guardo
				for (int j = 0; j < respuestasPreguntaX.size(); j++) {
					if(respuestasPreguntaX.get(j).equals(respuestaCorrecta)){
						soluciones.add(j);
						Log.i("SOLUCION", respuestasPreguntaX.get(j) + " index: " + j);
						break;
					}
				}
				
				// Salimos del bucle si ya tenemos las preguntas que queremos
				if(preguntas.size() == NUM_PREGUNTAS) {
					break;
				}
			}
			//Log.i("SOLUCION 0", "" + soluciones.get(0));
		}
				
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
		Puntuacion p = new Puntuacion("David", calcularPuntuacion(), today);
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

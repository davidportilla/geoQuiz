package com.swcm.geoQuiz;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class PantallaJuegoCapitales extends PantallaJuego {

	@Override
	public void crearJuego() {

		// Inicializar base de datos aquí
		// DbOpenHelper db = new DbOpenHelper(this);

		// Inserting Cities
		// Log.d("Insert: ", "Inserting ..");
		// Ciudad c = new Ciudad("Madrid", "España", 1);
		// c.setCoordenadas(super.getApplicationContext());
		// db.addCity(c);

		preguntas.add("Capital de España");
		preguntas.add("Capital de Portugal");
		preguntas.add("Capital de Alemania");
		final List<String> resp0 = new ArrayList<String>();
		final List<String> resp1 = new ArrayList<String>();
		final List<String> resp2 = new ArrayList<String>();
		resp0.add("Madrid");
		resp0.add("Barcelona");
		resp0.add("Bilbao");
		resp1.add("Lisboa");
		resp1.add("Oporto");
		resp2.add("Múnich");
		resp2.add("Bonn");
		resp2.add("Berlín");
		respuestas.add(resp0);
		respuestas.add(resp1);
		respuestas.add(resp2);
		soluciones.add(0);
		soluciones.add(0);
		soluciones.add(2);
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

	}

}

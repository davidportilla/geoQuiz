package com.swcm.geoQuiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class PantallaJuego extends Activity {

	private List<Integer> itemsSelected = new ArrayList<Integer>();
	
	private List<String> preguntas = new ArrayList<String>();
	private List<Integer> soluciones = new ArrayList<Integer>();
	private List<List<String>> respuestas = new ArrayList<List<String>>();
	
	// Este método es para probar. Su funcionalidad será parte del modelo
	private void crearJuego() {
		soluciones = Arrays.asList(0, 3, 2);
		preguntas = Arrays.asList("p 1", "p 2", "p 3");
		final List<String> resp0 = new ArrayList<String>();
		final List<String> resp1 = new ArrayList<String>();
		final List<String> resp2 = new ArrayList<String>();
		resp0.add("resp0"); resp0.add("resp1"); resp0.add("resp2");
		resp1.add("resp4"); resp1.add("resp5"); resp1.add("resp6"); resp1.add("resp7");
		resp2.add("resp8"); resp2.add("resp9"); resp2.add("resp10");
		respuestas.add(resp0);
		respuestas.add(resp1);
		respuestas.add(resp2);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		crearJuego();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_principal);
	}
		
}

package com.swcm.geoQuiz.model;

public class Juego {

	private String[] preguntas;
	private int[] respuestaCorrecta;
	private String[][] respuestas;
	
	public Juego(String[] preguntas, int[] respuestaCorrecta, String[][] respuestas) {
		this.preguntas = preguntas;
		this.respuestaCorrecta = respuestaCorrecta;
		this.respuestas = respuestas;
	}
		
}

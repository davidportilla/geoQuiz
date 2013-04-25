package com.swcm.geoQuiz.model;

import java.util.ArrayList;
import java.util.List;

public class Juego {

	
	public List<ElementoJuego> getJuego(DbHelper dbJuego){
		List<ElementoJuego> listaTotal = dbJuego.getElementos();
		List<ElementoJuego> listaJuego =  new ArrayList<ElementoJuego>();
		int indice = (int) (Math.random()*listaTotal.size());
		for(int i=0; i==10; i++){
			listaJuego.add(listaTotal.get(indice));
		}
		return listaJuego;
	}

}

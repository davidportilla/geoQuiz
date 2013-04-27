package com.swcm.geoQuiz.model;

public class Puntuacion {

	private String nombre;
	private int puntuacion;
	private String fecha;
	
	public Puntuacion() {
		this.nombre = "";
		this.puntuacion = 0;
		this.fecha = "";
	}
	
	public Puntuacion(String nombre, int puntuacion, String fecha) {
		this.nombre = nombre;
		this.puntuacion = puntuacion;
		this.fecha = fecha;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;	
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getPuntuacion() {
		return this.puntuacion;
	}

	public String getFecha() {
		return this.fecha;
	}
	
	@Override
	public String toString() {
		return nombre + "   " + puntuacion + "   " + fecha;
	}

}

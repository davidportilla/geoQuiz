package com.swcm.geoQuiz.model;

/**
 * 
 * @author David Portilla
 * @author Álvaro Pérez
 * @version 29-4-2013
 */
public class Puntuacion {

	private String nombre;
	private int puntuacion;
	private String fecha;
	private String modo;
	
	/**
	 * Constructor que inicializa todo a cero o String vacías
	 */
	public Puntuacion() {
		this.nombre = "";
		this.puntuacion = 0;
		this.fecha = "";
		this.modo = "";
	}
	
	/**
	 * Constructor
	 * @param nombre
	 * @param puntuacion
	 * @param modo
	 * @param fecha
	 */
	public Puntuacion(String nombre, int puntuacion, String modo, String fecha) {
		this.nombre = nombre;
		this.modo = modo;
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

	public void setModo(String modo){
		this.modo = modo;
	}
	public String getFecha() {
		return this.fecha;
	}
	
	public String getModo(){
		return this.modo;
	}
	
	@Override
	public String toString() {
		return modo.toUpperCase() + ":  " + puntuacion + "    " + nombre + ",  " + fecha;
	}
	
}

package com.swcm.geoQuiz.model;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

/**
 * @author David Portilla
 * @author Álvaro Pérez
 * @version 29-4-2013
 *
 * Clase Ciudad. Una ciudad tiene un nombre, pertenece a un país, puede ser capital y tiene latitud y longitud
 */
public class Ciudad {

	private String nombre;
	private String pais;
	private int isCapital;
	private double latitud;
	private double longitud;
	
	/**
	 * Constructor. Inicializa coordenadas a cero y asigna String vacías a los demás atributos
	 */
	public Ciudad() {
		this.nombre = "";
		this.pais = "";
		this.isCapital = 0;
		this.latitud = 0;
		this.longitud = 0;
	}
	
	/**
	 * 
	 * @param nombre
	 * @param pais
	 * @param isCapital
	 * @param latitud
	 * @param longitud
	 */
	public Ciudad(String nombre, String pais, int isCapital, double latitud, double longitud) {
		this.nombre = nombre;
		this.pais = pais;
		this.isCapital = isCapital;
		this.latitud = latitud;
		this.longitud = longitud;	
	}
	
	/**
	 * Iniciaaliza las coordenadas a cero
	 * @param nombre
	 * @param pais
	 * @param isCapital
	 */
	public Ciudad(String nombre, String pais, int isCapital) {
		this.nombre = nombre;
		this.pais = pais;
		this.isCapital = isCapital;
		this.latitud = 0;
		this.longitud = 0;	
	}
	
	/**
	 * 
	 * @param context
	 */
	public void setCoordenadas(Context context) {
		List<Address> foundGeocode = null;
		try {
			foundGeocode = new Geocoder(context).getFromLocationName(nombre + " " + pais,
					1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (foundGeocode != null) {
			this.latitud = foundGeocode.get(0).getLatitude(); // getting latitude
			this.longitud = foundGeocode.get(0).getLongitude(); // getting longitude
			//Log.i("COORDENADAS", this.nombre + ": " + this.latitud + ", " + this.longitud);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public double getLatitud() {
		return latitud;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * 
	 * @return
	 */
	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombre;
	}

	/**
	 * 
	 * @return
	 */
	public String getPais() {
		// TODO Auto-generated method stub
		return this.pais;
	}

	/**
	 * 
	 * @return
	 */
	public int isCapital() {
		// TODO Auto-generated method stub
		return this.isCapital;
	}

	/**
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
		
	}

	/**
	 * 
	 * @param pais
	 */
	public void setPais(String pais) {
		this.pais = pais;	
	}

	/**
	 * 
	 * @param isCapital
	 */
	public void setCapital(int isCapital) {
		this.isCapital = isCapital;
	}
	
	/**
	 * 
	 * @param latitud
	 */
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	/**
	 * 
	 * @param longitud
	 */
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
}

package com.swcm.geoQuiz.model;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

public class Ciudad {

	private String nombre;
	private String pais;
	private int isCapital;
	private double latitud;
	private double longitud;
	
	public Ciudad() {
		this.nombre = "";
		this.pais = "";
		this.isCapital = 0;
		this.latitud = 0;
		this.longitud = 0;
	}
	
	public Ciudad(String nombre, String pais, int isCapital, double latitud, double longitud) {
		this.nombre = nombre;
		this.pais = pais;
		this.isCapital = isCapital;
		this.latitud = latitud;
		this.longitud = longitud;	
	}
	
	public Ciudad(String nombre, String pais, int isCapital) {
		this.nombre = nombre;
		this.pais = pais;
		this.isCapital = isCapital;
		this.latitud = 0;
		this.longitud = 0;	
	}
	
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
	
	public double getLatitud() {
		return latitud;
	}
	
	public double getLongitud() {
		return longitud;
	}

	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombre;
	}

	public String getPais() {
		// TODO Auto-generated method stub
		return this.pais;
	}

	public int isCapital() {
		// TODO Auto-generated method stub
		return this.isCapital;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
		
	}

	public void setPais(String pais) {
		this.pais = pais;	
	}

	public void setCapital(int isCapital) {
		this.isCapital = isCapital;
	}
	
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
}

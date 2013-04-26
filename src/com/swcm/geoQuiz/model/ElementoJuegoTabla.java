package com.swcm.geoQuiz.model;

import com.swcm.geoQuiz.model.ElementoJuegoColumnas;;



public class ElementoJuegoTabla implements ElementoJuegoColumnas {

	public final static String TABLE_NAME = "ciudades";

	public final static String[] COLS = { ElementoJuegoColumnas._ID,
		ElementoJuegoColumnas.CIUDAD, ElementoJuegoColumnas.PAIS,
		ElementoJuegoColumnas.CAPITAL };

	public static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CIUDAD + " TEXT NOT NULL, " + PAIS + " TEXT, " + CAPITAL
			+ " NUMERIC );";
}




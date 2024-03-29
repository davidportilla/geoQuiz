package com.swcm.geoQuiz.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Clase que extiende a SQLiteOpenHelper para gestionar la base de datos
 * 
 * @author David Portilla
 * @author Álvaro Pérez
 * @version 29-4-2013
 *
 */
public class DbOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ciudades.db";
	private static final int DATABASE_VERSION = 2;

	private static final String TABLE_CITIES = "ciudades";
	private static final String TABLE_SCORES = "puntuaciones";

	private static final String KEY_ID = "_id";
	private static final String KEY_CIUDAD = "ciudad";
	private static final String KEY_PAIS = "pais";
	private static final String KEY_ISCAPITAL = "is_capital";
	private static final String KEY_LATITUD = "latitud";
	private static final String KEY_LONGITUD = "longitud";

	private static final String KEY_NOMBRE = "nombre";
	private static final String KEY_PUNTUACION = "puntuacion";
	private static final String KEY_FECHA = "fecha";
	private static final String KEY_MODO = "modo";

	/**
	 * query para crear la tabla de ciudades
	 */
	private static final String DB_TABLE_CREATE_CITIES = "CREATE TABLE "
			+ TABLE_CITIES + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CIUDAD + " TEXT, "
			+ KEY_PAIS + " TEXT, " + KEY_ISCAPITAL + " INTEGER, " + KEY_LATITUD
			+ " DOUBLE, " + KEY_LONGITUD + " DOUBLE " + ");";

	/**
	 * query para crear la tabla de puntuaciones
	 */
	private static final String DB_TABLE_CREATE_SCORES = "CREATE TABLE "
			+ TABLE_SCORES + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOMBRE + " TEXT, "
			+ KEY_PUNTUACION + " INTEGER, " + KEY_MODO + " TEXT, "
			+ KEY_FECHA + " TEXT);";

	/**
	 * Constructor para crear una base de datos
	 * 
	 * @param context
	 */
	public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Crea la base de datos
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_TABLE_CREATE_CITIES);
		db.execSQL(DB_TABLE_CREATE_SCORES);
	}

	/**
	 * Inicializa la tabla de ciudades añadiendo unas cuantas
	 * @param context
	 */
	public void inicializa(Context context) {
		List<Ciudad> ciudades = new ArrayList<Ciudad>();

		ciudades.add(new Ciudad("Tirana", "Albania", 1));
		ciudades.add(new Ciudad("Berlín", "Alemania", 1));
		ciudades.add(new Ciudad("Erevan", "Armenia", 1));
		ciudades.add(new Ciudad("Viena", "Austria", 1));
		ciudades.add(new Ciudad("Bakú", "Azerbaiyan", 1));
		ciudades.add(new Ciudad("Bruselas", "Bélgica", 1));
		ciudades.add(new Ciudad("Sarajevo", "Bosnia-Heregovina", 1));
		ciudades.add(new Ciudad("Sofía", "Bulgaria", 1));
		ciudades.add(new Ciudad("Nicosia", "Chipre", 1));
		ciudades.add(new Ciudad("Zagreb", "Croacia", 1));
		ciudades.add(new Ciudad("Copenhague", "Dinamarca", 1));
		ciudades.add(new Ciudad("Liubliana", "Eslovenia", 1));
		ciudades.add(new Ciudad("Madrid", "España", 1));
		ciudades.add(new Ciudad("Tallin", "Estonia", 1));
		ciudades.add(new Ciudad("Helsinki", "Finlandia", 1));
		ciudades.add(new Ciudad("París", "Francia", 1));
		ciudades.add(new Ciudad("Tiflis", "Georgia", 1));
		ciudades.add(new Ciudad("Atenas", "Grecia", 1));
		ciudades.add(new Ciudad("Amsterdam", "Países Bajos", 1));
		ciudades.add(new Ciudad("Budapest", "Hungría", 1));
		ciudades.add(new Ciudad("Dublín", "Irlanda", 1));
		ciudades.add(new Ciudad("Reikiavik", "Islandia", 1));
		ciudades.add(new Ciudad("Roma", "Italia", 1));

		ciudades.add(new Ciudad("Barcelona", "España", 0));
		ciudades.add(new Ciudad("Bilbao", "España", 0));
		ciudades.add(new Ciudad("Valencia", "España", 0));
		ciudades.add(new Ciudad("Londres", "Inglaterra", 1));
		ciudades.add(new Ciudad("Lyon", "Francia", 0));
		ciudades.add(new Ciudad("Lisboa", "Portugal", 1));
		ciudades.add(new Ciudad("Oporto", "Portugal", 0));
		ciudades.add(new Ciudad("Estocolmo", "Suecia", 1));
		ciudades.add(new Ciudad("Tampere", "Finlandia", 0));
		ciudades.add(new Ciudad("Milan", "Italia", 0));
		ciudades.add(new Ciudad("Venecia", "Italia", 0));
		ciudades.add(new Ciudad("Graz", "Austria", 0));
		ciudades.add(new Ciudad("Sao Paulo", "Brasil", 0));
		ciudades.add(new Ciudad("Río de Janeiro", "Brasil", 0));
		ciudades.add(new Ciudad("Brasilia", "Brasil", 1));

		for (Ciudad c : ciudades) {
			c.setCoordenadas(context);
			Log.d("LATITUD", ""+c.getLatitud());
			addCity(c);
		}

	}

	/**
	 * Actualiza la base de datos si hay una nueva versión
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);

		// Create tables again
		onCreate(db);

	}

	/**
	 * Añade una nueva ciudad a la base de datos
	 * 
	 * @param c
	 */
	public void addCity(Ciudad c) {

		// Validador para evitar nulos
		if (c == null) {
			Log.w("DATABASE", "Intento de meter null");
			return;
		}

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CIUDAD, c.getNombre());
		values.put(KEY_PAIS, c.getPais());
		values.put(KEY_ISCAPITAL, c.isCapital());
		values.put(KEY_LATITUD, c.getLatitud());
		values.put(KEY_LONGITUD, c.getLongitud());

		// Inserting Row
		db.insert(TABLE_CITIES, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * 
	 * @param id
	 * @return ciudad en la fila con el id que se pasa como parametro
	 */
	public Ciudad getCity(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CITIES,
				new String[] { KEY_ID, KEY_CIUDAD, KEY_PAIS, KEY_ISCAPITAL,
						KEY_LATITUD, KEY_LONGITUD }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Ciudad c = new Ciudad(cursor.getString(1), cursor.getString(2),
				cursor.getInt(3), cursor.getDouble(4), cursor.getDouble(5));
		// return city
		return c;
	}

	/**
	 * 
	 * @return lista con todas las ciudades de la base de datos
	 */
	public List<Ciudad> getAllCities() {
		List<Ciudad> citiesList = new ArrayList<Ciudad>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CITIES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Ciudad c = new Ciudad();
				c.setNombre(cursor.getString(1));
				c.setPais(cursor.getString(2));
				c.setCapital(cursor.getInt(3));
				c.setLatitud(cursor.getDouble(4));
				c.setLongitud(cursor.getDouble(5));
				// Adding city to list
				citiesList.add(c);
			} while (cursor.moveToNext());
		}

		return citiesList;
	}

	/**
	 * 
	 * @return número de filas de la tabla de ciudades
	 */
	public int getCitiesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CITIES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	/**
	 * Actualiza una ciudad
	 * 
	 * @param id
	 *            de la ciudad a actualizar
	 * @param c
	 *            Ciudad a introducir en esa fila
	 * @return int con código de error
	 */
	public int updateCity(int id, Ciudad c) {

		// Validador para evitar nulos
		if (c == null) {
			Log.w("DATABASE", "Intento de meter null");
			return -1;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CIUDAD, c.getNombre());
		values.put(KEY_PAIS, c.getPais());
		values.put(KEY_ISCAPITAL, c.isCapital());
		values.put(KEY_LATITUD, c.getLatitud());
		values.put(KEY_LONGITUD, c.getLongitud());

		// updating row
		return db.update(TABLE_CITIES, values, KEY_CIUDAD + " = ?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * Elimina una ciudad
	 * 
	 * @param id
	 *            de la ciudad a eliminar
	 */
	public void deleteCity(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CITIES, KEY_ID + " = ?", new String[] { "" + id });
		db.close();
	}
	
	/**
	 * Elimina todas las filas de la tabla de puntuaciones
	 */
	public void deleteAllScores() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SCORES, null, null);
	}

	/**
	 * Añade una nueva puntuación a la tabla de puntuaciones
	 * @param p Puntuacion
	 */
	public void addPuntuacion(Puntuacion p) {

		// Validador para evitar nulos
		if (p == null) {
			Log.w("DATABASE", "Intento de meter null");
			return;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOMBRE, p.getNombre());
		values.put(KEY_PUNTUACION, p.getPuntuacion());
		values.put(KEY_MODO, p.getModo());
		values.put(KEY_FECHA, p.getFecha());
		
		// Inserting Row
		db.insert(TABLE_SCORES, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * 
	 * @return lista con todas las puntuaciones guardadas
	 */
	public List<Puntuacion> getAllScores() {
		List<Puntuacion> puntuaciones = new ArrayList<Puntuacion>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_SCORES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Puntuacion p = new Puntuacion();
				p.setNombre(cursor.getString(1));
				p.setPuntuacion(Integer.parseInt(cursor.getString(2)));
				p.setModo(cursor.getString(3));
				p.setFecha(cursor.getString(4));
				// Adding score to list
				puntuaciones.add(p);
			} while (cursor.moveToNext());
		}

		return puntuaciones;
	}

}

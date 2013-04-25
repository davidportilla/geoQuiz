package com.swcm.geoQuiz.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private Context myContext;
	// Ruta por defecto de las bases de datos en el sistema Android
	//private static final String DB_PATH = myContext.getApplicationContext().getFilesDir().getPath()
     //       + myContext.getApplicationContext().getPackageName()
      //      + "/databases/";
	//private static String DB_PATH = "/data/data/com.swcm.geoQuiz.model/databases/";
    private String DB_PATH;
	private static String geoQuiz_database = "geoQuiz_database";

	private SQLiteDatabase db;

	// Establecemos los nombres de las columnas
	public final static String KEY_ID_JUEGO = "_id";
	public final static String KEY_COL1_JUEGO = "ciudad";
	public final static String KEY_COL2_JUEGO = "pais";
	public final static String KEY_COL3_JUEGO = "is_capital";
	public final static String KEY_ID_PUNT = "_id";
	public final static String KEY_COL1_PUNT = "nombre";
	public final static String KEY_COL2_PUNT = "fecha";
	public final static String KEY_COL3_PUNT = "puntuacion";
	// Array de strings para su uso en los diferentes métodos
	private static final String[] colsJuego = new String[] { KEY_ID_JUEGO, KEY_COL1_JUEGO,
			KEY_COL2_JUEGO, KEY_COL3_JUEGO };
	private static final String[] colsPunt = new String[] { KEY_ID_PUNT, KEY_COL1_PUNT,
		KEY_COL2_PUNT, KEY_COL3_PUNT };

	/**
	 * Constructor. Toma referencia hacia el contexto de la aplicación que lo
	 * invoca para poder acceder a los 'assets' y 'resources' de la aplicación.
	 * Crea un objeto DBOpenHelper que nos permitirá controlar la apertura de la
	 * base de datos.
	 * 
	 * @param context
	 */
	public DbHelper(Context context) {
		super(context, geoQuiz_database, null, 1);
		this.myContext = context;
		this.DB_PATH = "/data/data/geoQuiz/databases/";
	}

	/**
	 * Crea una base de datos vacía en el sistema y la reescribe con nuestro
	 * fichero de base de datos.
	 * */
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			// la base de datos existe y no hacemos nada.
		} else {
			// Llamando a este método se crea la base de datos vacía en la ruta
			// por defecto del sistema
			// de nuestra aplicación por lo que podremos sobreescribirla con
			// nuestra base de datos.
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copiando Base de Datos");
			}
		}

	}

	/**
	 * Comprueba si la base de datos existe para evitar copiar siempre el
	 * fichero cada vez que se abra la aplicación.
	 * 
	 * @return true si existe, false si no existe
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {

			String myPath = DB_PATH + geoQuiz_database;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// si llegamos aqui es porque la base de datos no existe todavía.
		}
		if (checkDB != null) {

			checkDB.close();

		}
		return checkDB != null ? true : false;
	}

	/**
	 * Copia nuestra base de datos desde la carpeta assets a la recién creada
	 * base de datos en la carpeta de sistema, desde donde podremos acceder a
	 * ella. Esto se hace con bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Abrimos el fichero de base de datos como entrada
		InputStream myInput = myContext.getAssets().open(geoQuiz_database);

		// Ruta a la base de datos vacía recién creada
		String outFileName = DB_PATH + geoQuiz_database;

		// Abrimos la base de datos vacía como salida
		OutputStream myOutput = new FileOutputStream(outFileName);

		// Transferimos los bytes desde el fichero de entrada al de salida
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Liberamos los streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void open() throws SQLException {

		// Abre la base de datos
		try {
			createDataBase();
		} catch (IOException e) {
			throw new Error("Ha sido imposible crear la Base de Datos");
		}

		String myPath = DB_PATH + geoQuiz_database;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {
		if (db != null)
			db.close();
		super.close();
	}

	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	public ElementoJuego getElemento( long _rowIndex) {
		ElementoJuego elemento = null;
		Cursor result = db.query(true, "ciudades", colsJuego, KEY_ID_JUEGO + "="
				+ _rowIndex, null, null, null, null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
			// Si la nombre no existe, devuelve una nombre con valores -1 y -1
		} else {
			if (result.moveToFirst()) {
				elemento = new ElementoJuego(
						result.getString(result.getColumnIndex(KEY_COL1_JUEGO)),
						result.getString(result.getColumnIndex(KEY_COL2_JUEGO)),
						result.getInt(result.getColumnIndex(KEY_COL3_JUEGO)));
			}
		}
		return elemento;
	}
	
	public List<ElementoJuego> getElementos() {
		List<ElementoJuego> elementos = new ArrayList<ElementoJuego>();
		Cursor result = db.query("ciudades",
		colsJuego, null, null, null, null, null, KEY_ID_JUEGO);
		if (result.moveToFirst())
		do {
			elementos.add(new ElementoJuego(
			result.getString(result.getColumnIndex(KEY_COL1_JUEGO)),
			result.getString(result.getColumnIndex(KEY_COL2_JUEGO)),
			result.getInt(result.getColumnIndex(KEY_COL3_JUEGO))));
		} 
		while(result.moveToNext());
			return elementos;
		}

/**
* INSERTAR NUEVA PUNTUACION
* */
public long insertPuntuacion(int id, String nombre, int fecha, String puntuacion) {
	ContentValues newValues = new ContentValues();
	newValues.put(KEY_ID_PUNT, id);
	newValues.put(KEY_COL1_PUNT, nombre);
	newValues.put(KEY_COL2_PUNT, fecha);
	newValues.put(KEY_COL3_PUNT, puntuacion);
	return db.insert("puntuaciones", null, newValues);
}
 
/**
* BORRAR PUNTUACION CON _id = _rowIndex
* */
public boolean removePuntuacion(long _rowIndex) {
	return db.delete("puntuaciones", KEY_ID_PUNT + "=" + _rowIndex, null) > 0;
}
 
/**
* ACTUALIZAR PUNTUACION _id = _rowIndex
* */
public boolean updatePuntuacion(int _rowIndex, String nombre, String fecha, int puntuacion ) {
	ContentValues newValues = new ContentValues();
	newValues.put(KEY_COL1_PUNT,nombre);
	newValues.put(KEY_COL2_PUNT, fecha);
	newValues.put(KEY_COL3_PUNT, puntuacion);
	return db.update("puntuaciones", newValues, KEY_ID_PUNT + "=" + _rowIndex, null) > 0;
}




}
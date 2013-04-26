package com.swcm.geoQuiz.model;

import java.util.ArrayList;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.provider.BaseColumns;

public class DbAdapter extends Service {

	private final IBinder mBinder = new LocalBinder();

	private DbHelper dbHelper;

	private SQLiteDatabase db;

	public class LocalBinder extends Binder {
		public DbAdapter getService() {
			return DbAdapter.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		dbHelper = new DbHelper(this);
		// try {
		// db = dbHelper.getWritableDatabase();
		// } catch (SQLiteException ex) {
		// db = dbHelper.getReadableDatabase();
		// }
		db = dbHelper.getDataBase();
	}

	@Override
	public void onDestroy() {
		db.close();
	}

	public Cursor getCursorItem(long _rowIndex) throws SQLException {
		Cursor result = db.query(true, ElementoJuegoTabla.TABLE_NAME,
				ElementoJuegoTabla.COLS, BaseColumns._ID + "=" + _rowIndex, null,
				null, null, null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No items found for row: " + _rowIndex);
		}
		return result;
	}

	/**
	 * Obtiene un cursor con todos los ElementoJuegos de la base de datos
	 */
	public Cursor getCursorElementoJuegos() {
		return db.query(ElementoJuegoTabla.TABLE_NAME, ElementoJuegoTabla.COLS, null,
				null, null, null, BaseColumns._ID);
	}

	public ArrayList<ElementoJuego> getAllElementoJuegos() {
		ArrayList<ElementoJuego> ElementoJuegos = new ArrayList<ElementoJuego>();
		Cursor result = db.query(ElementoJuegoTabla.TABLE_NAME, ElementoJuegoTabla.COLS,
				null, null, null, null, BaseColumns._ID);
		if (result.moveToFirst())
			do {
				ElementoJuegos.add(new ElementoJuego(result.getInt(result
						.getColumnIndex(BaseColumns._ID)), result
						.getString(result
								.getColumnIndex(ElementoJuegoColumnas.CIUDAD)),
						result.getString(result
								.getColumnIndex(ElementoJuegoColumnas.PAIS)),
						result.getInt(result
								.getColumnIndex(ElementoJuegoColumnas.CAPITAL))));
			} while (result.moveToNext());
		return ElementoJuegos;
	}

	/**
	 * INSERTAR NUEVO ElementoJuego
	 * */
	public long insertElementoJuego(String ciudad, String pais, int capital) {
		ContentValues newValues = new ContentValues();
		newValues.put(ElementoJuegoColumnas.CIUDAD, ciudad);
		newValues.put(ElementoJuegoColumnas.PAIS, pais);
		newValues.put(ElementoJuegoColumnas.CAPITAL, capital);
		return db.insert(ElementoJuegoTabla.TABLE_NAME, null, newValues);
	}

	/**
	 * BORRAR ElementoJuego CON _id = _rowIndex
	 * */
	public boolean deleteElementoJuego(int _rowIndex) {
		return db.delete(ElementoJuegoTabla.TABLE_NAME, BaseColumns._ID + "="
				+ _rowIndex, null) > 0;
	}

	/**
	 * ACTUALIZAR CIENTE _id = _rowIndex
	 * */
	public boolean updateElementoJuego(int _rowIndex, String ciudad,
			String pais, int capital) {
		ContentValues newValues = new ContentValues();
		newValues.put(ElementoJuegoColumnas.CIUDAD, ciudad);
		newValues.put(ElementoJuegoColumnas.PAIS, pais);
		newValues.put(ElementoJuegoColumnas.CAPITAL, capital);
		return db.update(ElementoJuegoTabla.TABLE_NAME, newValues, BaseColumns._ID
				+ "=" + _rowIndex, null) > 0;
	}

}

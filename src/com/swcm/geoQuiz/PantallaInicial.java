package com.swcm.geoQuiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.swcm.geoQuiz.model.DbOpenHelper;

public class PantallaInicial extends Activity {

	/**
	 * Tiempo que va a durar la pantalla inicial
	 */
	private int m_dwSplashTime = 3000;

	/**
	 * Se pone a true al ejecutar onPause(). Vuelve a false con onResume().
	 */
	private boolean m_bPaused = false;

	/**
	 * Indicador que siempre vale true.
	 */
	private boolean m_bSplashActive = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantalla_inicial);
		// Timer thread
		Thread splashTimer = new Thread() {
			public void run() {
				try {
					// Wait loop
					long ms = 0;
					while (m_bSplashActive && ms < m_dwSplashTime) {
						sleep(100);
						// Advance the timer only if we're running
						if (!m_bPaused) {
							ms += 100;
						}
					}
					// Advance to the next screen
					Intent i = new Intent(PantallaInicial.this,
							PantallaPrincipal.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
				} catch (Exception e) {
					Log.e("Splash", e.toString());
				} finally {
					finish();
				}
			}
		};
		splashTimer.start();

		// Inicializa la tabla de ciudades si está vacía
		DbOpenHelper db = new DbOpenHelper(this);
		SQLiteDatabase sql = db.getReadableDatabase();
		Cursor mCursor = sql.rawQuery("SELECT * FROM " + "ciudades", null);
		boolean rowExists;
		if (mCursor.moveToFirst()) {
			rowExists = true;
		} else {
			// EMPTY
			rowExists = false;
		}
		if (!rowExists) {
			db.inicializa(getApplicationContext());
		}
	}

	/**
	 * onPause
	 */
	protected void onPause() {
		super.onPause();
		m_bPaused = true;
	}

	/**
	 * onResume
	 */
	protected void onResume() {
		super.onResume();
		m_bPaused = false;
	}

}

package com.swcm.geoQuiz;

import com.swcm.geoQuiz.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
		overridePendingTransition(0, 0);
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
		
		//Copiar la base de datos a la memoria interna aquí
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

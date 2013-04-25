package com.swcm.geoQuiz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Clase Pantalla Juego <br>
 * <br>
 * Controlador de una ventana donde irán saliendo preguntas una tras otra. Se
 * cambiará de pregunta cuando el usuario pulse en alguna respuesta o se acabe
 * la barra de progreso.
 * 
 * @author David Portilla Abellán
 * @version 22-4-2013
 */
public class PantallaJuego extends Activity {

	private TextView mTextView;
	private ListView mListView;

	private ProgressBar mProgressBar;
	private int mProgressStatus;

	private Handler mHandler = new Handler();

	private boolean barraBloqueada = false;

	private boolean listenerBloqueado = false;

	/* Se deben inicializar con los datos que recibe del modelo */
	protected List<String> preguntas = new ArrayList<String>();
	protected List<Integer> soluciones = new ArrayList<Integer>();
	protected List<List<String>> respuestas = new ArrayList<List<String>>();

	protected List<Integer> itemsSelected = new ArrayList<Integer>();
	protected List<Boolean> aciertos = new ArrayList<Boolean>();
	protected List<Integer> timeForAnswer = new ArrayList<Integer>();

	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		crearJuego();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_juego);

		mTextView = (TextView) findViewById(R.id.textView);
		mListView = (ListView) findViewById(R.id.listView);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		configuraListener();
		actualizarPregunta();
		progreso().start();

	}

	/**
	 * Configura el listener de mListView
	 */
	private void configuraListener() {
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				if (!listenerBloqueado) {
					listenerBloqueado = true;
					
					pintarSoluciones(position, soluciones.get(0));

					timeForAnswer.add(mProgressStatus);
					soluciones.remove(0);
					itemsSelected.add(position);
					Log.i("ITEMSELECTED", "" + position);
					if (preguntas.size() != 0) {
						Log.i("PULSADO", "mProgressStatus = " + mProgressStatus);
						// Para dos segundos antes de sacar la siguiente
						// pregunta
						mProgressStatus = 0;
						mHandler.post(new Runnable() {
							public void run() {
								try {
									barraBloqueada = true;
									Thread.sleep(2000);
									barraBloqueada = false;
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								actualizarPregunta();
								listenerBloqueado = false;
							}
						});

					} else {
						// La partida termina en el thread de progreso al salir
						// del bucle.
						mProgressStatus = 200;
					}
				}
				else {
					Log.i("ONCLICKLISTENER","cerrojo puesto");
				}
			}
		});
	}

	/**
	 * Pinta la respuesta seleccionada y la solcuión. Guarda si ha habido o no
	 * acierto <br>
	 * <br>
	 * Atención: este método actualiza la UI, ejecutar siempre en el hilo
	 * principal.
	 */
	private void pintarSoluciones(int pulsada, int solucion) {
		// Verde: #99CC00
		// Rojo: FF4444
		// Amarillo: FF8800 (para timeout)
		if (pulsada == -1) {
			Log.i("UIThread", "Pinto la solución de amarillo");
			mListView.getChildAt(soluciones.get(0)).setBackgroundColor(
					Color.parseColor("#FF8800"));
			aciertos.add(false);
		} else if (pulsada == solucion) {
			Log.i("UIThread", "Pinto la solución de verde");
			mListView.getChildAt(pulsada).setBackgroundColor(
					Color.parseColor("#99CC00"));
			aciertos.add(true);
		} else {
			Log.i("UIThread",
					"Pinto la respuesta de rojo y la solución de verde");
			mListView.getChildAt(pulsada).setBackgroundColor(
					Color.parseColor("#FF4444"));
			mListView.getChildAt(soluciones.get(0)).setBackgroundColor(
					Color.parseColor("#99CC00"));
			aciertos.add(false);
		}
		mListView.invalidate();
	}

	/**
	 * Extrae y una pregunta con sus respuestas y las pone en mListView. <br>
	 * <br>
	 * Atención: este método actualiza la UI, ejecutar siempre en el hilo
	 * principal.
	 */
	private void actualizarPregunta() {

		mTextView.setText(preguntas.get(0));
		preguntas.remove(0);
		mListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, respuestas.get(0)));
		respuestas.remove(0);
	}

	/**
	 * Hilo que controla la el tiempo de la partida a través de una barra de
	 * progreso.
	 * 
	 * @return Thread progreso
	 */
	private Thread progreso() {
		// Start lengthy operation in a background thread
		return new Thread(new Runnable() {
			public void run() {
				mProgressStatus = 0;
				while (mProgressStatus <= 101) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!barraBloqueada) {
						mProgressStatus++;
						// Update the progress bar
						mHandler.post(new Runnable() {
							public void run() {
								//Log.i("ACTUALIZA BARRA", "mProgressStatus = "
								//		+ mProgressStatus);
								mProgressBar.setProgress(mProgressStatus);
							}
						});
					}

					// Si quedan más preguntas me quedo en el bucle
					if (mProgressStatus == 100 && preguntas.size() != 0) {
						runOnUiThread(new Runnable() {
							public void run() {
								itemsSelected.add(-1);
								Log.i("ITEMSELECTED", "-1");
								pintarSoluciones(-1, soluciones.get(0));
								soluciones.remove(0);
								timeForAnswer.add(mProgressStatus);
								mProgressStatus = 0;
							}
						});
						// Para dos segundos antes de sacar la siguiente
						// pregunta
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mHandler.post(new Runnable() {
							public void run() {
								Log.i("HANDLER", "saco la siguiente pregunta");
								actualizarPregunta();
							}
						});
					}
				}
				// Al salir del bucle
				if (mProgressStatus == 102) {
					runOnUiThread(new Runnable() {
						public void run() {
							itemsSelected.add(-1);
							Log.i("ITEMSELECTED", "-1");
							pintarSoluciones(-1, soluciones.get(0));
							soluciones.remove(0);
							timeForAnswer.add(mProgressStatus);
						}
					});
					// Para dos segundos
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				runOnUiThread(new Runnable() {
					public void run() {
						int n = calcularPuntuacion();
						guardarPuntuacion();
						confirmDialog("PUNTUACION: " + n).show();
					}
				});
			}
		});
	}

	protected int calcularPuntuacion() {
		return 0;
	}

	protected void guardarPuntuacion() {
		return;
	}

	private AlertDialog confirmDialog(String titulo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		CharSequence text = "OK";
		builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent i = new Intent(PantallaJuego.this,
						PantallaPrincipal.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
			}
		});
		return builder.create();
	}

	// Este método es para probar. Su funcionalidad será parte del modelo
	protected void crearJuego() {

	}

	@Override
	public void onBackPressed() {
		// No se puede volver a la pantalla principal en medio de una partida
		return;
	}

}

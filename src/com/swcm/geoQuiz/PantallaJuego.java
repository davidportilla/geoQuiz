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

public class PantallaJuego extends Activity {

	private TextView mTextView;
	private ListView mListView;

	private ProgressBar mProgressBar;
	private int mProgressStatus;
	private Handler mHandler = new Handler();

	private List<Integer> itemsSelected = new ArrayList<Integer>();
	private List<String> preguntas = new ArrayList<String>();
	private List<Integer> soluciones = new ArrayList<Integer>();
	private List<List<String>> respuestas = new ArrayList<List<String>>();
	
	private List<Boolean> aciertos = new ArrayList<Boolean>();
	private List<Integer> timeForAnswer = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		crearJuego();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_juego);

		mTextView = (TextView) findViewById(R.id.textView);
		mListView = (ListView) findViewById(R.id.listView);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public synchronized void onItemClick(AdapterView<?> arg0,
					View arg1, int position, long arg3) {

				if (position == soluciones.get(0)) {
					arg1.setBackgroundColor(Color.parseColor("#99CC00"));
					aciertos.add(true);
				} else {
					arg1.setBackgroundColor(Color.parseColor("#FF4444"));
					mListView.getChildAt(soluciones.get(0)).setBackgroundColor(
							Color.parseColor("#99CC00"));
					aciertos.add(false);
				}
				timeForAnswer.add(mProgressStatus);
				soluciones.remove(0);
				mListView.invalidate();
				itemsSelected.add(position);
				Log.i("ITEMSELECTED", "" + position);

				if (preguntas.size() != 0) {
					mProgressStatus = 0;
					Log.i("PULSADO", "mProgressStatus = " + mProgressStatus);
					mHandler.post(new Runnable() {
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							actualizarPregunta();
						}
					});

				} else {
					mProgressStatus = 101;
				}

			}
		});

		actualizarPregunta();
		// Start lengthy operation in a background thread
		new Thread(new Runnable() {
			public synchronized void run() {
				mProgressStatus = 0;
				while (mProgressStatus < 100) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mProgressStatus++;
					// Update the progress bar
					mHandler.post(new Runnable() {
						public void run() {
							Log.i("ACTUALIZA BARRA", "mProgressStatus = "
									+ mProgressStatus);
							mProgressBar.setProgress(mProgressStatus);
						}
					});

					if (mProgressStatus == 99 && preguntas.size() != 0) {
						runOnUiThread(new Runnable() {
							public void run() {
								itemsSelected.add(-1);
								Log.i("ITEMSELECTED", "-1");
								soluciones.remove(0);
								aciertos.add(false);
								timeForAnswer.add(mProgressStatus + 1);
								mProgressStatus = 0;
								actualizarPregunta();
							}
						});
					}
				}
				if(mProgressStatus == 100) {
					itemsSelected.add(-1);
					Log.i("ITEMSELECTED", "-1");
					timeForAnswer.add(mProgressStatus);
				}
				runOnUiThread(new Runnable() {
					public void run() {
						int n = calcularPuntuacion();
						Log.i("ANTES DEL ALERTDIALOG", "wiiiii");
						confirmDialog("PUNTUACION: " + n).show();
					}
				});
			}
		}).start();

	}

	private void actualizarPregunta() {
		mTextView.setText(preguntas.get(0));
		preguntas.remove(0);
		mListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, respuestas.get(0)));
		respuestas.remove(0);
	}
	
	private int calcularPuntuacion() {
		int n = 0;
		for(Boolean b: aciertos){
			if(b) n++;
		}
		int time = 0;
		for(Integer i: timeForAnswer) {
			time += i;
		}
		return n*10000/aciertos.size()/time;
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
	private void crearJuego() {
		preguntas.add("p 1");
		preguntas.add("p 2");
		preguntas.add("p 3");
		final List<String> resp0 = new ArrayList<String>();
		final List<String> resp1 = new ArrayList<String>();
		final List<String> resp2 = new ArrayList<String>();
		resp0.add("resp0");
		resp0.add("resp1");
		resp0.add("resp2");
		resp1.add("resp4");
		resp1.add("resp5");
		resp1.add("resp6");
		resp1.add("resp7");
		resp2.add("resp8");
		resp2.add("resp9");
		resp2.add("resp10");
		respuestas.add(resp0);
		respuestas.add(resp1);
		respuestas.add(resp2);
		soluciones.add(0);
		soluciones.add(1);
		soluciones.add(2);
	}

}

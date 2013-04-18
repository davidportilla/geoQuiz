package com.swcm.geoQuiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PantallaJuego extends Activity {

	private ProgressBar mProgress;
	private ListView mList;
	private int mProgressStatus = 0;

	private Handler mHandler = new Handler();

	private List<Integer> itemsSelectedFromDialog = new ArrayList<Integer>();
	
	private List<String> preguntas = new ArrayList<String>();
	private List<Integer> soluciones = new ArrayList<Integer>();
	private List<List<String>> respuestas = new ArrayList<List<String>>();
	
	// Este método es para probar. Su funcionalidad será parte del modelo
	private void crearJuego() {
		soluciones = Arrays.asList(0, 3, 2);
		preguntas = Arrays.asList("p 1", "p 2", "p 3");
		final List<String> resp0 = new ArrayList<String>();
		final List<String> resp1 = new ArrayList<String>();
		final List<String> resp2 = new ArrayList<String>();
		resp0.add("resp0"); resp0.add("resp1"); resp0.add("resp2");
		resp1.add("resp4"); resp1.add("resp5"); resp1.add("resp6"); resp1.add("resp7");
		resp2.add("resp8"); resp2.add("resp9"); resp2.add("resp10");
		respuestas.add(resp0);
		respuestas.add(resp1);
		respuestas.add(resp2);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		crearJuego();
		
		super.onCreate(savedInstanceState);
		openDialog();
	}

	public void closeDialog(AlertDialog d, int selected) {
		d.dismiss();
		Log.i("PANTALLAJUEGO", "Diálogo cerrado");
	}

	public void crearDialogo() {
		
		// Adaptador para el ListView del diálogo
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item);
		
		// Premite actualizar los valores del ListView
		runOnUiThread(new Runnable() {
		    public void run() {
		        adapter.notifyDataSetChanged();
		    }
		});
		
		// Builder del diálogo insuflado de res/custom_dialog.xml
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(getLayoutInflater().inflate(R.layout.custom_dialog,
				null));
		

		// Añade el adaptador al diálogo
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				itemsSelectedFromDialog.add(item);
			}
		});
		
		// Crea el diálogo y lo muestra
		final AlertDialog d = builder.create();
		d.show();
		
		mProgress = (ProgressBar) d.findViewById(R.id.progressBar1);
		mList = (ListView) d.findViewById(R.id.list);
		
		mList.setAdapter(adapter);
		
	}
	
	public void openDialog() {
				
		adapterForQuestion.add(preguntas.get(0));
		preguntas.remove(0);
		
		adapterForAnswers.addAll(respuestas.get(0));
		respuestas.remove(0);
			

		builder.setAdapter(adapterForQuestion, null);
		
		
		
		final AlertDialog d = builder.create();
		d.show();

		mProgress = (ProgressBar) d.findViewById(R.id.progressBar1);
		
		// Start lengthy operation in a background thread
		new Thread(new Runnable() {
			public void run() {
				while (mProgressStatus < 100) {
					mProgressStatus += doWork();
					Log.i("PANTALLAJUEGO", String.valueOf(mProgressStatus));

					// Update the progress bar
					mHandler.post(new Runnable() {
						public void run() {
							mProgress.setProgress(mProgressStatus);
						}
					});
				}
				itemsSelectedFromDialog.add(-1);
				// Actualizar el AlertDialog
				Log.i("PANTALLAJUEGO", "Actualizar la lista de respuestas....");
				
				runOnUiThread(new Runnable() {
				    public void run() {
				        adapterForAnswers.clear();
				        if(preguntas.get(0) != null) {
				        	adapterForQuestion.add(preguntas.get(0));
					        preguntas.remove(0);
				        	adapterForAnswers.addAll(respuestas.get(0));
					        respuestas.remove(0);
				        }
				        else d.dismiss();
				    }
				});
				
				
			}
			private int doWork() {
			    try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			    return 1;
			}
		}).start();
				
	}
	
	
	
}

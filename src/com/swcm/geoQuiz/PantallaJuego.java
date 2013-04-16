package com.swcm.geoQuiz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

public class PantallaJuego extends Activity {

	private static final int PROGRESS = 0x1;

	private ProgressBar mProgress;
	private int mProgressStatus = 0;

	private Handler mHandler = new Handler();

	private List<Integer> itemsSelectedFromDialog = new ArrayList<Integer>();
	
	private String[] preguntas = new String[10];
	private String[][] respuestas = new String[10][4];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		for(int i = 0; i < preguntas.length; i++) {
			preguntas[i] = "Pregunta número " + i;
			for(int j = 0; j < respuestas[0].length; j++) {
				respuestas[i][j] = "Resp. no. " + j + " de la pregunta " + i;
			}
		}
		super.onCreate(savedInstanceState);
		openDialog();
	}

	public void closeDialog(AlertDialog d, int selected) {
		d.dismiss();
		Log.i("PANTALLAJUEGO", "Diálogo cerrado");
	}

	public void openDialog() {
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item);
		runOnUiThread(new Runnable() {
		    public void run() {
		    	Log.i("PANTALLAJUEGO", "antes de morir....");
		        adapter.notifyDataSetChanged();
		    }
		});
		
		adapter.addAll(respuestas[0]);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(getLayoutInflater().inflate(R.layout.custom_dialog,
				null));
		builder.setTitle(preguntas[0]);
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				itemsSelectedFromDialog.add(item);
			}
		});
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
				    	Log.i("PANTALLAJUEGO", "antes de morir....");
				        adapter.add("Nuva respuesta");
				    }
				});
				
				
			}
			private int doWork() {
			    try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    return 1;
			}
		}).start();
				
	}
	
}

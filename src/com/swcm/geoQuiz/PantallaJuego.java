package com.swcm.geoQuiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
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

	private TextView mTextView;;
	private ListView mListView;

	private ProgressBar mProgressBar;
	private int mProgressStatus;
	private Handler mHandler = new Handler();

	private boolean itemClicked = false;

	private List<Integer> itemsSelected = new ArrayList<Integer>();
	private List<String> preguntas = new ArrayList<String>();
	private List<Integer> soluciones = new ArrayList<Integer>();
	private List<List<String>> respuestas = new ArrayList<List<String>>();

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
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				itemClicked = true;
				itemsSelected.add(position);
				Log.i("ITEMSELECTED", "" + position);
				if (preguntas.size() != 0) {
					
				} else {
					mTextView.setText("FINAL");
				}

			}
		});
		actualizarPregunta();
		resetProgressBar();
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

	private void resetProgressBar() {
		mProgressStatus = 0;
		// Start lengthy operation in a background thread
		new Thread(new Runnable() {
			public void run() {
				while (mProgressStatus < 100) {
					mProgressStatus += doWork();
					// Update the progress bar
					mHandler.post(new Runnable() {
						public void run() {
							mProgressBar.setProgress(mProgressStatus);
						}
					});
				}
				itemsSelected.add(-1);
				Log.i("ITEMSELECTED", "-1");
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

	// Este método es para probar. Su funcionalidad será parte del modelo
	private void crearJuego() {
		soluciones = Arrays.asList(0, 3, 2);
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
	}

}

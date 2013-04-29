package com.swcm.geoQuiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.swcm.geoQuiz.model.DbOpenHelper;
import com.swcm.geoQuiz.model.Puntuacion;

public class PantallaPuntuaciones extends Activity {

	private DbOpenHelper db;
	private ListView lv1;
	private ListView lv2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_puntuaciones);
		// lv1 para capitales, lv2 para orientacion
		lv1 = (ListView) findViewById(R.id.list1);
		lv2 = (ListView) findViewById(R.id.list2);

		db = new DbOpenHelper(this);
		db.getReadableDatabase();

		fillData();

	}

	private void fillData() {
		// Get all of the notes from the database and create the item list
		List<Puntuacion> todo = db.getAllScores();

		if (todo.isEmpty() || todo == null) {
			Log.i("PUNTUACIONES", "no cargo ninguna puntuacion");
			return;
		}

		List<Puntuacion> capitales = new ArrayList<Puntuacion>();
		List<Puntuacion> orientacion = new ArrayList<Puntuacion>();

		for (Puntuacion p : todo) {
			if (p.getModo().equals("capitales")) {
				capitales.add(p);
			} else if (p.getModo().equals("orientacion")) {
				orientacion.add(p);
			} else {
				Log.w("PUNTUACIONES", "puntuacion con modo incorrecto");
			}
		}

		if (!capitales.isEmpty()) {
			// Ordeno la lista
			Collections.sort(capitales, new Comparator<Puntuacion>() {
				@Override
				public int compare(Puntuacion p1, Puntuacion p2) {
					return p2.getPuntuacion() - p1.getPuntuacion();
				}
			});
			// Paso la lista a una lista de String
			List<String> valuesCapitales = new ArrayList<String>();
			for (Puntuacion p : capitales) {
				valuesCapitales.add(p.toString());
			}
			Log.i("PUNT. CAP. 0", valuesCapitales.get(0));
			// Now create an array adapter and set it to display
			ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, valuesCapitales);
			lv1.setAdapter((ListAdapter) arrayAdapter1);
		}

		if (!orientacion.isEmpty()) {
			// Ordeno la lista
			Collections.sort(orientacion, new Comparator<Puntuacion>() {
				@Override
				public int compare(Puntuacion p1, Puntuacion p2) {
					return p2.getPuntuacion() - p1.getPuntuacion();
				}
			});
			// Paso la lista a una lista de String
			List<String> valuesOrientacion = new ArrayList<String>();
			for (Puntuacion p : orientacion) {
				valuesOrientacion.add(p.toString());
			}
			Log.i("PUNT. ORI. 0", valuesOrientacion.get(0));
			// Now create an array adapter and set it to display
			ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, valuesOrientacion);
			lv2.setAdapter((ListAdapter) arrayAdapter2);
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		db.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		db.getReadableDatabase();
	}

}
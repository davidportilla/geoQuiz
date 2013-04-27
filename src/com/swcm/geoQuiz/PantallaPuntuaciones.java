package com.swcm.geoQuiz;

import java.util.ArrayList;
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
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_puntuaciones);
		lv = (ListView) findViewById(R.id.list);

		db = new DbOpenHelper(this);
		db.getReadableDatabase();

		fillData();

	}

	private void fillData() {
		// Get all of the notes from the database and create the item list
		List<Puntuacion> l = db.getAllScores();
		// Ordena la lista de mayores a menores puntuaciones

		if (l == null) {
			Log.i("FILLDATA", "No hay puntuaciones");
		}

		// Pasa la lista de puntuaciones a una lista de String
		List<String> values = new ArrayList<String>();
		for (Puntuacion p : l) {
			values.add(p.toString());
		}

		// Now create an array adapter and set it to display
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		lv.setAdapter((ListAdapter) arrayAdapter);

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
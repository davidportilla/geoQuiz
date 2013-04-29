package com.swcm.geoQuiz;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * 
 * @author David Portilla
 * @author Álvaro Pérez
 * @version 29-4-2013
 *
 */
public class PantallaMapa extends FragmentActivity {

	private GoogleMap mMap;

	private EditText editText1;
	private EditText editText2;

	private String ciudad1;
	private String ciudad2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_mapa);

		Log.i("GOOGLE SERVICE",
				"" + GooglePlayServicesUtil.isGooglePlayServicesAvailable(this));

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);

		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}

		setUpMapIfNeeded();

		if (mMap != null) {
			mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		}
	}

	/**
	 * Muestra en el mapa las ciudades escritas con sus coordenadas
	 * @param view
	 */
	public void onClickButton(View view) {

		Log.i("ONCLICK MAPA", "Submit");
		if (mMap != null) {
			Log.i("ONCLICK MAPA", "hay mapa");
			if (editText1.getText().length() != 0
					&& editText2.getText().length() != 0) {
				if (editText1.getText() != null) {
					ciudad1 = editText1.getText().toString();
				}
				if (editText2.getText() != null) {
					ciudad2 = editText2.getText().toString();
				}

				List<Address> foundGeocode1 = null;
				List<Address> foundGeocode2 = null;
				/*
				 * find the addresses by using getFromLocationName() method with
				 * the given address
				 */
				try {
					foundGeocode1 = new Geocoder(this).getFromLocationName(
							ciudad1, 1);
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					foundGeocode2 = new Geocoder(this).getFromLocationName(
							ciudad2, 1);
				} catch (IOException e) {
					e.printStackTrace();
				}

				double lat1 = 0;
				double long1 = 0;
				double lat2 = 0;
				double long2 = 0;

				if (foundGeocode1 != null) {
					lat1 = foundGeocode1.get(0).getLatitude(); // getting
																// latitude
					long1 = foundGeocode1.get(0).getLongitude(); // getting
																	// longitude
					Log.i("COORDENADAS", "punto 1: " + lat1 + ", " + long1);
				}

				if (foundGeocode2 != null) {
					lat2 = foundGeocode2.get(0).getLatitude();
					long2 = foundGeocode2.get(0).getLongitude();
					Log.i("COORDENADAS", "punto 2: " + lat2 + ", " + long2);
				}

				LatLng ll1 = new LatLng(lat1, long1);
				LatLng ll2 = new LatLng(lat2, long2);

				String s = ciudad1 + ": " + ll1.toString() + "\n\n" + ciudad2
						+ ": " + ll2.toString();

				confirmDialog("Coordenadas", s).show();

				LatLng media = new LatLng((lat1 + lat2) / 2,
						(long1 + long2) / 2);

				setUpMapIfNeeded();
				try {
					mMap.moveCamera(CameraUpdateFactory.newLatLng(media));
					mMap.addMarker(new MarkerOptions().position(ll1).title(
							ciudad1 + ": " + ll1.toString()));
					mMap.addMarker(new MarkerOptions().position(ll2).title(
							ciudad2 + ": " + ll2.toString()));
				} catch (Exception e) {
					Log.e("MAPA", "Error al tocar el mapa");
				}

				// mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
				// LatLng(lat1,
				// long1), 15));
				// Zoom in, animating the camera.
				// mMap.animateCamera(CameraUpdateFactory.zoomIn());
			}
		} else {
			Log.i("ONCLICK MAPA", "no hay mapa");
			confirmDialog("Mapas no disponibles",
					"Compruebe su conexión a Internet").show();
		}

	}

	/**
	 * Do a null check to confirm that we have not already instantiated the map.
	 */
	private void setUpMapIfNeeded() {
		Log.i("SETMAP", "a crear el mapa...");
		if (mMap == null) {
			Log.i("SETMAP", "creando el mapa...");
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
				Log.i("SETMAP", "Mapa creado");
			}
		}
	}

	/**
	 * @param titulo
	 * @param mensaje
	 * @return AlertDialog
	 */
	private AlertDialog confirmDialog(String titulo, String mensaje) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		builder.setMessage(mensaje);
		CharSequence text = "OK";
		builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		return builder.create();
	}

}

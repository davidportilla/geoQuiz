package com.swcm.geoQuiz;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.MapController;

public class PantallaMapa extends FragmentActivity {

	private Geocoder geoCoder;
	private GoogleMap mMap;
	private MapController mapController;

	private EditText editText1;
	private EditText editText2;

	private String ciudad1;
	private String ciudad2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_mapa);

		setUpMapIfNeeded();
		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

		geoCoder = new Geocoder(this);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);

	}

	public void onClickButton(View view) {
		ciudad1 = editText1.getText().toString();
		ciudad2 = editText2.getText().toString();

		List<Address> foundGeocode1 = null;
		List<Address> foundGeocode2 = null;
		/*
		 * find the addresses by using getFromLocationName() method with the
		 * given address
		 */
		try {
			foundGeocode1 = new Geocoder(this).getFromLocationName(ciudad1, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			foundGeocode2 = new Geocoder(this).getFromLocationName(ciudad2, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		double lat1 = 30;
		double long1 = 30;
		double lat2 = 30.5;
		double long2 = 30;
		
		if (foundGeocode1 != null) {
			lat1 = foundGeocode1.get(0).getLatitude(); // getting latitude
			long1 = foundGeocode1.get(0).getLongitude(); // getting longitude
			Log.i("COORDENADAS", "punto 1: " + lat1 + ", " + long1);
		}
		
		if (foundGeocode2 != null) {
			lat2 = foundGeocode2.get(0).getLatitude();
			long2 = foundGeocode2.get(0).getLatitude();
			Log.i("COORDENADAS", "punto 2: " + lat2 + ", " + long2);
		}
		
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1,long1), 15));
		// Zoom in, animating the camera.
		mMap.animateCamera(CameraUpdateFactory.zoomIn());
	}

	/**
	 * Do a null check to confirm that we have not already instantiated the map.
	 */
	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
			}
		}
	}

}
/*
private static final LatLng SYDNEY = new LatLng(-33.88,151.21);
private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);

private GoogleMap map;
... // Obtain the map from a MapFragment or MapView.

// Move the camera instantly to Sydney with a zoom of 15.
map.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));

// Zoom in, animating the camera.
map.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
map.animateCamera(CameraUpdateFactory.zoomTo(10), null, 2000);

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
CameraPosition cameraPosition = new CameraPosition.Builder()
    .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
    .zoom(17)                   // Sets the zoom
    .bearing(90)                // Sets the orientation of the camera to east
    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
    .build();                   // Creates a CameraPosition from the builder
map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
*/

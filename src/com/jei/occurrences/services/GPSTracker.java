package com.jei.occurrences.services;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.jei.occurrences.model.OccurrenceLocation;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {

	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	double selectedLatitude;
	double selectedLongitude;	
	

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.mContext = context;
		
		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER ,10000, 10,this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER ,10000, 10,this);
		
		location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		if(location == null){
			
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		
		
		
		//getLocation();
	}

	public String getCurrentAddress() {
		return getAddress(latitude, longitude);
		
	}
	
	public String getSelectedAddress() {
		return getAddress(selectedLatitude, selectedLongitude);
	}
	
	

	public String getAddress(double latitude, double longitude) {
		String addressText = "Nenhum endereço encontrado.";
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		// Get the current location from the input parameter list

		OccurrenceLocation location = null;

		// Create a list to contain the result address
		List<Address> addresses = null;
		try {
			/*
			 * Return 1 address.
			 */
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
		} catch (IOException e1) {
			Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
			e1.printStackTrace();
			return ("Endereço inválido");
		} catch (IllegalArgumentException e2) {
			// Error message to post in the log
			String errorString = "Illegal arguments "
					+ Double.toString(latitude) + " , "
					+ Double.toString(longitude) + " passed to address service";
			Log.e("LocationSampleActivity", errorString);
			e2.printStackTrace();
			return errorString;
		}
		// If the reverse geocode returned an address
		if (addresses != null && addresses.size() > 0) {			
			// Get the first address
			Address address = addresses.get(0);
			addressText = "Erro ao carregar o endereço";
			if (address.getMaxAddressLineIndex() > 0) {
				addressText = address.getAddressLine(0) + " -- "
						+ address.getAddressLine(1) + " -- "
						+ address.getAddressLine(2);
				
				//Construtor location = new OccurrenceLocation(country, city, neighborhood, state, street);
				
				location = new OccurrenceLocation(address.getCountryName(),
						getCity(address.getAddressLine(1)), address.getSubLocality(),
						getState(address.getAdminArea()), address.getThoroughfare(), latitude, longitude);
				//toString  street + ", " + neighborhood + ", " + city + ", " + state + ", " + country;
				addressText = location.toString();
			}

		}

		return addressText;			
	}

	private String getState(String state) {
		String stateRes = "Desconhecido";
		
		int indexOfSeparateChar = state.indexOf("of");
		
		if (indexOfSeparateChar != -1) {
			stateRes = state.substring(indexOfSeparateChar + 2, state.length());
		} 
		
		return stateRes;
	}

	private String getCity(String city) {
		String cityRes = "Desconhecida";
		int indexOfSeparateChar = city.indexOf('-');
		
		if (indexOfSeparateChar != -1) {
			cityRes = city.substring(0, indexOfSeparateChar - 1);
		}
		
		return cityRes;
	}

	public Location getLocation() {
//		try {

//
//			// getting GPS status
//			isGPSEnabled = locationManager
//					.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//			// getting network status
//			isNetworkEnabled = locationManager
//					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//			if (!isGPSEnabled && !isNetworkEnabled) {
//				// no network provider is enabled
//			} else {
//				this.canGetLocation = true;
//				// First get location from Network Provider
//				if (isNetworkEnabled) {
//					
//					locationManager.requestLocationUpdates(
//							LocationManager.NETWORK_PROVIDER,
//							MIN_TIME_BW_UPDATES,
//							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//					Log.d("Network", "Network");
//					if (locationManager != null) {
//						location = locationManager
//								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//						if (location != null) {
//							latitude = location.getLatitude();
//							longitude = location.getLongitude();
//						}
//					}
//				}
//				// if GPS Enabled get lat/long using GPS Services
//				if (isGPSEnabled) {
//					if (location == null) {
//						locationManager.requestLocationUpdates(
//								LocationManager.GPS_PROVIDER,
//								MIN_TIME_BW_UPDATES,
//								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//						Log.d("GPS Enabled", "GPS Enabled");
//						if (locationManager != null) {
//							location = locationManager
//									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//							if (location != null) {
//								latitude = location.getLatitude();
//								longitude = location.getLongitude();
//							}
//						}
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return location;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}

		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}

		// return longitude
		return longitude;
	}

	public double getSelectedLatitude() {
		return selectedLatitude;
	}

	public void setSelectedLatitude(double selectedLatitude) {
		this.selectedLatitude = selectedLatitude;
	}

	public double getSelectedLongitude() {
		return selectedLongitude;
	}

	public void setSelectedLongitude(double selectedLongitude) {
		this.selectedLongitude = selectedLongitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location location) {
		
		this.location = location;
		
		Intent intent = new Intent("LOCALIZACAO_MODIFICADA");
		intent.putExtra("localizacao", location);
		
		mContext.sendBroadcast(intent);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
package com.jei.occurrences.view;

import java.io.Serializable;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jei.occurrences.R;

public class RegisterActivity extends Fragment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8516649067814123432L;
	private Spinner lstOccurencesType;
	private static View fragView;
	private static Context context;
	private String locationStr;
	BroadcastReceiver receiver;

	private GoogleMap map;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		locationStr = "Carregando...";
		context = container.getContext();

		if (fragView != null) {
			ViewGroup parent = (ViewGroup) fragView.getParent();
			if (parent != null)
				parent.removeView(fragView);

		}

		try {
			fragView = inflater.inflate(R.layout.activity_register, container,
					false);
		} catch (Exception e) {
			// TODO: handle exception
		}

		lstOccurencesType = (Spinner) fragView
				.findViewById(R.id.lstOccurrencesType);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				fragView.getContext(), R.array.crimesTypes,
				android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		lstOccurencesType.setAdapter(adapter);
		
		animateMap();
		final TextView text = (TextView) fragView
				.findViewById(R.id.register_location);
		text.setText(locationStr);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				locationStr = ((MainActivity) getActivity()).getGps().getSelectedAddress();
				//Toast.makeText(context, locationStr, Toast.LENGTH_SHORT).show();
				text.post(new Runnable() {
					
					@Override
					public void run() {
						text.setText(locationStr);					
					}
				});
			}
		}).start();
		
		/*receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				animateMap();
				String address = ((MainActivity) getActivity()).getGps()
						.getSelectedAddress();
				TextView text = (TextView) fragView
						.findViewById(R.id.register_location);
				text.setText(address);
			}
		};*/
		
		return fragView;
	}

	
	
	@Override
	public void onResume() {
		//iniciarMonitoramentoGPS();
		super.onResume();
	}
	@Override
	public void onPause() {
		//pararMonitoramentoGPS();
		super.onPause();
	}

	private void iniciarMonitoramentoGPS() {
		
		context.registerReceiver(receiver, new IntentFilter("LOCALIZACAO_MODIFICADA"));
		
	}
	

	private void pararMonitoramentoGPS() {
		
		context.unregisterReceiver(receiver);
		
	}


	public void animateMap() {
		map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapRegister)).getMap();

		LatLng position = ((MainActivity) getActivity()).getLastLongClick();
		((MainActivity) getActivity()).getGps().setSelectedLatitude(
				position.latitude);
		((MainActivity) getActivity()).getGps().setSelectedLongitude(
				position.longitude);

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 16);
		map.animateCamera(update);
		map.clear();
		map.addMarker(new MarkerOptions().position(position).title(
				"Você está aqui!"));
	}
}

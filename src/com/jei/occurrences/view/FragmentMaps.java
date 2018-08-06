package com.jei.occurrences.view;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jei.occurrences.R;
import com.jei.occurrences.db.dao.OccurrenceDAO;
import com.jei.occurrences.model.Occurrence;

public class FragmentMaps extends Fragment {
	
	private View fragView;
	private GoogleMap map;
	private Context context;
	
	BroadcastReceiver receiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		context = container.getContext();

		if (fragView != null) {
			ViewGroup parent = (ViewGroup) fragView.getParent();
			if (parent != null)
				parent.removeView(fragView);

		} 
		try {
			fragView = inflater.inflate(R.layout.fragment_maps, container,
					false);

		} catch (Exception e) {
			// TODO: handle exception
		}	
		
		receiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				
				followMyPosition();			
				
			}
		};
		
		return fragView;
	}
	
	@Override
	public void onResume() {
		iniciarMonitoramentoGPS();
		super.onResume();
	}
	@Override
	public void onPause() {
		pararMonitoramentoGPS();
		super.onPause();
	}

	private void iniciarMonitoramentoGPS() {
		
		context.registerReceiver(receiver, new IntentFilter("LOCALIZACAO_MODIFICADA"));
		
	}
	

	private void pararMonitoramentoGPS() {
		
		context.unregisterReceiver(receiver);
		
	}

	public void followMyPosition() {
		// Display the connection status
		
		Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
		
		FragmentManager manager = getFragmentManager();

		MapFragment frag = ((MapFragment) manager.findFragmentById(R.id.map));
		
		map =  frag.getMap();

		//LocationClient mLocationClient = ((MainActivity) getActivity())
				//.getmLocationClient();
		Location mCurrentLocation = ((MainActivity) getActivity()).getGps().getLocation();

		LatLng position = new LatLng(mCurrentLocation.getLatitude(),
				mCurrentLocation.getLongitude());

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 16);

		map.animateCamera(update);
		map.clear();
		map.addMarker(new MarkerOptions().position(position).title(
				"Você está aqui!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).showInfoWindow();;
		OccurrenceDAO dao = new OccurrenceDAO(context);
		List<Occurrence> ocs = dao.getAllOccurrences();
		
		for (Occurrence occurrence : ocs) {
			map.addMarker(new MarkerOptions().position(new LatLng(occurrence.getLocation().getLatitude(), occurrence.getLocation().getLongitude()))
					.title(occurrence.getCrimeType()));
		}
		
		map.setOnMapLongClickListener(new OnMapLongClickListener() {
			@Override
			public void onMapLongClick(LatLng _longClick) {

				final LatLng longClick = _longClick;

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Relatar uma ocorrência em " +
				((MainActivity) getActivity()).getGps().getAddress(longClick.latitude, longClick.longitude) +"?");
				builder.setPositiveButton("Sim", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						((MainActivity) getActivity())
								.setLastLongClick(longClick);

						((MainActivity) getActivity()).changeFragment(1);
						//((MainActivity) getActivity()).getGps().getAddress();
						/*FragmentTransaction transaction = getFragmentManager()
								.beginTransaction();
						transaction.replace(R.id.content_frame,
								MainActivity.getFragRegister());
						transaction.commit();*/
					}
				});
				builder.setNegativeButton("Cancelar", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

				builder.create().show();

			}
		});
	}

	

}

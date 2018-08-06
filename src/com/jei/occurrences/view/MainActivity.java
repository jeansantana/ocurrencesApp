package com.jei.occurrences.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.jei.occurrences.R;
import com.jei.occurrences.controller.SingletonDBController;
import com.jei.occurrences.controller.SingletonDataOccurrences;
import com.jei.occurrences.internet.RESTClient;
import com.jei.occurrences.model.Occurrence;
import com.jei.occurrences.model.OccurrenceLocation;
import com.jei.occurrences.services.GPSTracker;
import com.jei.occurrences.services.OccurrenceService;

public class MainActivity extends Activity implements OnItemClickListener {
	// GooglePlayServicesClient.ConnectionCallbacks,
	// GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private Location location;
	private LatLng lastLongClick;
	private Intent intentServiceOccurrenceConextion;

	private ListView mListView;
	private MyAdapterLeftDrawerLayout mAdapterDrawer;

	private static FragmentMaps fragMaps;
	private static RegisterActivity fragRegister;
	//private static EstatisticsFragment fragEsts;
	private static NumsUteisFragment fragNums;
	private static FragmentMyOccurrences fragMyOccur;

	// private LocationClient mLocationClient;
	private static GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SingletonDBController.getInstance(this).open();
		setContentView(R.layout.activity_main);

		// start service

		intentServiceOccurrenceConextion = new Intent(this,
				OccurrenceService.class);
		startService(intentServiceOccurrenceConextion);

		location = null;
		// mLocationClient = new LocationClient(this, this, this);
		// mLocationClient.connect(); // Initialize the Location Cliente

		if (savedInstanceState == null) {
			Log.i("BugMapa", "Qualquer coisa ai");
			gps = new GPSTracker(MainActivity.this);
			fragMaps = new FragmentMaps();
			fragRegister = new RegisterActivity();
			//fragEsts = new EstatisticsFragment();
			fragNums = new NumsUteisFragment();
			fragMyOccur = new FragmentMyOccurrences();
			getFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragMaps).commit();
		}

		// Configuration ListView
		mListView = (ListView) findViewById(R.id.left_drawer_list_view);
		mAdapterDrawer = new MyAdapterLeftDrawerLayout(this);
		mListView.setAdapter(mAdapterDrawer);
		mListView.setOnItemClickListener(this);

		// Configuration of DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {
			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				// getActionBar().setTitle(mDrawerTitle);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// getActionBar().setTitle(mDrawerTitle);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

	}

	@Override
	protected void onStop() {
		// stopService(intentServiceOccurrenceConextion);
		super.onStop();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		changeFragment(position);
		selectItem(position);
	}

	public void selectItem(int position) {
		mListView.setItemChecked(position, true);
		setTitle((String) mAdapterDrawer.getItem(position));

		mDrawerLayout.closeDrawer(mListView);
	}

	public void setTitle(String title) {
		getActionBar().setTitle(title);
	}

	public void changeFragment(int fragtype) {
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		switch (fragtype) {
		case 0:
			transaction.replace(R.id.content_frame, fragMaps);
			break;
		case 1:
			transaction.replace(R.id.content_frame, fragRegister);
			break;
		case 2:
			transaction.replace(R.id.content_frame, fragMyOccur);
			break;
		/*case 4:
			//transaction.replace(R.id.content_frame, fragEsts);
			break;*/
		case 3:
			transaction.replace(R.id.content_frame, fragNums);
			break;
		default:

			break;
		}

		transaction.commit();

	}

	public void showTimePickerDialog(View v) {
		TimePickerFragment newFragment = new TimePickerFragment();
		// TextView time = (TextView) findViewById(R.id.timePicker);
		newFragment.show(getFragmentManager(), "timePicker");
		// time.setText(newFragment.getHour());
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void saveOccurrence(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Salvar?");
		builder.setPositiveButton("Salvar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveOccurrence_();
				
			}
		});
		builder.setNegativeButton("Cancelar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.create().show();
	}

	private void saveOccurrence_() {

		String title = new String(
				((TextView) findViewById(R.id.register_title)).getText()
						.toString());

		/*
		 * Location location = new Location( ((TextView)
		 * findViewById(R.id.register_location)).getText() .toString());
		 */
		OccurrenceLocation location = new OccurrenceLocation(
				((TextView) findViewById(R.id.register_location)).getText()
						.toString(), lastLongClick.latitude,
				lastLongClick.longitude);

		String description = new String(
				((TextView) findViewById(R.id.register_description)).getText()
						.toString());
		String date = new String(
				((TextView) findViewById(R.id.register_dataPicker)).getText()
						.toString());
		String hour = new String(
				((TextView) findViewById(R.id.register_timePicker)).getText()
						.toString());

		String crimeType = new String(
				((Spinner) findViewById(R.id.lstOccurrencesType))
						.getSelectedItem().toString());

		final Occurrence occur = new Occurrence(title, location,
				dateToUSAFormat(date), hour, crimeType, description);

		SingletonDataOccurrences.getInstance(this).report(occur);

		Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
		
		//changeFragment(0);
	}

	/*************************************************************************
	 * Gets and Sets
	 * ***********************************************************************/

	private String dateToUSAFormat(String date) {

		String[] parts = date.split("/");

		String response = parts[2] + "/" + parts[1] + "/" + parts[0];

		return response;
	}

	/**
	 * @return the fragMaps
	 */
	public static FragmentMaps getFragMaps() {
		return fragMaps;
	}

	/**
	 * @param fragMaps
	 *            the fragMaps to set
	 */
	public static void setFragMaps(FragmentMaps fragMaps) {
		MainActivity.fragMaps = fragMaps;
	}

	/**
	 * @return the fragRegister
	 */
	public static RegisterActivity getFragRegister() {
		return fragRegister;
	}

	/**
	 * @param fragRegister
	 *            the fragRegister to set
	 */
	public static void setFragRegister(RegisterActivity fragRegister) {
		MainActivity.fragRegister = fragRegister;
	}

	/**
	 * @return the fragEsts
	 */
	/*public static EstatisticsFragment getFragEsts() {
		return fragEsts;
	}

	public static void setFragEsts(EstatisticsFragment fragEsts) {
		MainActivity.fragEsts = fragEsts;
	}*/

	/**
	 * @return the fragNums
	 */
	public static NumsUteisFragment getFragNums() {
		return fragNums;
	}

	/**
	 * @param fragNums
	 *            the fragNums to set
	 */
	public static void setFragNums(NumsUteisFragment fragNums) {
		MainActivity.fragNums = fragNums;
	}

	/**
	 * @return the fragMyOccur
	 */
	public static FragmentMyOccurrences getFragMyOccur() {
		return fragMyOccur;
	}

	/**
	 * @param fragMyOccur
	 *            the fragMyOccur to set
	 */
	public static void setFragMyOccur(FragmentMyOccurrences fragMyOccur) {
		MainActivity.fragMyOccur = fragMyOccur;
	}

	/**
	 * @return the mLocationClient
	 */
	/*
	 * public LocationClient getmLocationClient() { return mLocationClient; }
	 * 
	 * /**
	 * 
	 * @param mLocationClient the mLocationClient to set
	 */
	/*
	 * public void setmLocationClient(LocationClient mLocationClient) {
	 * this.mLocationClient = mLocationClient; }
	 */

	public LatLng getLastLongClick() {
		if (lastLongClick == null)
			lastLongClick = new LatLng(gps.getLatitude(), gps.getLongitude());
		return lastLongClick;
	}

	public void setLastLongClick(LatLng lastLongClick) {
		this.lastLongClick = lastLongClick;
		// Location location = mLocationClient.getLastLocation();
		location = gps.getLocation();
		location.setLatitude(lastLongClick.latitude);
		location.setLongitude(lastLongClick.longitude);
	}

	public Location getLocation() {
		if (location == null)
			location = gps.getLocation();
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public GPSTracker getGps() {
		return gps;
	}

	@Override
	protected void onDestroy() {
		SingletonDBController.getInstance(this).close();
		super.onDestroy();
	}
}

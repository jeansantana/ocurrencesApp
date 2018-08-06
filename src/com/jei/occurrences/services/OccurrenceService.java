package com.jei.occurrences.services;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.jei.occurrences.controller.SingletonDataOccurrences;
import com.jei.occurrences.internet.RESTClient;
import com.jei.occurrences.model.Occurrence;

public class OccurrenceService extends Service {

	public Context context = this;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		//Toast.makeText(this, "Occurrence Service is Created",
				//Toast.LENGTH_SHORT).show();

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Toast.makeText(this, "Occurrence Service is Started",
				//Toast.LENGTH_SHORT).show();
		try {
			// new Thread(new Runnable() {

			// @Override
			// public void run() {
			long time = 1000 * 1000;
			long timeToStart = 1000 * 10;

			// final String url =
			// "http://192.168.43.186:8080/occurrence/54556d293acf36b636cdf6a3";
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {

				@Override
				public void run() {

					Occurrence[] occurrences = RESTClient.getOccurrences();
					if (occurrences != null) {

						Log.i("TASK DONE!", "TASK DONE!");

						SingletonDataOccurrences.getInstance(context)
								.synchronizeData(Arrays.asList(occurrences));
					}
				}
			};
			timer.scheduleAtFixedRate(task, timeToStart, time);
			// }

			// }).start();
		} catch (Exception e) {
			Log.i("Server error",
					"Connection error, at to connect occurrence api");
			// Log.e("Bug", e.getMessage());
		} finally {
			Log.i("Server error finally",
					"Connection error, at to connect occurrence api");
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		//Toast.makeText(this, "Occurrence Service is Stopped",
				//Toast.LENGTH_SHORT).show();
		stopSelf();
		super.onDestroy();
	}
}
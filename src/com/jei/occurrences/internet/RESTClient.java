package com.jei.occurrences.internet;

import java.net.ConnectException;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jei.occurrences.model.Occurrence;

public class RESTClient {
	//public static final String PREFS_NAME = "OccurrencePrefs";
	//private static Context contex;
	public static final String URL = "http://192.168.43.186:8080/occurrence"; 
	private static RestTemplate request;
	
	public static void initRESTClient() {
		request = new RestTemplate();
		request.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
	}
	
	public static Occurrence[] getOccurrences() {		
		Log.i("GET Occurrence", URL);

		if (request == null)
			initRESTClient();
		
		Occurrence occurrences[] = null;
		try {
			occurrences = request.getForObject(URL, Occurrence[].class);
		} catch (Exception e) {
			Log.i("sdsd", "sds");
		}
		
		//SharedPreferences settings = contex.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
		
		/*SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("autosave", false);
		editor.putInt("tries", 10);
		editor.putString("silentMode", "Yes");
		editor.commit();*/
		
		return occurrences;
	}

	public static Occurrence putOccurrence(Occurrence occ) {
		Log.i("POST Occurrence", URL);
		if (request == null)
			initRESTClient();
		Occurrence o = null;
		
		try {
			o = request.postForObject(URL, occ, Occurrence.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return o;
	}
}

package com.jei.occurrences.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jei.occurrences.db.dao.OccurrenceDAO;
import com.jei.occurrences.internet.RESTClient;
import com.jei.occurrences.model.Occurrence;
import com.jei.occurrences.view.MainActivity;

public class DataOccurrences {

	private List<Occurrence> occurrences;
	private List<Occurrence> myOccurrences;

	private OccurrenceDAO daoOccurrence;

	Context context;

	public DataOccurrences(Context context) {
		occurrences = new ArrayList<Occurrence>();
		myOccurrences = new ArrayList<Occurrence>();
		daoOccurrence = new OccurrenceDAO(context);
		this.context = context;
	}

	public Boolean report(Occurrence occur) {
		try {
			occurrences.add(occur);
			myOccurrences.add(occur);

			daoOccurrence.createOccurrence(occur);
			syncronizeCloudLocalDB();

			return true;
		} catch (Exception e) {
			Log.e("bug bd", e.getMessage());
			return false;
		}
	}

	public Occurrence getOccurrence(int index) {
		return occurrences.get(index);
	}

	public int getOccurrenceNumber() {
		return occurrences.size();
	}

	public int getMyOccurrenceNumber() {
		return myOccurrences.size();
	}

	public List<Occurrence> getMyAllOccurrences() {
		occurrences = daoOccurrence.getAllOccurrences();
		return occurrences;
	}

	public boolean synchronizeData(List<Occurrence> occsFromSerivece) {
		if (occsFromSerivece == null)
			return false;

		syncronizeDBCloud(occsFromSerivece);

		syncronizeCloudLocalDB();

		return true;
	}

	private void syncronizeCloudLocalDB() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				List<Occurrence> occs = getMyAllOccurrences();

				for (Occurrence occurrence : occs) {
					if (occurrence.get_id() == null) {
						Occurrence o = RESTClient.putOccurrence(occurrence);
						daoOccurrence.deleteOccurrence(occurrence);
						daoOccurrence.createOccurrence(o);
						//daoOccurrence.updateOccurrence(o);
					}
				}
			}
		}).start();
	}

	private void syncronizeDBCloud(List<Occurrence> occsFromSerivece) {
		for (Occurrence occurrence : occsFromSerivece) {
			if (daoOccurrence.getOccurrenceByAPIId(occurrence.get_id()) == null)
				report(occurrence);
		}
	}

	private boolean findOccurrenceOnLocalDataBase(Occurrence occurrence) {
		boolean found = false;
		List<Occurrence> occs = getMyAllOccurrences();

		for (int i = 0; i < occs.size(); i++) {
			// Log.i("WWW", occs.get(i).get_id() + " == " + occurrence.get_id()
			// + " ?");
			if (occs.get(i).get_id().compareTo(occurrence.get_id()) == 0)
				found = true;
		}

		return found;
	}
}

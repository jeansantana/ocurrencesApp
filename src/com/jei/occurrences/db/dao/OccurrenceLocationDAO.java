package com.jei.occurrences.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jei.occurrences.controller.SingletonDBController;
import com.jei.occurrences.db.contract.OccurrenceContract;
import com.jei.occurrences.db.contract.OccurrenceLocationContract.OccurrenceLocationEntry;
import com.jei.occurrences.db.helper.OccurrenceDBHelper;
import com.jei.occurrences.model.OccurrenceLocation;

public class OccurrenceLocationDAO {

	/*
	 * private String _id; //private int __v; private int id; private String
	 * street;//rua/av. private String country;//país private String city;//
	 * cidade private String neighborhood;// bairro private String state;//
	 * estado private double latitude; private double longitude;
	 */
	
	private Context context;
	
	private String[] projection = {
			OccurrenceLocationEntry.COLUMN_ID,
			OccurrenceLocationEntry.COLUMN_API_ID,
			OccurrenceLocationEntry.COLUMN_STREET,
			OccurrenceLocationEntry.COLUMN_COUNTRY,
			OccurrenceLocationEntry.COLUMN_CITY,
			OccurrenceLocationEntry.COLUMN_NEIGHBORHOOD,
			OccurrenceLocationEntry.COLUMN_STATE,
			OccurrenceLocationEntry.COLUMN_LATITUDE,
			OccurrenceLocationEntry.COLUMN_LONGITUDE };

	public OccurrenceLocationDAO(Context context) {
		this.context = context;
	}

	public OccurrenceLocation createOccurrenceLocation(
			OccurrenceLocation occcurrenceLocation) {
		
		ContentValues values = new ContentValues();

		values.put(OccurrenceLocationEntry.COLUMN_API_ID,
				occcurrenceLocation.get_id());
		values.put(OccurrenceLocationEntry.COLUMN_STREET,
				occcurrenceLocation.getStreet());
		values.put(OccurrenceLocationEntry.COLUMN_COUNTRY,
				occcurrenceLocation.getCountry());
		values.put(OccurrenceLocationEntry.COLUMN_CITY,
				occcurrenceLocation.getCity());
		values.put(OccurrenceLocationEntry.COLUMN_NEIGHBORHOOD,
				occcurrenceLocation.getNeighborhood());
		values.put(OccurrenceLocationEntry.COLUMN_STATE,
				occcurrenceLocation.getState());
		values.put(OccurrenceLocationEntry.COLUMN_LATITUDE,
				occcurrenceLocation.getLatitude() + "");
		values.put(OccurrenceLocationEntry.COLUMN_LONGITUDE,
				occcurrenceLocation.getLongitude() + "");

		long insertId = SingletonDBController.getInstance(context).insert(
				OccurrenceLocationEntry.TABLE_NAME, values);

		Cursor cursor = SingletonDBController.getInstance(context).query(OccurrenceLocationEntry.TABLE_NAME,
				projection, OccurrenceLocationEntry.COLUMN_ID
						+ " = " + insertId);
		cursor.moveToFirst();
		OccurrenceLocation newOccurrence = cursorToOccurrenceLocation(cursor);
		cursor.close();
		
		return newOccurrence;
	}

	private OccurrenceLocation cursorToOccurrenceLocation(Cursor cursor) {
		/*
		 * int id, String _id, String country, String city, String neighborhood,
		 * String state, String street, double latitude, double longitude
		 */
		/*double lat = 0;
		double longt = 0;
		try {
			lat = ;
			longt = ;
		} catch (Exception e) {
			Log.e("Error at convertting String to Double", e.getMessage());
		}*/
		
		int teste = cursor.getInt(cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_ID));
		OccurrenceLocation occurLocation = new OccurrenceLocation(teste,
				cursor.getString(cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_API_ID)),
				cursor.getString(cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_COUNTRY)),
				cursor.getString(cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_CITY)),
				cursor.getString(cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_NEIGHBORHOOD)),
				cursor.getString(cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_STATE)),
				cursor.getString(cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_STREET)),
				Double.parseDouble( cursor.getString( cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_LATITUDE ) ) ),
				Double.parseDouble( cursor.getString( cursor.getColumnIndex(OccurrenceLocationEntry.COLUMN_LONGITUDE ) ) ));
		return occurLocation;
	}

	public void deleteOccurrence(OccurrenceLocation occur) {
		long id = occur.getId();
		System.out.println("Comment deleted with id: " + id);
		SingletonDBController.getInstance(context).delete(
				OccurrenceLocationEntry.TABLE_NAME,
				OccurrenceLocationEntry.COLUMN_ID + " = " + id);
	}
	
	public OccurrenceLocation getOccurrenceLocationById(int id) {
		//this.db.rawQuery("select * from " + BanksTable.NAME + " where " + BanksTable.COL_NAME + "='" + bankName + "'" , null)
		Cursor cursor = SingletonDBController.getInstance(context).rawQuery("select * from " + OccurrenceLocationEntry.TABLE_NAME + " where " + 
		OccurrenceLocationEntry.COLUMN_ID + " = '" + id + "'");
		
		cursor.moveToFirst();
		
		return cursorToOccurrenceLocation(cursor);
	}
	
	public OccurrenceLocation getOccurrenceLocationByAPIId(String apiId) {
		//this.db.rawQuery("select * from " + BanksTable.NAME + " where " + BanksTable.COL_NAME + "='" + bankName + "'" , null)
		Cursor cursor = SingletonDBController.getInstance(context).rawQuery("select * from " + OccurrenceLocationEntry.TABLE_NAME + " where " + 
		OccurrenceLocationEntry.COLUMN_ID + " = '" + apiId + "'");
		
		cursor.moveToFirst();
		
		return cursorToOccurrenceLocation(cursor);
	}

	public List<OccurrenceLocation> getAllOccurrencesLocations() {
		List<OccurrenceLocation> occurrencesLocation = new ArrayList<OccurrenceLocation>();

		Cursor cursor = SingletonDBController.getInstance(context).query(OccurrenceContract.OccurrenceEntry.TABLE_NAME,
				projection, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			OccurrenceLocation occur = cursorToOccurrenceLocation(cursor);
			occurrencesLocation.add(occur);
			cursor.moveToNext();
		}
		cursor.close();
		return occurrencesLocation;
	}

}

package com.jei.occurrences.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jei.occurrences.controller.SingletonDBController;
import com.jei.occurrences.db.contract.OccurrenceContract;
import com.jei.occurrences.db.contract.OccurrenceContract.OccurrenceEntry;
import com.jei.occurrences.db.contract.OccurrenceLocationContract.OccurrenceLocationEntry;
import com.jei.occurrences.db.helper.OccurrenceDBHelper;
import com.jei.occurrences.model.Occurrence;
import com.jei.occurrences.model.OccurrenceLocation;

public class OccurrenceDAO {

	private OccurrenceLocationDAO occLocDao;
	private Context context;

	private String[] projection = {
			OccurrenceContract.OccurrenceEntry.COLUMN_ENTRY_ID,
			OccurrenceContract.OccurrenceEntry.COLUMN_API_ID,
			OccurrenceContract.OccurrenceEntry.COLUMN_TITLE,
			OccurrenceContract.OccurrenceEntry.COLUMN_LOCATION_ID_FK,
			OccurrenceContract.OccurrenceEntry.COLUMN_DATE,
			OccurrenceContract.OccurrenceEntry.COLUMN_HOUR,
			OccurrenceContract.OccurrenceEntry.COLUMN_CRIME_TYPE,
			OccurrenceContract.OccurrenceEntry.COLUMN_DESCRIPTION };

	public OccurrenceDAO(Context context) {
		this.context = context;
		occLocDao = new OccurrenceLocationDAO(context);

	}

	public Occurrence createOccurrence(Occurrence occurrence) {

		OccurrenceLocation occLoc = occLocDao
				.createOccurrenceLocation(occurrence.getLocation());

		occurrence.setLocation(occLoc);

		ContentValues values = new ContentValues();

		values.put(OccurrenceContract.OccurrenceEntry.COLUMN_API_ID,
				occurrence.get_id());
		values.put(OccurrenceContract.OccurrenceEntry.COLUMN_TITLE,
				occurrence.getTitle());
		values.put(OccurrenceContract.OccurrenceEntry.COLUMN_LOCATION_ID_FK,
				occurrence.getLocation().getId());
		values.put(OccurrenceContract.OccurrenceEntry.COLUMN_DATE,
				occurrence.getDate());
		values.put(OccurrenceContract.OccurrenceEntry.COLUMN_HOUR,
				occurrence.getHour());
		values.put(OccurrenceContract.OccurrenceEntry.COLUMN_CRIME_TYPE,
				occurrence.getCrimeType());
		values.put(OccurrenceContract.OccurrenceEntry.COLUMN_DESCRIPTION,
				occurrence.getDescription());

		long insertId = SingletonDBController.getInstance(context).insert(
				OccurrenceContract.OccurrenceEntry.TABLE_NAME, values);

		Cursor cursor = SingletonDBController.getInstance(context).query(
				OccurrenceContract.OccurrenceEntry.TABLE_NAME,
				projection,
				OccurrenceContract.OccurrenceEntry.COLUMN_ENTRY_ID + " = "
						+ insertId);
		cursor.moveToFirst();
		Occurrence newOccurrence = cursorToOccurrence(cursor);
		cursor.close();
		return newOccurrence;
	}

	private Occurrence cursorToOccurrence(Cursor cursor) {
		/*
		 * Integer id, String _id, String title, OccurrenceLocation location, String date,
		 * String hour, String crimeType, String description
		 */

		Occurrence occur = new Occurrence(cursor.getInt(cursor.getColumnIndex(OccurrenceEntry.COLUMN_ENTRY_ID)),
				cursor.getString(cursor.getColumnIndex(OccurrenceEntry.COLUMN_API_ID)),
				cursor.getString(cursor.getColumnIndex(OccurrenceEntry.COLUMN_TITLE)),
				occLocDao.getOccurrenceLocationById(cursor.getInt(cursor.getColumnIndex(OccurrenceEntry.COLUMN_LOCATION_ID_FK))),
				cursor.getString(cursor.getColumnIndex(OccurrenceEntry.COLUMN_DATE)), 
				cursor.getString(cursor.getColumnIndex(OccurrenceEntry.COLUMN_HOUR)),
				cursor.getString(cursor.getColumnIndex(OccurrenceEntry.COLUMN_CRIME_TYPE)),
				cursor.getString(cursor.getColumnIndex(OccurrenceEntry.COLUMN_DESCRIPTION)));
		return occur;
	}

	public void deleteOccurrence(Occurrence occur) {
		long id = occur.getId();
		System.out.println("Comment deleted with id: " + id);
		SingletonDBController.getInstance(context)
				.delete(OccurrenceContract.OccurrenceEntry.TABLE_NAME,
						OccurrenceContract.OccurrenceEntry.COLUMN_ENTRY_ID
								+ " = " + id);
	}
	
	public Occurrence updateOccurrence(Occurrence occurrence) {
		/*UPDATE table_name
		SET column1 = value1, column2 = value2...., columnN = valueN
		WHERE [condition];*/
		Cursor cursor = SingletonDBController.getInstance(context).rawQuery("UPDATE " + OccurrenceEntry.TABLE_NAME + " SET " +
		OccurrenceEntry.COLUMN_API_ID + " = " + occurrence.get_id() + 
		" WHERE " + OccurrenceEntry.COLUMN_ENTRY_ID + " = " + occurrence.getId());
		
		if (cursor.moveToFirst()) {
			return cursorToOccurrence(cursor);
		}
		
		return null;
	}

	public List<Occurrence> getAllOccurrences() {
		List<Occurrence> occurrences = new ArrayList<Occurrence>();

		Cursor cursor = SingletonDBController.getInstance(context).query(OccurrenceContract.OccurrenceEntry.TABLE_NAME, projection, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Occurrence occur = cursorToOccurrence(cursor);
			occurrences.add(occur);
			cursor.moveToNext();
		}
		cursor.close();
		return occurrences;
	}
	
	public Occurrence getOccurrenceByAPIId(String apiId) {
		//this.db.rawQuery("select * from " + BanksTable.NAME + " where " + BanksTable.COL_NAME + "='" + bankName + "'" , null)
		Cursor cursor = SingletonDBController.getInstance(context).rawQuery("select * from " + OccurrenceEntry.TABLE_NAME + " where " + 
		OccurrenceLocationEntry.COLUMN_API_ID + " = '" + apiId + "'");
		
		if (cursor.moveToFirst()) {
			return cursorToOccurrence(cursor);
		}
		
		return null;
	}
	
}

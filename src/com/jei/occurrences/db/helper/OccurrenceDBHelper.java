package com.jei.occurrences.db.helper;

import com.jei.occurrences.db.contract.OccurrenceContract.OccurrenceEntry;
import com.jei.occurrences.db.contract.OccurrenceLocationContract.OccurrenceLocationEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OccurrenceDBHelper  extends SQLiteOpenHelper {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	private static final String SQL_CREATE_ENTRIES_OCCURRENCE =
	    "CREATE TABLE " + OccurrenceEntry.TABLE_NAME + " (" +
	    OccurrenceEntry.COLUMN_ENTRY_ID + " INTEGER PRIMARY KEY," +
	    OccurrenceEntry.COLUMN_API_ID + TEXT_TYPE + COMMA_SEP +
	    OccurrenceEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
	    OccurrenceEntry.COLUMN_LOCATION_ID_FK + " INTEGER," +
	    OccurrenceEntry.COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
	    OccurrenceEntry.COLUMN_HOUR + TEXT_TYPE + COMMA_SEP +
	    OccurrenceEntry.COLUMN_CRIME_TYPE + TEXT_TYPE + COMMA_SEP +
	    OccurrenceEntry.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
	    "FOREIGN KEY(" + OccurrenceEntry.COLUMN_LOCATION_ID_FK + ") REFERENCES " + OccurrenceLocationEntry.TABLE_NAME + "(" + OccurrenceLocationEntry.COLUMN_ID + ")" +
	    " );";
    private static final String SQL_CREATE_ENTRIES_OCCURRENCE_LOC =    
	    "CREATE TABLE " + OccurrenceLocationEntry .TABLE_NAME + "(" +
	    OccurrenceLocationEntry.COLUMN_ID + " INTEGER PRIMARY KEY," + 
	    OccurrenceLocationEntry.COLUMN_API_ID + TEXT_TYPE + COMMA_SEP +
	    OccurrenceLocationEntry.COLUMN_STREET + TEXT_TYPE + COMMA_SEP +
	    OccurrenceLocationEntry.COLUMN_NEIGHBORHOOD + TEXT_TYPE + COMMA_SEP +
	    OccurrenceLocationEntry.COLUMN_CITY + TEXT_TYPE + COMMA_SEP + 
	    OccurrenceLocationEntry.COLUMN_STATE + TEXT_TYPE + COMMA_SEP +
	    OccurrenceLocationEntry.COLUMN_COUNTRY + TEXT_TYPE + COMMA_SEP +
	    OccurrenceLocationEntry.COLUMN_LATITUDE + TEXT_TYPE + COMMA_SEP +
	    OccurrenceLocationEntry.COLUMN_LONGITUDE + TEXT_TYPE +
	    ");";
		

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + OccurrenceEntry.TABLE_NAME + "; " +
		"DROP TABLE IF EXISTS " + OccurrenceLocationEntry.TABLE_NAME;
	
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "Occurrences.db";

    public OccurrenceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(SQL_CREATE_ENTRIES_OCCURRENCE_LOC);
        db.execSQL(SQL_CREATE_ENTRIES_OCCURRENCE);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
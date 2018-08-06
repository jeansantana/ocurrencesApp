package com.jei.occurrences.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jei.occurrences.db.contract.OccurrenceContract;
import com.jei.occurrences.db.helper.OccurrenceDBHelper;

public class DBController {

	private SQLiteDatabase db;
	private OccurrenceDBHelper dbHelper;
	private Context context;

	public DBController(Context context) {
		this.context = context;
		dbHelper = new OccurrenceDBHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Context getContext() {
		return context;
	}

	public long insert(String tableName, ContentValues values) {
		return db.insert(tableName, null, values);
	}

	public Cursor query(String tableName, String[] projection, String query) {
		return db.query(tableName, projection, query, null, null, null, null);
	}

	public void delete(String tableName, String query) {
		db.delete(tableName, query, null);
	}
	
	public Cursor rawQuery(String query) {
		return db.rawQuery(query, null);
	}

}

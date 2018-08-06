package com.jei.occurrences.db.contract;

import android.provider.BaseColumns;

public final class OccurrenceLocationContract {
	
	public OccurrenceLocationContract() {}
	
	public static abstract class OccurrenceLocationEntry implements BaseColumns {
		public static final String TABLE_NAME = "OccurrenceLocation";
		public static final String COLUMN_ID = "idLocation";
		public static final String COLUMN_API_ID = "apiID";
		public static final String COLUMN_STREET = "street";
		public static final String COLUMN_COUNTRY = "country";
		public static final String COLUMN_CITY = "city";
		public static final String COLUMN_NEIGHBORHOOD = "neighborhood";
		public static final String COLUMN_STATE = "state";
		public static final String COLUMN_LATITUDE = "latitude";
		public static final String COLUMN_LONGITUDE = "longitude";		
	}
}

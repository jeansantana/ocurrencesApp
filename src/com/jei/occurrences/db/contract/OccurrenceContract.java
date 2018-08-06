package com.jei.occurrences.db.contract;

import android.provider.BaseColumns;

public final class OccurrenceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public OccurrenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class OccurrenceEntry implements BaseColumns {
        public static final String TABLE_NAME = "Occurrence";
        public static final String COLUMN_ENTRY_ID = "entryid";
        public static final String COLUMN_API_ID = "apiID";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LOCATION_ID_FK = "locationfk";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_CRIME_TYPE = "crimeType";
        public static final String COLUMN_DESCRIPTION = "description";
    }
}
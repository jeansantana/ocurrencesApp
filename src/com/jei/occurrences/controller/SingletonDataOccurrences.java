package com.jei.occurrences.controller;

import android.content.Context;

public class SingletonDataOccurrences {
	private static DataOccurrences occur;

	private SingletonDataOccurrences() {

	}

	public static DataOccurrences getInstance(Context context) {
		if (occur == null) {
			occur = new DataOccurrences(context);
		}
		return occur;
	}
}

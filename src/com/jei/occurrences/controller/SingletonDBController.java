package com.jei.occurrences.controller;

import android.content.Context;

public class SingletonDBController {
	
	public static DBController bd;
	
	private SingletonDBController() {
	}
	
	public static DBController getInstance(Context context) {
		if (bd == null)
			bd = new DBController(context);
		return bd;
	}
}

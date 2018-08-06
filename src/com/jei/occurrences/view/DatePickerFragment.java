package com.jei.occurrences.view;

import java.util.Calendar;

import com.jei.occurrences.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private Integer day;
	private Integer month;
	private Integer year;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		this.day = day;
		this.month = month;
		this.year = year;
		
		TextView time = (TextView) getActivity().findViewById(R.id.register_dataPicker);
	    time.setText(getDateSelected());
	}
	
	private String getDateSelected() {
		String d;
		String m;
		
		if(day < 10) d = "0" + day.toString();
		else d = day.toString();;
		
		if(month < 10) m = "0" + month.toString();
		else m = month.toString();
		
		return d+ "/" + m + "/" + year;
	}
}
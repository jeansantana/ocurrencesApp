package com.jei.occurrences.view;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.internal.mi;
import com.jei.occurrences.R;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private Integer hourOfDay;
	private Integer minute;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		this.hourOfDay = hourOfDay;
		this.minute = minute;
		
		TextView time = (TextView) getActivity().findViewById(R.id.register_timePicker);
	    time.setText(getHour());	
	}
	
	public int getHourOfDay() {
		return hourOfDay;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public String getHour() {
		String hour;
		String min;
		
		if(hourOfDay < 10) hour = "0" + hourOfDay.toString();
		else hour = hourOfDay.toString();;
		
		if(minute < 10) min = "0" + minute.toString();
		else min = minute.toString();
		
		return hour+ ":" + min;
	}
}

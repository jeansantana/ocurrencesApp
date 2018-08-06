package com.jei.occurrences.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jei.occurrences.R;
import com.jei.occurrences.controller.SingletonDataOccurrences;
import com.jei.occurrences.model.Occurrence;

public class MyAdapterRegisters extends BaseAdapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7479741601536112926L;
	private Context context;
	private List<Occurrence> occurrences;
	
	public MyAdapterRegisters(Context context) {
		this.context = context;
		occurrences = new ArrayList<Occurrence>();
		occurrences = SingletonDataOccurrences.getInstance(context).getMyAllOccurrences();		
	}
	
	@Override
	public int getCount() {
		return occurrences.size();
	}

	@Override
	public Object getItem(int position) {
		return occurrences.get(position);
	}

	@Override
	public long getItemId(int position) {
		return occurrences.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = null;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_row_my_registers, parent, false);
		} else {
			row = convertView;
		}

		TextView title = (TextView) row.findViewById(R.id.my_register_title);
		TextView date = (TextView) row.findViewById(R.id.my_register_date);
		TextView description = (TextView) row.findViewById(R.id.my_register_description);
		
		title.setText(occurrences.get(position).getId() + " - " +  occurrences.get(position).getTitle());
		date.setText(occurrences.get(position).toString());
		description.setText("");
		
		return row;
	}
	
	
}

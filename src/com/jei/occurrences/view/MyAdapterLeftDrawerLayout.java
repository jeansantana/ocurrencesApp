package com.jei.occurrences.view;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jei.occurrences.R;

public class MyAdapterLeftDrawerLayout extends BaseAdapter implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4083978711672552995L;
	private Context context;
	private String[] content;
	// int[] images = { 0, 0, 0, 0 }; // R drawerable icons
	ArrayList<Integer> images;

	public MyAdapterLeftDrawerLayout(Context context) {
		this.context = context;
		content = context.getResources().getStringArray(
				R.array.options_left_drawer);
		images = new ArrayList<Integer>();

		images.add(R.drawable.ic_ic_jei_home);
		images.add(R.drawable.ic_ic_jei_register);
		images.add(R.drawable.ic_ic_jei_myregisters);
		images.add(R.drawable.ic_ic_jei_statistics);
		images.add(R.drawable.ic_ic_jei_numbers);
	}

	@Override
	public int getCount() {

		return content.length;
	}

	@Override
	public Object getItem(int position) {
		return content[position];
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = null;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_row, parent, false);
		} else {
			row = convertView;
		}

		TextView textView = (TextView) row.findViewById(R.id.drawer_left_text);
		ImageView imageView = (ImageView) row
				.findViewById(R.id.drawer_left_image);

		textView.setText(content[position]);
		imageView.setImageResource(getIcon(position));

		return row;
	}

	public Integer getIcon(int position) {
		try {
			return images.get(position);
		} catch (Exception e) {
			return R.drawable.ic_drawer;
		}
	}
}

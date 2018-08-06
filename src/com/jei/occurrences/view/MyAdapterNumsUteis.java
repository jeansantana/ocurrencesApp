package com.jei.occurrences.view;

import java.io.Serializable;

import com.jei.occurrences.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapterNumsUteis extends BaseAdapter implements Serializable {

	private static final long serialVersionUID = 550876058730922752L;
	private Context context;
	private String[] content;

	public MyAdapterNumsUteis(Context context) {
		this.context = context;
		content = context.getResources().getStringArray(R.array.lstNumsDescription);
		
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
		imageView.setImageResource(R.drawable.ic_ic_jei_telephone);

		return row;
	}

}

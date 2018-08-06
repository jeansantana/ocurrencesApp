package com.jei.occurrences.view;

import java.io.Serializable;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jei.occurrences.R;

public class NumsUteisFragment extends Fragment implements Serializable,
		OnItemClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3521722684733862617L;
	private ListView lstNums;
	private MyAdapterNumsUteis myAdapterNumsUteis;
	private String[] lstNumbers;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.num_uteis_fragament,
				container, false);
		lstNums = (ListView) fragView.findViewById(R.id.lstNums);
		myAdapterNumsUteis = new MyAdapterNumsUteis(container.getContext());
		lstNums.setAdapter(myAdapterNumsUteis);
		lstNums.setOnItemClickListener(this);

		lstNumbers = getResources().getStringArray(R.array.lstNums);
		/*
		 * lstNums.setAdapter(new ArrayAdapter<String>(getActivity(),
		 * android.R.layout.simple_list_item_1, getResources()
		 * .getStringArray(R.array.lstNums)));
		 */

		return fragView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		/*CharSequence text = lstNumbers[position];
		Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
		*/
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+lstNumbers[position]));
		
		startActivity(intent);
	}
}
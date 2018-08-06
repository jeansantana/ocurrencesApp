package com.jei.occurrences.view;

import java.io.Serializable;

import com.jei.occurrences.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentMyOccurrences extends Fragment implements Serializable,
		OnItemClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2776257905833460593L;
	private ListView listView;
	private MyAdapterRegisters myRegisters;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View fragView = inflater.inflate(R.layout.my_occurrences_fragment,
				container, false);

		listView = (ListView) fragView.findViewById(R.id.my_occurrences_list);

		myRegisters = new MyAdapterRegisters(container.getContext());
		listView.setAdapter(myRegisters);
		listView.setOnItemClickListener(this);

		return fragView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}

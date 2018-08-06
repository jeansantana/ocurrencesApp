package com.jei.occurrences.view;

import java.io.Serializable;

import com.jei.occurrences.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EstatisticsFragment extends Fragment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816799416943884773L;
	private ListView lstNums;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.estatistics_fragment,
				container, false);
		
		lstNums = (ListView) fragView.findViewById(R.id.lstEstatistics);
		lstNums.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.lstEstatistics)));
		return fragView;
	}
}
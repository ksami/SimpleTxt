package com.ksami.simpletxt;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ListViewItemLongClickListener implements OnItemLongClickListener {

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		MainActivity main = (MainActivity) view.getContext();
		ListView listView = (ListView) view;
		listView.setItemChecked(position, !(main.directoryList.isPositionChecked(position)));
		return true;
	}

}

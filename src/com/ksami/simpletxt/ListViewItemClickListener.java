package com.ksami.simpletxt;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ListViewItemClickListener implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Get string of item selected
		TextView textBox = (TextView) view.findViewById(R.id.textViewItem);
		String fileName = textBox.getText().toString();
		
		// Start the next activity
		MainActivity main = (MainActivity) view.getContext();
		main.startEditTextActivity(fileName);
	}

}

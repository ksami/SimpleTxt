package com.ksami.simpletxt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements NewFileNameDialogFragment.NewFileNameDialogListener {
	
	public static final String FILENAME = "com.ksami.simpletxt.FILENAME";
	public List<String> fileList = new ArrayList<String>();
	public SelectionAdapter directoryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listDirectory(getFilesDir());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_new_file:
			DialogFragment newFragment = new NewFileNameDialogFragment();
		    newFragment.show(getFragmentManager(), "newfile");
		}
		return super.onOptionsItemSelected(item);
	}
	
	// List all the files in the directory
	public void listDirectory(File rootDir) {
		updateList(rootDir);
		
		ListView listViewItems = (ListView) this.findViewById(R.id.list_view);
		listViewItems.setOnItemClickListener(new ListViewItemClickListener());
		listViewItems.setOnItemLongClickListener(new ListViewItemLongClickListener());
		listViewItems.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listViewItems.setMultiChoiceModeListener(new ListViewMultiChoiceModeListener(directoryList));
	}
	
	// Update the list of files
	public void updateList(File rootDir) {
		ListView listViewItems = (ListView) this.findViewById(R.id.list_view);
		File[] files = rootDir.listFiles();
		fileList.clear();
		for (File file : files){
			// Add only the file name without the .txt extension
			String fileName = file.getName().replace(".txt", "");
			fileList.add(fileName);
		}
		  
		directoryList = new SelectionAdapter(this, R.layout.list_item, R.id.textViewItem, fileList);
		listViewItems.setAdapter(directoryList);
	}
	
	/*
	public void list_item1_click(View view) {
		// Start next activity for editing indicated text file
		Intent intent = new Intent(this, EditTextActivity.class);
		Button listItem = (Button) findViewById(R.id.list_item1);
		String fileName = listItem.getText().toString();
		
    	//Attach data to pass to next activity to intent object
    	intent.putExtra(FILENAME, fileName);
    	
    	//Start activity specified by intent
    	startActivity(intent);
	}
	*/
	
	public void startEditTextActivity(String fileName) {
		// Start next activity for editing indicated text file
		Intent intent = new Intent(this, EditTextActivity.class);
		
    	//Attach data to pass to next activity to intent object
    	intent.putExtra(FILENAME, fileName);
    	
    	//Start activity specified by intent
    	startActivity(intent);
	}
	

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		//Create new file
		Dialog dialogView = dialog.getDialog();
		EditText textBox = (EditText) dialogView.findViewById(R.id.user_file_name);
		String newFileName = textBox.getText().toString();
		File newFile = new File(getFilesDir(), newFileName + ".txt");
		if(!newFile.exists()) {
			try {
				newFile.createNewFile();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		//Start editing the file
		startEditTextActivity(newFileName);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
	}

}

package com.ksami.simpletxt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.ksami.simpletxt.MainActivity;
import com.ksami.simpletxt.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class EditTextActivity extends Activity {
	
	private static String filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_text);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Get fileName passed from previous activity
		Intent intent=getIntent();
		String fileName=intent.getStringExtra(MainActivity.FILENAME);
		filePath=fileName+".txt";
		
		readFromFile();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_text, menu);
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
			
			writeToFile();
			
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Read from file and display content
	private void readFromFile() {
		try {
			BufferedReader readStream = new BufferedReader(new FileReader(getFilesDir()+File.separator+filePath));
			
			EditText textContent=(EditText) findViewById(R.id.edit_text);
			int charRead;
			String contentToDisplay="";
			while((charRead = readStream.read()) != -1) {
				contentToDisplay+=(char)charRead;
			}
			textContent.append(contentToDisplay);
			readStream.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// Save changes on exit or up level or every x secs
	private void writeToFile() {
		try {
			BufferedWriter writeStream = new BufferedWriter(new FileWriter(getFilesDir()+File.separator+filePath));
			
			EditText textContent=(EditText) findViewById(R.id.edit_text);
			String textToWrite = textContent.getText().toString();
			for(int i=0; i<textToWrite.length(); i++)
			{
				writeStream.write(textToWrite.charAt(i));
			}
			
			writeStream.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

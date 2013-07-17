package de.jensdriller.thenewboston;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// if app is update internal data AND shared preferences are DELETED!!!!
// external data NOT!!!!
public class ExternalData extends Activity implements OnItemSelectedListener, OnClickListener {

	private TextView textViewCanRead, textViewCanWrite;
	private String state;
	private boolean canRead, canWrite;
	Spinner spinner;
	String paths[] = { Environment.DIRECTORY_MUSIC, Environment.DIRECTORY_PICTURES, Environment.DIRECTORY_DOWNLOADS };
	File path = null, file = null;
	EditText editTextSaveAs;
	Button buttonConfirm, buttonSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.externaldata);
		textViewCanRead = (TextView) findViewById(R.id.textViewCanRead);
		textViewCanWrite = (TextView) findViewById(R.id.textViewCanWrite);

		// create Spinner with array from above!
		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paths);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

		state = Environment.getExternalStorageState();

		// MEDIA_MOUNTED = read and write
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			textViewCanRead.setText("true");
			textViewCanWrite.setText("true");
			canRead = canWrite = true;
			// MEDIA_MOUNTED_READ_ONLY = read only no write
		} else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			textViewCanRead.setText("true");
			textViewCanWrite.setText("false");
			canRead = true;
			canWrite = false;
		} else {
			textViewCanRead.setText("false");
			textViewCanWrite.setText("false");
			canRead = canWrite = false;
		}

		editTextSaveAs = (EditText) findViewById(R.id.editTextSaveAs);
		buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonConfirm.setOnClickListener(this);
		buttonSave.setOnClickListener(this);

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int selectedIndex, long arg3) {

		path = Environment.getExternalStoragePublicDirectory(paths[selectedIndex]);

	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonConfirm:
				buttonSave.setVisibility(View.VISIBLE);
				break;

			case R.id.buttonSave:
				String stringSaveAs = editTextSaveAs.getText().toString();
				file = new File(path, stringSaveAs);

				// path = Environment.getExternalStoragePublicDirectory("custom");
				// to create and save to a custom named directory﻿ :)

				if (canWrite == true && canRead == true) {

					path.mkdirs();

					try {

						InputStream is = getResources().openRawResource(R.drawable.greenball);
						OutputStream os = new FileOutputStream(file);
						byte[] data = new byte[is.available()];
						is.read(data);
						os.write(data);
						is.close();
						os.close();
						Toast.makeText(this, "File has been saved", Toast.LENGTH_SHORT).show();

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				break;

		}

	}

}

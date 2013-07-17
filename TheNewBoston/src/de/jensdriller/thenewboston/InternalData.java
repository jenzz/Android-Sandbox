package de.jensdriller.thenewboston;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InternalData extends Activity implements OnClickListener {

	EditText editTextSharedData;
	TextView textViewDataResults;
	FileOutputStream fos;
	FileInputStream fis;
	String FILENAME = "InternalString";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);

		init();

	}

	private void init() {

		Button buttonSaveData = (Button) findViewById(R.id.buttonSaveData);
		Button buttonLoadData = (Button) findViewById(R.id.buttonLoadData);
		editTextSharedData = (EditText) findViewById(R.id.editTextSharedData);
		textViewDataResults = (TextView) findViewById(R.id.textViewDataResults);
		buttonSaveData.setOnClickListener(this);
		buttonLoadData.setOnClickListener(this);

	}

	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonSaveData:
				String data = editTextSharedData.getText().toString();
				try {
					fos = openFileOutput(FILENAME, MODE_PRIVATE);
					fos.write(data.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Toast.makeText(this, "String saved!", Toast.LENGTH_SHORT).show();
				break;

			case R.id.buttonLoadData:
				new AsyncLoadStuff().execute(FILENAME);
				break;

		}

	}

	private class AsyncLoadStuff extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {

			for (int i = 0; i < 20; i++) {
				publishProgress(5);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			String dataRead = null;
			try {
				fis = openFileInput(FILENAME);
				byte[] dataArray = new byte[fis.available()];
				while (fis.read(dataArray) != -1) {
					dataRead = new String(dataArray);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					return dataRead;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(InternalData.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMax(100);
			progressDialog.show();
			super.onPreExecute();

		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			progressDialog.incrementProgressBy(values[0]);
			super.onProgressUpdate(values);

		}

		@Override
		protected void onPostExecute(String result) {

			Log.v("result", result);
			progressDialog.dismiss();
			textViewDataResults.setText(result);
			super.onPostExecute(result);

		}

	}

}

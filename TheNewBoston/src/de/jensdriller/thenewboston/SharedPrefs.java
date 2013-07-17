package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SharedPrefs extends Activity implements OnClickListener {

	EditText editTextSharedData;
	TextView textViewDataResults;
	public final String filename = "MySharedString";
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);

		init();
		sharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);

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
				String stringData = editTextSharedData.getText().toString();
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("sharedString", stringData);
				editor.commit();
				Toast.makeText(this, "String saved!", Toast.LENGTH_SHORT).show();
				break;

			case R.id.buttonLoadData:
				sharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
				String stringDataReturned = sharedPreferences.getString("sharedString", "Could not load data!");
				textViewDataResults.setText(stringDataReturned);
				break;

		}

	}

}

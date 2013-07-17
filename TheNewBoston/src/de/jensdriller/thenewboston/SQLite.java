package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SQLite extends Activity implements OnClickListener {

	Button buttonSQLUpdate, buttonSQLView, buttonGetInfo, buttonEditEntry, buttonDeleteEntry;
	EditText editTextSQLName, editTextRowID;
	Spinner spinnerHotness;
	String spinnerValues[] = new String[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlite);

		buttonSQLUpdate = (Button) findViewById(R.id.buttonSQLUpdate);
		buttonSQLView = (Button) findViewById(R.id.buttonSQLView);
		buttonSQLUpdate.setOnClickListener(this);
		buttonSQLView.setOnClickListener(this);
		editTextSQLName = (EditText) findViewById(R.id.editTextSQLName);
		spinnerHotness = (Spinner) findViewById(R.id.spinnerHotness);

		for (int i = 0; i < spinnerValues.length; i++) {
			spinnerValues[i] = "" + (i + 1);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerValues);
		spinnerHotness.setAdapter(adapter);

		buttonGetInfo = (Button) findViewById(R.id.buttonGetInfo);
		buttonEditEntry = (Button) findViewById(R.id.buttonEditEntry);
		buttonDeleteEntry = (Button) findViewById(R.id.buttonDeleteEntry);
		editTextRowID = (EditText) findViewById(R.id.editTextRowID);
		buttonGetInfo.setOnClickListener(this);
		buttonEditEntry.setOnClickListener(this);
		buttonDeleteEntry.setOnClickListener(this);

	}

	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonSQLUpdate:

				boolean ok = true;
				try {

					String name = editTextSQLName.getText().toString();
					String hotness = spinnerHotness.getSelectedItem().toString();

					DBAdapter db = new DBAdapter(this);
					db.open();
					db.insertEntry(name, hotness);
					db.close();

				} catch (Exception e) {
					ok = false;
					Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
				} finally {
					if (ok) {
						Toast.makeText(this, "SQL updated!", Toast.LENGTH_SHORT).show();
					}
				}

				break;

			case R.id.buttonSQLView:

				Intent intent = new Intent("de.jensdriller.thenewboston.SQLVIEW");
				startActivity(intent);

				break;

			case R.id.buttonGetInfo:

				try {
					String id = editTextRowID.getText().toString();
					long l = Long.parseLong(id);
					DBAdapter db = new DBAdapter(this);
					db.open();
					Cursor c = db.getBabeForId(l);

					if (c != null) {
						c.moveToFirst();
						editTextSQLName.setText(c.getString(1));
						spinnerHotness.setSelection((Integer.parseInt(c.getString(2)) - 1));
					}
					db.close();
				} catch (Exception e) {
					Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.buttonEditEntry:

				boolean ok2 = true;
				try {
					String id2 = editTextRowID.getText().toString();
					long l2 = Long.parseLong(id2);
					String name = editTextSQLName.getText().toString();
					String hotness = spinnerHotness.getSelectedItem().toString();

					DBAdapter db2 = new DBAdapter(this);
					db2.open();
					db2.updateEntry(l2, name, hotness);
					db2.close();
				} catch (Exception e) {
					ok2 = false;
					Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
				} finally {
					if (ok2) {
						Toast.makeText(this, "Babe updated!", Toast.LENGTH_SHORT).show();
					}
				}

				break;

			case R.id.buttonDeleteEntry:

				boolean ok3 = true;
				try {
					String id3 = editTextRowID.getText().toString();
					long l3 = Long.parseLong(id3);

					DBAdapter db3 = new DBAdapter(this);
					db3.open();
					db3.deleteEntry(l3);
					db3.close();
				} catch (Exception e) {
					ok3 = false;
					Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
				} finally {
					if (ok3) {
						Toast.makeText(this, "Babe deleted!", Toast.LENGTH_SHORT).show();
					}
				}

				break;

		}

	}

}

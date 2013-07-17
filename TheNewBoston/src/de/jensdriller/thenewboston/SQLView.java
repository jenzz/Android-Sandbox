package de.jensdriller.thenewboston;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SQLView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);

		DBAdapter db = new DBAdapter(this);
		db.open();
		Cursor data = db.getAllBabes();

		for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
			displayTitle(data);
		}

		db.close();

	}

	public void displayTitle(Cursor c) {

		Toast.makeText(this, "ID: " + c.getInt(0) + "\n" + "Name: " + c.getString(1) + "\n" + "Hotness: " + c.getString(2), Toast.LENGTH_LONG).show();

	}

}

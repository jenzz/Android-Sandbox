package de.jensdriller.thenewboston;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	String classes[] = { "Counter", "TextPlay", "Email", "Camera", "DataSend", "DataGet", "GFX", "GFXSurface", "Sound", "Slider", "Tabs", "SimpleBrowser", "Flipper", "SharedPrefs", "InternalData",
			"ExternalData", "SQLite", "Accelerate", "HTTP", "WeatherXML", "Voice", "TextVoice", "NotificationBar", "SeekBarVolume" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// make activities full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classes));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);

		String selectedClass = classes[position];
		try {

			Class ourClass = Class.forName("de.jensdriller.thenewboston." + selectedClass);
			Intent ourIntent = new Intent(this, ourClass);
			startActivity(ourIntent);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	// menu via options button!
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {

		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

			case R.id.itemAboutUs:
				startActivity(new Intent(this, AboutUs.class));
				break;

			case R.id.itemPreferences:
				startActivity(new Intent(this, Preferences.class));
				break;

			case R.id.itemExit:
				finish();
				break;

		}

		return super.onOptionsItemSelected(item);
		// return false;

	}

}

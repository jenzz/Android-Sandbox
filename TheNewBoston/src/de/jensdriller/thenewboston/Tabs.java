package de.jensdriller.thenewboston;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Tabs extends Activity implements OnClickListener {

	TabHost tabHost;
	TextView textViewResult;
	long start = 0, stop = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);

		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();

		TabSpec tabSpec = tabHost.newTabSpec("tabSpec1");
		tabSpec.setContent(R.id.tab1);
		tabSpec.setIndicator("StopWatch");
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tabSpec2");
		tabSpec.setContent(R.id.tab2);
		tabSpec.setIndicator("Tab 2");
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tabSpec3");
		tabSpec.setContent(R.id.tab3);
		tabSpec.setIndicator("Tab 3");
		tabHost.addTab(tabSpec);

		Button buttonStartWatch = (Button) findViewById(R.id.buttonStartWatch);
		Button buttonStopWatch = (Button) findViewById(R.id.buttonStopWatch);
		Button buttonAddTab = (Button) findViewById(R.id.buttonAddTab);

		buttonStartWatch.setOnClickListener(this);
		buttonStopWatch.setOnClickListener(this);
		buttonAddTab.setOnClickListener(this);

		textViewResult = (TextView) findViewById(R.id.textViewResults);
	}

	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonStartWatch:

				start = System.currentTimeMillis();
				break;

			case R.id.buttonStopWatch:

				if (start != 0) {
					stop = System.currentTimeMillis();
					int millis = (int) (stop - start);
					int seconds = millis / 1000;
					int minutes = seconds / 60;
					millis %= 100;
					seconds %= 60;
					this.textViewResult.setText(String.format("%d:%02d:%02d", minutes, seconds, millis));
					start = stop = 0;
				}
				break;

			case R.id.buttonAddTab:

				TabSpec newTabSpec = tabHost.newTabSpec("tabSpecNew");
				newTabSpec.setContent(new TabHost.TabContentFactory() {

					public View createTabContent(String tag) {

						TextView textView = new TextView(Tabs.this);
						textView.setText("New tab successfully created!");
						return textView;
					}
				});
				newTabSpec.setIndicator("New");
				tabHost.addTab(newTabSpec);

				break;

		}

	}

}

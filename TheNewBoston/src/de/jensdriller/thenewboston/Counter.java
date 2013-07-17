package de.jensdriller.thenewboston;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Counter extends Activity {

	static int counter = 0;
	TextView textView;
	Button buttonAdd, buttonSub;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter);

		buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonSub = (Button) findViewById(R.id.buttonSub);
		textView = (TextView) findViewById(R.id.textView);
		textView.setText("Your total is " + counter);

		buttonAdd.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				counter++;
				textView.setText("Your total is " + counter);
			}

		});
		buttonSub.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				counter--;
				textView.setText("Your total is " + counter);
			}

		});

	}
}
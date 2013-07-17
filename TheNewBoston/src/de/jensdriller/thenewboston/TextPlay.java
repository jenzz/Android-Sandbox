package de.jensdriller.thenewboston;

import java.util.Random;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TextPlay extends Activity {

	Button buttonCommand;
	ToggleButton toggleButtonPassword;
	EditText editTextInput;
	TextView textViewResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.textplay);

		init();
		toggleButtonPassword.setOnClickListener(new ToggleButtonPasswordListener());
		buttonCommand.setOnClickListener(new ButtonCommandListener());

	}

	private void init() {

		buttonCommand = (Button) findViewById(R.id.buttonCommand);
		toggleButtonPassword = (ToggleButton) findViewById(R.id.toggleButtonPassword);
		editTextInput = (EditText) findViewById(R.id.editTextInput);
		textViewResult = (TextView) findViewById(R.id.textViewResult);

	}

	private class ToggleButtonPasswordListener implements OnClickListener {

		public void onClick(View v) {

			if (toggleButtonPassword.isChecked()) {
				editTextInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			} else {
				editTextInput.setInputType(InputType.TYPE_CLASS_TEXT);
			}

		}

	}

	private class ButtonCommandListener implements OnClickListener {

		public void onClick(View v) {

			String check = editTextInput.getText().toString();
			textViewResult.setText(check);

			if (check.contentEquals("left")) {
				textViewResult.setGravity(Gravity.LEFT);
			} else if (check.contentEquals("center")) {
				textViewResult.setGravity(Gravity.CENTER);
			} else if (check.contentEquals("right")) {
				textViewResult.setGravity(Gravity.RIGHT);
			} else if (check.contentEquals("blue")) {
				textViewResult.setTextColor(Color.BLUE);
			} else if (check.contentEquals("WTF")) {

				Random r = new Random();
				textViewResult.setText("WTF!!!!");
				textViewResult.setTextSize(r.nextInt(75));
				textViewResult.setTextColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
				switch (r.nextInt(3)) {
					case 0:
						textViewResult.setGravity(Gravity.LEFT);
						break;
					case 2:
						textViewResult.setGravity(Gravity.CENTER);
						break;
					default:
						textViewResult.setGravity(Gravity.RIGHT);
						break;

				}

			} else {

				textViewResult.setText("Invalid");
				textViewResult.setGravity(Gravity.CENTER);

			}

		}

	}

}

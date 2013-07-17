package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DataGet extends Activity {

	TextView textViewQuestion, textViewAnswer;
	RadioGroup radioGroupAnswers;
	Button buttonReturn;
	String textGot, setData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.get);
		init();
		getData();

	}

	private void init() {

		textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
		textViewAnswer = (TextView) findViewById(R.id.textViewAnswer);
		radioGroupAnswers = (RadioGroup) findViewById(R.id.radioGroupAnswers);
		radioGroupAnswers.setOnCheckedChangeListener(new RadioButtonsChangeListener());
		buttonReturn = (Button) findViewById(R.id.buttonReturn);
		buttonReturn.setOnClickListener(new ButtonReturnClickListener());

	}

	private void getData() {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String name = preferences.getString("prefName", "Travis is...");
		String list = preferences.getString("prefList", "4");
		if (list.contentEquals("1")) {
			textViewQuestion.setText(name);
		}

		// get bundle from other activity / class
		try {
			Bundle basketGot = getIntent().getExtras();
			textGot = basketGot.getString("key");
			textViewQuestion.setText(textGot);
		} catch (NullPointerException ex) {
		}

	}

	private class RadioButtonsChangeListener implements OnCheckedChangeListener {

		public void onCheckedChanged(RadioGroup group, int checkedId) {

			switch (checkedId) {

				case R.id.radioCrazy:
					setData = "Probably Right!";
					break;

				case R.id.radioCool:
					setData = "Def Right!";
					break;

				case R.id.radioSuper:
					setData = "Spot On!";
					break;

			}

			textViewAnswer.setText(setData);

		}

	}

	private class ButtonReturnClickListener implements OnClickListener {

		public void onClick(View v) {

			Intent intent = new Intent();
			Bundle dataToSend = new Bundle();
			dataToSend.putString("answer", setData);
			intent.putExtras(dataToSend);
			setResult(RESULT_OK, intent);

			// finish this activity
			finish();

		}

	}

}

package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DataSend extends Activity {

	Button buttonStartActivity, buttonStartActivityWithResult;
	EditText editTextSend;
	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);
		init();

	}

	private void init() {

		buttonStartActivity = (Button) findViewById(R.id.buttonStartActivity);
		buttonStartActivityWithResult = (Button) findViewById(R.id.buttonStartActivityForResult);
		editTextSend = (EditText) findViewById(R.id.editTextSend);
		textView = (TextView) findViewById(R.id.textView);

		buttonStartActivity.setOnClickListener(new ButtonStartActivityListener());
		buttonStartActivityWithResult.setOnClickListener(new ButtonStartActivityWithResultsListener());

	}

	private class ButtonStartActivityListener implements OnClickListener {

		public void onClick(View v) {

			String text = editTextSend.getText().toString();
			Bundle basket = new Bundle();
			basket.putString("key", text);

			Intent intent = new Intent(DataSend.this, DataGet.class);
			intent.putExtras(basket);

			startActivity(intent);

		}

	}

	private class ButtonStartActivityWithResultsListener implements OnClickListener {

		public void onClick(View v) {

			Intent intent = new Intent(DataSend.this, DataGet.class);
			startActivityForResult(intent, 0);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			Bundle dataGot = data.getExtras();
			String answer = dataGot.getString("answer");
			textView.setText(answer);

		}

	}

}

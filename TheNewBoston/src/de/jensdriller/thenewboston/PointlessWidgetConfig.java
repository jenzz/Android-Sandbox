package de.jensdriller.thenewboston;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

public class PointlessWidgetConfig extends Activity implements OnClickListener {

	AppWidgetManager appWidgetManager;
	int appWidgetID;
	EditText editTextInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.widgetconfig);
		Button buttonWidgetOpen = (Button) findViewById(R.id.buttonWidgetGetConfig);
		buttonWidgetOpen.setOnClickListener(this);
		editTextInfo = (EditText) findViewById(R.id.editTextWidgetConfig);

		// get info about the widget that launched this activity
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			appWidgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		} else {
			finish();
		}

		appWidgetManager = AppWidgetManager.getInstance(this);
	}

	public void onClick(View v) {

		String info = editTextInfo.getText().toString();

		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget);
		remoteViews.setTextViewText(R.id.textViewConfigInput, info);

		Intent intent = new Intent(PointlessWidgetConfig.this, SplashScreen.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.buttonWidgetOpen, pendingIntent);
		appWidgetManager.updateAppWidget(appWidgetID, remoteViews);

		Intent result = new Intent();
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetID);
		setResult(RESULT_OK, result);

		// close activity
		finish();

	}
}

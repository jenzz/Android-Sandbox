package de.jensdriller.thenewboston;

import java.util.Random;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

public class PointlessWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Random r = new Random();
		int randomInt = r.nextInt(10000000);
		String randomString = String.valueOf(randomInt);

		for (int i = 0; i < appWidgetIds.length; i++) {

			int appWidgetID = appWidgetIds[i];
			RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget);
			remoteView.setTextViewText(R.id.textViewWidgetUpdate, randomString);
			appWidgetManager.updateAppWidget(appWidgetID, remoteView);

		}

	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {

		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "See ya sucka!", Toast.LENGTH_SHORT).show();

	}

}
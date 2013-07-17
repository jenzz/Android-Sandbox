package de.jensdriller.thenewboston;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationBar extends Activity implements OnClickListener {

	NotificationManager notificationManager;
	int uniqueID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		((Button) findViewById(R.id.buttonNotification)).setOnClickListener(this);

		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

	}

	public void onClick(View v) {

		Intent intent = new Intent(this, NotificationBar.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		String title = "Yoo whaaat's up?";
		String body = "This is a notification message!";

		Notification notification = new Notification(R.drawable.lightbulb, body, System.currentTimeMillis());
		notification.setLatestEventInfo(this, title, body, pendingIntent);
		notification.defaults = notification.DEFAULT_ALL;
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(uniqueID, notification);
		uniqueID++;

	}

}

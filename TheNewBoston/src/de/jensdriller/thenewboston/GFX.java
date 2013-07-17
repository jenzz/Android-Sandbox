package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class GFX extends Activity {

	CustomView customView;
	WakeLock wakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// wake-lock (keep screen fully awake all time)
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WakeLock");

		super.onCreate(savedInstanceState);
		this.customView = new CustomView(this);
		setContentView(this.customView);

	}

	@Override
	protected void onResume() {

		super.onResume();
		wakeLock.acquire();

	}

	@Override
	protected void onPause() {

		super.onPause();
		wakeLock.release();

	}

}

package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class SplashScreen extends Activity {

	MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mediaPlayer = MediaPlayer.create(SplashScreen.this, R.raw.laser);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		if (preferences.getBoolean("prefPlaySound", true)) {

			mediaPlayer.start();

		}

		Thread timer = new Thread() {
			public void run() {
				super.run();
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openMenu = new Intent("de.jensdriller.thenewboston.MENU");
					startActivity(openMenu);
				}
			}
		};
		timer.start();

		setContentView(R.layout.splashscreen);

	}

	@Override
	protected void onPause() {

		super.onPause();
		mediaPlayer.release();
		finish();

	}

}

package de.jensdriller.thenewboston;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class Sound extends Activity implements OnClickListener, OnLongClickListener {

	SoundPool soundPool;
	int explosion;
	MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		View v = new View(this);
		v.setOnClickListener(this);
		v.setOnLongClickListener(this);
		setContentView(v);

		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		explosion = soundPool.load(this, R.raw.laser, 1);
		mediaPlayer = MediaPlayer.create(this, R.raw.boing);

	}

	public void onClick(View v) {

		if (explosion != 0) {
			soundPool.play(explosion, 1, 1, 0, 0, 1);
		}

	}

	public boolean onLongClick(View v) {

		mediaPlayer.start();
		return false;

	}

}

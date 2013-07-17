package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarVolume extends Activity implements OnSeekBarChangeListener {

	SeekBar seekBar;
	MediaPlayer mediaPlayer;
	AudioManager audioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.seekbarvolume);
		seekBar = (SeekBar) findViewById(R.id.seekBarVolume);
		seekBar.setOnSeekBarChangeListener(this);

		mediaPlayer = MediaPlayer.create(this, R.raw.backgroundmusic);
		mediaPlayer.start();
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		seekBar.setMax(maxVolume);
		seekBar.setProgress(currentVolume);

	}

	@Override
	protected void onPause() {

		super.onPause();
		mediaPlayer.release();

	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}
}

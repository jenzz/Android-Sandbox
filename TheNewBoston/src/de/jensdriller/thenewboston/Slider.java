package de.jensdriller.thenewboston;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class Slider extends Activity implements OnClickListener, OnCheckedChangeListener, OnDrawerOpenListener {

	SlidingDrawer slidingDrawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.slider);

		Button button1 = (Button) findViewById(R.id.handle1);
		Button button2 = (Button) findViewById(R.id.handle2);
		Button button3 = (Button) findViewById(R.id.handle3);
		CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox);
		checkbox.setOnCheckedChangeListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);

		slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
		slidingDrawer.setOnDrawerOpenListener(this);

	}

	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.handle1:
				slidingDrawer.open();
				break;

			case R.id.handle2:
				slidingDrawer.toggle();
				break;

			case R.id.handle3:
				slidingDrawer.close();
				break;

		}

	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (isChecked) {
			slidingDrawer.lock();
		} else {
			slidingDrawer.unlock();
		}

	}

	public void onDrawerOpened() {

		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.boing);
		mediaPlayer.start();

	}

}

package de.jensdriller.thenewboston;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GFXSurface extends Activity implements OnTouchListener {

	CustomViewSurface customViewSurface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.customViewSurface = new CustomViewSurface(this);
		customViewSurface.setOnTouchListener(this);

		setContentView(this.customViewSurface);

	}

	@Override
	protected void onPause() {

		super.onPause();
		this.customViewSurface.pause();

	}

	@Override
	protected void onResume() {

		super.onResume();
		this.customViewSurface.resume();

	}

	public boolean onTouch(View v, MotionEvent event) {

		this.customViewSurface.setPosition(event.getX(), event.getY());
		return true;

	}

}

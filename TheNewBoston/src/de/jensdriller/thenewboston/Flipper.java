package de.jensdriller.thenewboston;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ViewFlipper;

public class Flipper extends Activity implements OnClickListener {

	ViewFlipper viewFlipper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.flipper);

		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		viewFlipper.setOnClickListener(this);
		viewFlipper.setFlipInterval(500);
		viewFlipper.startFlipping();

	}

	public void onClick(View v) {

		viewFlipper.showNext();

	}

}

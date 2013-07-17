package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Accelerate extends Activity implements SensorEventListener {

	float x, y, sensorX, sensorY;
	Bitmap ball;
	SensorManager sensorManager;
	CustomViewSurface surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		surfaceView = new CustomViewSurface(this);
		surfaceView.resume();
		setContentView(surfaceView);

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			Sensor sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}

		ball = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
		x = y = sensorX = sensorY = 0;
	}

	public void onAccuracyChanged(Sensor s, int i) {

	}

	public void onSensorChanged(SensorEvent e) {

		sensorX = e.values[0];
		sensorY = e.values[1];

	}

	@Override
	protected void onPause() {

		super.onPause();

	}

	public class CustomViewSurface extends SurfaceView implements Runnable {

		SurfaceHolder surfaceHolder;
		Thread thread = null;
		Bitmap tmp;
		boolean isRunning;

		public CustomViewSurface(Context context) {

			super(context);
			this.surfaceHolder = getHolder();

		}

		public void run() {

			while (this.isRunning) {

				if (!surfaceHolder.getSurface().isValid()) {
					continue; // loop through if-statement until surface is valid
				}

				// no other classes/activities/threads can access the canvas
				Canvas canvas = surfaceHolder.lockCanvas();
				canvas.drawRGB(2, 2, 150);
				float centerX = canvas.getWidth() / 2;
				float centerY = canvas.getHeight() / 2;
				canvas.drawBitmap(ball, centerX - sensorX * 25, centerY + sensorY * 25, null);
				surfaceHolder.unlockCanvasAndPost(canvas);

			}

		}

		public void pause() {

			this.isRunning = false;
			this.thread.interrupt();
			this.thread = null;

		}

		public void resume() {

			this.isRunning = true;
			this.thread = new Thread(this);
			this.thread.start();

		}

	}

}

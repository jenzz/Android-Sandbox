package de.jensdriller.thenewboston;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomViewSurface extends SurfaceView implements Runnable {

	SurfaceHolder surfaceHolder;
	Thread thread = null;
	float x = 0.0f, y = 0.0f;
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
			if (x != 0.0f && y != 0.0f) {
				this.tmp = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
				canvas.drawBitmap(tmp, this.x - this.tmp.getWidth() / 2, this.y - this.tmp.getWidth() / 2, null);
			}
			surfaceHolder.unlockCanvasAndPost(canvas);
		}

	}

	public void setPosition(float x, float y) {

		this.x = x;
		this.y = y;

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

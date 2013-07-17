package de.jensdriller.thenewboston;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.view.View;

public class CustomView extends View {

	Bitmap greenBall;
	float y;
	Typeface font;

	public CustomView(Context context) {

		super(context);
		this.greenBall = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
		this.y = 0.0f;
		this.font = Typeface.createFromAsset(context.getAssets(), "BIRTH OF A HERO.ttf");

	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);

		// draw font
		Paint textPaint = new Paint();
		textPaint.setARGB(50, 245, 10, 50);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(100.0f);
		textPaint.setTypeface(this.font);
		canvas.drawText("Yeeeah Sexy!!!", canvas.getWidth() / 2, 200, textPaint);

		// animate ball
		canvas.drawBitmap(this.greenBall, canvas.getWidth() / 2 - this.greenBall.getWidth() / 2, this.y, null);
		if (this.y < canvas.getHeight()) {
			this.y += 10;
		} else {
			this.y = 0;
		}

		// draw rectangle
		Rect middleRect = new Rect();
		middleRect.set(0, 400, canvas.getWidth(), 550);
		Paint blue = new Paint();
		blue.setColor(Color.BLUE);
		canvas.drawRect(middleRect, blue);

		// all onDraw again and again
		invalidate();

	}

}

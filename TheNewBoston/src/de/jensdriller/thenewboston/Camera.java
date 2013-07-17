package de.jensdriller.thenewboston;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Camera extends Activity {

	ImageButton imageButton;
	Button button;
	ImageView imageView;
	Intent intent;
	int cameraData = 0;
	Bitmap bmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);
		init();
		Toast.makeText(this, "onCreate called", Toast.LENGTH_SHORT).show();

		// pre-set bitmap with default icon
		InputStream is = getResources().openRawResource(R.drawable.ic_launcher);
		bmp = BitmapFactory.decodeStream(is);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
		Toast.makeText(this, "onConfigurationChanged called", Toast.LENGTH_SHORT).show();
		setContentView(R.layout.camera);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data");
			imageView.setImageBitmap(bmp);

		}

	}

	private void init() {

		imageButton = (ImageButton) findViewById(R.id.imageButtonTakePicture);
		button = (Button) findViewById(R.id.buttonSetWallpaper);
		imageView = (ImageView) findViewById(R.id.imageViewIcon);

		button.setOnClickListener(new ButtonListener());
		imageButton.setOnClickListener(new ImageButtonListener());

	}

	private class ButtonListener implements OnClickListener {

		public void onClick(View v) {

			try {
				getApplication().setWallpaper(bmp);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private class ImageButtonListener implements OnClickListener {

		public void onClick(View v) {

			intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, cameraData);

		}

	}

}

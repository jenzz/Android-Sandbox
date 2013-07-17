package de.jensdriller.thenewboston;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimpleBrowser extends Activity implements OnClickListener {

	EditText urlText;
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplebrowser);

		urlText = (EditText) findViewById(R.id.editTextURL);

		Button buttonGo = (Button) findViewById(R.id.buttonGo);
		Button buttonBack = (Button) findViewById(R.id.buttonBack);
		Button buttonForward = (Button) findViewById(R.id.buttonForward);
		Button buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
		Button buttonClearHistory = (Button) findViewById(R.id.buttonClearHistory);
		buttonGo.setOnClickListener(this);
		buttonBack.setOnClickListener(this);
		buttonForward.setOnClickListener(this);
		buttonRefresh.setOnClickListener(this);
		buttonClearHistory.setOnClickListener(this);

		webView = (WebView) findViewById(R.id.webView);
		webView.setWebViewClient(new InAppWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		try {
			webView.loadUrl("http://www.bild.de");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
		}

	}

	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonGo:
				webView.loadUrl(urlText.getText().toString());
				// hide keyboard
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(urlText.getWindowToken(), 0);
				break;

			case R.id.buttonBack:
				if (webView.canGoBack()) {
					webView.goBack();
				}
				break;

			case R.id.buttonForward:
				if (webView.canGoForward()) {
					webView.goForward();
				}
				break;

			case R.id.buttonRefresh:
				webView.reload();
				break;

			case R.id.buttonClearHistory:
				webView.clearHistory();
				break;

		}

	}

	private class InAppWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			return super.shouldOverrideUrlLoading(view, url);

		}

	}
}

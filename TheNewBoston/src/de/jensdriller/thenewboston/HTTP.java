package de.jensdriller.thenewboston;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class HTTP extends Activity {

	TextView textViewHttpResult;
	HttpClient httpClient;
	JSONObject json;

	final static String URL = "http://api.twitter.com/1/statuses/user_timeline.json?screen_name=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.httpexample);
		textViewHttpResult = (TextView) findViewById(R.id.textViewHttpResult);

		httpClient = new DefaultHttpClient();
		// "text" = json object containing twitter text
		new Read().execute("text");

	}

	public JSONObject lastTweet(String username) throws ClientProtocolException, IOException, JSONException {

		HttpGet request = new HttpGet(URL + username);
		HttpResponse response = httpClient.execute(request);

		int status = response.getStatusLine().getStatusCode();
		if (status == 200) {

			HttpEntity httpEntity = response.getEntity();
			String data = EntityUtils.toString(httpEntity);
			JSONArray timeline = new JSONArray(data);
			// 0 = most recent tweet
			JSONObject lastTweet = timeline.getJSONObject(0);
			return lastTweet;

		}

		return null;

	}

	private class Read extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			try {

				json = lastTweet("mybringback");
				return json.getString(params[0]);

			} catch (ClientProtocolException e) {
				Toast.makeText(HTTP.this, e.toString(), Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				Toast.makeText(HTTP.this, e.toString(), Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				Toast.makeText(HTTP.this, e.toString(), Toast.LENGTH_LONG).show();
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
			textViewHttpResult.setText(result);

		}

	}

}

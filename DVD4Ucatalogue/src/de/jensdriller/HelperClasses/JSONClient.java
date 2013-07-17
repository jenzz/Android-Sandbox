package de.jensdriller.HelperClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class JSONClient extends AsyncTask<String, Void, JSONObject> {

	private ProgressDialog progressDialog;
	private GetJSONListener getJSONListener;
	private Context context;
	private Exception e;

	public JSONClient(Context context) {
		
		this.context = context;
		
	}
	
	public JSONClient(Context context, GetJSONListener getJSONListener) {

		this(context);
		this.getJSONListener = getJSONListener;

	}
	
	public void setGetJSONListener(GetJSONListener getJSONListener) {
		
		this.getJSONListener = getJSONListener;
		
	}

	@Override
	public void onPreExecute() {
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Loading.. Please wait..");
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();

	}

	@Override
	protected JSONObject doInBackground(String... urls) {

		return connect(urls[0]);

	}

	@Override
	protected void onPostExecute(JSONObject jsonObject) {

		if(this.e == null) {
			getJSONListener.onJSONDownloadComplete(jsonObject);
		} else {
			getJSONListener.onJSONDownloadError(this.e);
		}		
		progressDialog.dismiss();

	}

	private JSONObject connect(String url) {
		
		HttpClient httpClient = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(url);

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);

				JSONObject json = new JSONObject(result);
				instream.close();

				return json;
			}

		} catch (Exception e) {
			this.e = e;
		}

		return null;

	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();

	}

}
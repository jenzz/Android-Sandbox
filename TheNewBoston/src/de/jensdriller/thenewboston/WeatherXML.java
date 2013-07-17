package de.jensdriller.thenewboston;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WeatherXML extends Activity implements OnClickListener {

	static final String URL = "http://www.google.com/ig/api?weather=";
	TextView textViewResults;
	EditText editTextCity, editTextState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.weatherxml);
		Button buttonGetXML = (Button) findViewById(R.id.buttonGetWeather);
		buttonGetXML.setOnClickListener(this);
		editTextCity = (EditText) findViewById(R.id.editTextCity);
		editTextState = (EditText) findViewById(R.id.editTextState);
		textViewResults = (TextView) findViewById(R.id.textViewXMLResult);

	}

	public void onClick(View v) {

		String city = editTextCity.getText().toString();
		String state = editTextState.getText().toString();

		String requestURL = URL + city + "," + state;

		new Read().execute(requestURL);

	}

	private class Read extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			try {

				URL url = new URL(params[0]);
				SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
				SAXParser saxParser = saxParserFactory.newSAXParser();
				XMLReader xmlReader = saxParser.getXMLReader();

				WeatherXMLHandler doingWork = new WeatherXMLHandler();
				xmlReader.setContentHandler(doingWork);
				xmlReader.parse(new InputSource(url.openStream()));
				return doingWork.getInfo();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
			textViewResults.setText(result);

		}

	}

}

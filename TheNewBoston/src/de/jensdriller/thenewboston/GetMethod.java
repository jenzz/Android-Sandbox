package de.jensdriller.thenewboston;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetMethod {

	public String getHTTPData() throws Exception {

		BufferedReader bufferedReader = null;
		String data = null;

		try {

			HttpClient httpClient = new DefaultHttpClient();
			URI uri = new URI("htrasdp://www.mybringback.com");
			HttpGet request = new HttpGet();
			request.setURI(uri);

			HttpResponse response = httpClient.execute(request);
			bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer();
			String line = "";
			String newLine = System.getProperty("line.separator");
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + newLine);
			}
			bufferedReader.close();
			data = stringBuffer.toString();
			return data;

		} catch (Exception e) {

			throw e;

		} finally {

			if (bufferedReader != null) {
				try {
					bufferedReader.close();
					return data;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

}

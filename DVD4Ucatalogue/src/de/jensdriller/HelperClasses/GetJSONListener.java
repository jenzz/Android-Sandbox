package de.jensdriller.HelperClasses;

import org.json.JSONObject;

public interface GetJSONListener {
	
	void onJSONDownloadComplete(JSONObject jsonObject);
	void onJSONDownloadError(Exception e);
	
}
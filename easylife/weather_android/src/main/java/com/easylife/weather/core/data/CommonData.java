package com.easylife.weather.core.data;

import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.util.Log;

public class CommonData {
	private static List<String> peelsList = null;
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static List<String> getColors(Context context) {
		if (peelsList == null) {
			try {
				peelsList = objectMapper.readValue(
						context.getAssets().open("peels.txt"),
						new TypeReference<List<String>>() {
						});
				return peelsList;
			} catch (Exception e) {
				Log.d("getCoolrs", "json to getCoolrs is error", e);
			}
			return Collections.emptyList();
		} else {
			return peelsList;
		}
	}

}

package com.easylife.weather.core.data;

import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.BuildConfig;

public class CommonData {
	private static List<String> peelsList = null;
	private static List<String> hoursList = null;
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

	public static List<String> getRemindHours(Context context) {
		if (hoursList == null) {
			try {
				hoursList = objectMapper.readValue(
						context.getAssets().open("hours.txt"),
						new TypeReference<List<String>>() {
						});
				return hoursList;
			} catch (Exception e) {
				if (BuildConfig.DEBUG) {
					Log.d("getRemindHours", "json to getRemindHours is error",
							e);
				}
			}
			return Collections.emptyList();
		} else {
			return hoursList;
		}
	}

}

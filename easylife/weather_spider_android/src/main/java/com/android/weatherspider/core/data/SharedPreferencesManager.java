package com.android.weatherspider.core.data;

import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
	private SharedPreferences sharedPreferences;
	public static final String HAS_SPIDER_TEXT = "has_spider_text";
	public static final String HAS_RUN_TASK_TEXT = "has_run_task_text";
	public static final String HAS_WIFI_TEXT = "has_wifi_text";
	public static final String HOUR_OF_DAY = "hour_of_day";
	public static final String MINUTE = "minute";

	public SharedPreferencesManager(Context context) {
		sharedPreferences = context.getSharedPreferences("juzhai-android",
				Context.MODE_PRIVATE);
	}

	public boolean isExist(String key) {
		return sharedPreferences.contains(key);
	}

	public void delete(String key) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public void commit(String key, String value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void commit(String key, boolean value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void commit(String key, int value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public void commit(Map<String, String> map) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		for (Entry<String, String> entry : map.entrySet()) {
			editor.putString(entry.getKey(), entry.getValue());
		}
		editor.commit();
	}

	public String getString(String key) {
		return sharedPreferences.getString(key, null);
	}

	public int getInt(String key) {
		return sharedPreferences.getInt(key, 0);
	}

	public int getInt(String key, int defValue) {
		return sharedPreferences.getInt(key, defValue);
	}

	public boolean getBoolean(String key) {
		return sharedPreferences.getBoolean(key, false);
	}
}

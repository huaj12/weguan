package com.easylife.weather.core.data;

import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
	private SharedPreferences sharedPreferences;
	public static final String HISTORY = "history_search_content";
	public static final String DEVICE_TOKEN = "device_token";
	public static final String USER_CONFIG = "user_config";
	public final static String P_WEATHERINFO = "p_weatherinfo";
	public final static String P_USER_CONFIG = "p_user_config";
	public final static String P_BACKGROUNDCOLOR = "p_backgroundcolor";
	public final static String CREATE_SHORTCUT = "create_shortcut";
	public final static String LAST_UPDATE_TIME = "last_update_time";

	public SharedPreferencesManager(Context context) {
		sharedPreferences = context.getSharedPreferences("weather-android",
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

	public void commit(String key, long value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
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

	public String getString(String key, String defValue) {
		return sharedPreferences.getString(key, defValue);
	}

	public int getInt(String key) {
		return sharedPreferences.getInt(key, 0);
	}

	public long getLong(String key) {
		return sharedPreferences.getLong(key, 0);
	}

	public int getInt(String key, int defValue) {
		return sharedPreferences.getInt(key, defValue);
	}

	public boolean getBoolean(String key) {
		return sharedPreferences.getBoolean(key, false);
	}
}

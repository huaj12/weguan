package com.juzhai.android.core.data;

import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
	private SharedPreferences sharedPreferences;
	
	public SharedPreferencesManager(Context context) {
		sharedPreferences = context.getSharedPreferences("juzhai-android",
				Context.MODE_PRIVATE);
	}

	public boolean isExist(String key) {
		return sharedPreferences.contains(key);
	}

	public void commit(String key, String value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
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
}

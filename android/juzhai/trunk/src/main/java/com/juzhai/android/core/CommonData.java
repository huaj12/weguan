package com.juzhai.android.core;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.core.model.Category;
import com.juzhai.android.core.model.CategoryResults;
import com.juzhai.android.core.utils.JackSonSerializer;

public class CommonData {
	public static List<Category> getCategorys(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SystemConfig.SHAREDPREFERNCES_NAME, Context.MODE_PRIVATE);
		String JsonString = sharedPreferences.getString("category", null);
		if (JsonString != null) {
			try {
				CategoryResults results = JackSonSerializer.toBean(JsonString,
						CategoryResults.class);
				return results.getResult();
			} catch (Exception e) {
				if (BuildConfig.DEBUG) {
					Log.d("getCategorys", "json to Category is error", e);
				}
			}
		}
		return Collections.emptyList();
	}
}

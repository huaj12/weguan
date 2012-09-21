package com.juzhai.android.common.service;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.common.model.Category;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.Result.CategoryResult;
import com.juzhai.android.core.utils.JackSonSerializer;

public class CommonData {
	public static List<Category> getCategorys(Context context) {
		String JsonString = new SharedPreferencesManager(context)
				.getString("category");
		if (JsonString != null) {
			try {
				CategoryResult result = JackSonSerializer.toBean(JsonString,
						CategoryResult.class);
				return result.getResult();
			} catch (Exception e) {
				if (BuildConfig.DEBUG) {
					Log.d("getCategorys", "json to Category is error", e);
				}
			}
		}
		return Collections.emptyList();
	}
}

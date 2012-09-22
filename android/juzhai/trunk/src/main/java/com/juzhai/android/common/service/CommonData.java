package com.juzhai.android.common.service;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.common.model.Category;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.Result.CategoryResult;
import com.juzhai.android.core.utils.JacksonSerializer;

public class CommonData {
	public static List<Category> getCategorys(Context context) {
		// TODO (review) 变量名是大写的吗？
		// TODO (review) "category"为什么没有常量？存储的地方不需要用？为什么要分开在不同地方使用？
		String JsonString = new SharedPreferencesManager(context)
				.getString("category");
		if (JsonString != null) {
			// TODO (review) 空字符串就能进入if里了吗？
			try {
				CategoryResult result = JacksonSerializer.toBean(JsonString,
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

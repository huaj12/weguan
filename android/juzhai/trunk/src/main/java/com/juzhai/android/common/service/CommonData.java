package com.juzhai.android.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.common.model.Category;
import com.juzhai.android.common.task.InitDataTask;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.Result.CategoryResult;
import com.juzhai.android.core.utils.JacksonSerializer;

public class CommonData {
	public static List<Category> getCategorys(Context context) {
		// TODO (done) 变量名是大写的吗？
		// TODO (done) "category"为什么没有常量？存储的地方不需要用？为什么要分开在不同地方使用？
		String jsonString = new SharedPreferencesManager(context)
				.getString(SharedPreferencesManager.CATEGORY);
		if (StringUtils.isNotEmpty(jsonString)) {
			// TODO (done) 空字符串就能进入if里了吗？
			try {
				CategoryResult result = JacksonSerializer.toBean(jsonString,
						CategoryResult.class);
				Category cat = new Category();
				cat.setCategoryId(0);
				cat.setName(context.getResources().getString(R.string.all));
				List<Category> list = new ArrayList<Category>();
				list.add(cat);
				list.addAll(result.getResult());
				return list;
			} catch (Exception e) {
				if (BuildConfig.DEBUG) {
					Log.d("getCategorys", "json to Category is error", e);
				}
			}
		}
		return Collections.emptyList();
	}

	public static String[] getCategoryNames(Context context) {
		List<Category> categorys = getCategorys(context);
		String[] categoryNames = new String[categorys.size()];
		for (int i = 0; i < categorys.size(); i++) {
			categoryNames[i] = categorys.get(i).getName();
		}
		return categoryNames;
	}

	public static void InitDate(Context context) {
		new InitDataTask(context).execute();
	}
}

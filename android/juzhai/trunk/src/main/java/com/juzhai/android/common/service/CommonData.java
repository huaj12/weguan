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
		String jsonString = new SharedPreferencesManager(context)
				.getString(SharedPreferencesManager.CATEGORY);
		if (StringUtils.isNotEmpty(jsonString)) {
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

	//TODO (review) 自己看有什么问题。如果你希望把所有基础数据请求放在一个任务里，那就用内部类来实现，因为外部不会使用InitDataTask
	public static void InitDate(Context context) {
		new InitDataTask(context).execute();
	}
}

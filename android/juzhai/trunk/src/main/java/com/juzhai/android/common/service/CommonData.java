package com.juzhai.android.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.common.model.Category;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.Result.CategoryResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.utils.JacksonSerializer;

public class CommonData {
	// TODO (done) 这个是基础类，为什么会有category这个东西？
	public static final String SHAREDPREFERNCES_CATEGORY = "category";

	public static List<Category> getCategorys(Context context) {
		String jsonString = new SharedPreferencesManager(context)
				.getString(SHAREDPREFERNCES_CATEGORY);
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

	// TODO (done)
	// 自己看有什么问题。如果你希望把所有基础数据请求放在一个任务里，那就用内部类来实现，因为外部不会使用InitDataTask
	public static void InitDate(final Context context) {
		new AsyncTask<Void, Void, Boolean>() {
			private final String CATEGORY_URI = "base/categoryList";
			private SharedPreferencesManager manager = new SharedPreferencesManager(
					context);

			@Override
			protected Boolean doInBackground(Void... params) {
				if (!manager.isExist(SHAREDPREFERNCES_CATEGORY)) {
					initCategory();
				}
				return true;
			}

			private void initCategory() {
				ResponseEntity<CategoryResult> response = HttpUtils.get(
						CATEGORY_URI, CategoryResult.class);
				if (response != null && response.getBody() != null
						&& response.getBody().getSuccess()) {
					CategoryResult categoryResult = response.getBody();
					try {
						manager.commit(SHAREDPREFERNCES_CATEGORY,
								JacksonSerializer.toString(categoryResult));
					} catch (JsonGenerationException e) {
						if (BuildConfig.DEBUG) {
							Log.d(getClass().getSimpleName(),
									"Category to json is error", e);
						}
					}

				}
			}
		}.execute();
	}
}

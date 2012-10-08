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
	public static final String SHARED_PREFERNCES_CATEGORY = "category";

	public static List<Category> getCategorys(Context context) {
		String jsonString = new SharedPreferencesManager(context)
				.getString(SHARED_PREFERNCES_CATEGORY);
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

	// TODO (done) 自己看有什么问题。
	public static void initDate(final Context context) {
		final SharedPreferencesManager manager = new SharedPreferencesManager(
				context);
		if (!manager.isExist(SHARED_PREFERNCES_CATEGORY)) {
			new AsyncTask<Void, Void, Boolean>() {
				private final String CATEGORY_URI = "base/categoryList";

				@Override
				protected Boolean doInBackground(Void... params) {
					// TODO (done) 自己看什么问题
					if (!manager.isExist(SHARED_PREFERNCES_CATEGORY)) {
						initCategory();
					}
					return true;
				}

				private void initCategory() {
					ResponseEntity<CategoryResult> response = HttpUtils.get(
							context, CATEGORY_URI, CategoryResult.class);
					if (response != null && response.getBody() != null
							&& response.getBody().getSuccess()) {
						CategoryResult categoryResult = response.getBody();
						try {
							manager.commit(SHARED_PREFERNCES_CATEGORY,
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
}

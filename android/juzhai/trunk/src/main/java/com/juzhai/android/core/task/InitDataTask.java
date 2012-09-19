package com.juzhai.android.core.task;

import org.codehaus.jackson.JsonGenerationException;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.CategoryResults;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.utils.JackSonSerializer;

public class InitDataTask extends AsyncTask<Void, Void, Boolean> {
	private final String CATEGORY_URI = "base/categoryList";
	private SharedPreferencesManager manager;

	public InitDataTask(Context context) {
		manager = new SharedPreferencesManager(context);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		if (!manager.isExist("category")) {
			initCategory();
		}
		return true;
	}

	private void initCategory() {
		ResponseEntity<CategoryResults> response = HttpUtils.get(CATEGORY_URI,
				CategoryResults.class);
		if (response != null && response.getBody() != null
				&& response.getBody().getSuccess()) {
			CategoryResults categoryResults = response.getBody();
			try {
				manager.commit("category",
						JackSonSerializer.toString(categoryResults));
			} catch (JsonGenerationException e) {
				if (BuildConfig.DEBUG) {
					Log.d(getClass().getSimpleName(),
							"Category to json is error", e);
				}
			}

		}
	}

}

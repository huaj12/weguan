package com.juzhai.android.core.task;

import org.codehaus.jackson.JsonGenerationException;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.core.SystemConfig;
import com.juzhai.android.core.model.CategoryResults;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.utils.JackSonSerializer;

public class InitDataTask extends AsyncTask<Void, Void, Boolean> {
	private final String CATEGORY_URI = "base/categoryList";
	private SharedPreferences sharedPreferences;
	private Context context;

	public InitDataTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		sharedPreferences = context.getSharedPreferences(
				SystemConfig.SHAREDPREFERNCES_NAME, Context.MODE_PRIVATE);
		if (!sharedPreferences.contains("category")) {
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
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("category",
						JackSonSerializer.toString(categoryResults));
				editor.commit();
			} catch (JsonGenerationException e) {
				if (BuildConfig.DEBUG) {
					Log.d(getClass().getSimpleName(),
							"Category to json is error", e);
				}
			}

		}
	}

}

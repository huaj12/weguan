package com.juzhai.android.dialog.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.DialogListResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.dialog.exception.DialogException;
import com.juzhai.android.dialog.service.IDialogService;
import com.juzhai.android.passport.data.UserCache;

public class DialogService implements IDialogService {
	private String dialogListUri = "dialog/dialogList";

	@Override
	public DialogListResult list(int page) throws DialogException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("page", page);
		String uri = HttpUtils.createHttpParam(dialogListUri, values);
		ResponseEntity<DialogListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(uri, UserCache.getUserStatus(),
					DialogListResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"get DialogListResult is  error", e);
			}
			throw new DialogException(R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();
	}

}

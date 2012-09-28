package com.juzhai.android.dialog.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.DialogContentListResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.dialog.exception.DialogContentException;
import com.juzhai.android.dialog.service.IDialogContentService;
import com.juzhai.android.passport.data.UserCache;

public class DialogContentService implements IDialogContentService {
	private String dialogContentListUri = "dialog/dialogContentList";

	@Override
	public DialogContentListResult list(long uid, int page)
			throws DialogContentException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("page", page);
		values.put("uid", uid);
		String uri = HttpUtils.createHttpParam(dialogContentListUri, values);
		ResponseEntity<DialogContentListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(uri, UserCache.getUserStatus(),
					DialogContentListResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"get DialogContentListResult error", e);
			}
			throw new DialogContentException(R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();
	}

}

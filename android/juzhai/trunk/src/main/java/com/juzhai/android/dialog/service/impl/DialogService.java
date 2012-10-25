package com.juzhai.android.dialog.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.DialogListResult;
import com.juzhai.android.core.model.Result.IntegerResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.dialog.exception.DialogException;
import com.juzhai.android.dialog.service.IDialogService;
import com.juzhai.android.passport.exception.NeedLoginException;

public class DialogService implements IDialogService {
	private String dialogListUri = "dialog/dialogList";
	private String noticeNumsUri = "dialog/notice/nums";

	@Override
	public DialogListResult list(Context context, int page)
			throws DialogException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("page", page);
		ResponseEntity<DialogListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, dialogListUri, values,
					DialogListResult.class);
		} catch (NeedLoginException e) {
			DialogListResult dialogListResult = new DialogListResult();
			dialogListResult.setSuccess(false);
			dialogListResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return dialogListResult;
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"get DialogListResult is  error", e);
			}
			throw new DialogException(context, R.string.system_internet_erorr,
					e);
		}
		return responseEntity.getBody();
	}

	@Override
	public int newMessageCount(Context context) {
		try {
			ResponseEntity<IntegerResult> responseEntity = HttpUtils.get(
					context, noticeNumsUri, IntegerResult.class);
			if (responseEntity.getBody() != null
					&& responseEntity.getBody() != null
					&& responseEntity.getBody().getSuccess()) {
				return responseEntity.getBody().getResult();
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "get newMessageCount error.", e);
		}
		return 0;
	}

}

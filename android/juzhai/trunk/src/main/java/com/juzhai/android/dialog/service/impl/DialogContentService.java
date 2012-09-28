package com.juzhai.android.dialog.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.PageList;
import com.juzhai.android.core.model.Result.DialogContentListResult;
import com.juzhai.android.core.model.Result.DialogContentResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.dialog.exception.DialogContentException;
import com.juzhai.android.dialog.model.DialogContent;
import com.juzhai.android.dialog.service.IDialogContentService;
import com.juzhai.android.passport.data.UserCache;

public class DialogContentService implements IDialogContentService {
	private String dialogContentListUri = "dialog/dialogContentList";
	private String sendMessageUri = "dialog/sendSms";
	private String refreshDialogContentListUri = "dialog/refreshDialogContent";

	@Override
	public PageList<DialogContent> list(long uid, int page)
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
		return responseEntity.getBody().getResult();
	}

	@Override
	public PageList<DialogContent> refreshList(long uid) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("uid", uid);
		String uri = HttpUtils.createHttpParam(refreshDialogContentListUri,
				values);
		ResponseEntity<DialogContentListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(uri, UserCache.getUserStatus(),
					DialogContentListResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"refresh DialogContentListResult error", e);
			}
			return null;
		}
		return responseEntity.getBody().getResult();
	}

	@Override
	public DialogContent sendSms(Context context, long uid, String content,
			Bitmap image) throws DialogContentException {
		ResponseEntity<DialogContentResult> responseEntity = null;
		try {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("content", content);
			values.put("uid", String.valueOf(uid));
			responseEntity = HttpUtils.uploadFile(sendMessageUri, values,
					UserCache.getUserStatus(), "dialogImg", image,
					DialogContentResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"send message thread is error", e);
			}
			throw new DialogContentException(R.string.system_internet_erorr, e);
		}
		if (responseEntity == null || responseEntity.getBody() == null) {
			throw new DialogContentException(R.string.system_internet_erorr);
		}

		if (!responseEntity.getBody().getSuccess()
				|| responseEntity.getBody().getResult() == null) {
			throw new DialogContentException(responseEntity.getBody()
					.getErrorInfo(), 0);
		} else {
			return responseEntity.getBody().getResult();
		}
	}
}

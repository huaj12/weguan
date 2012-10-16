package com.juzhai.android.home.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.StringResult;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.home.service.IHomeService;
import com.juzhai.android.passport.data.UserCacheManager;

public class HomeService implements IHomeService {

	private final String refeshUri = "home/refresh";
	private final String updatelocUri = "home/updateloc";

	@Override
	public String refresh(Context context) {
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils
					.get(context, refeshUri, UserResult.class);
		} catch (Exception e) {
			return context.getResources().getString(
					R.string.system_internet_erorr);
		}
		UserResult result = responseEntity.getBody();
		if (!result.getSuccess()) {
			return result.getErrorInfo();
		} else {
			UserCacheManager.updateUserCache(result.getResult());
		}
		return null;
	}

	@Override
	public void updateLocation(Context context, double longitude,
			double latitude) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("longitude", longitude);
		values.put("latitude", latitude);
		try {
			HttpUtils.post(context, updatelocUri, values, StringResult.class);
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "update location error.", e);
		}
	}
}
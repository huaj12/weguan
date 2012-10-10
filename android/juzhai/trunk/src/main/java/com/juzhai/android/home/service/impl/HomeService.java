package com.juzhai.android.home.service.impl;

import org.springframework.http.ResponseEntity;

import android.content.Context;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.home.service.IHomeService;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.data.UserCacheManager;

public class HomeService implements IHomeService {

	private final String refeshUri = "home/refresh";

	@Override
	public String refresh(Context context) {
		ResponseEntity<UserResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, refeshUri,
					UserCache.getUserStatus(), UserResult.class);
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

}

package com.juzhai.android.home.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.home.exception.HomeException;
import com.juzhai.android.home.service.IInterestUserService;
import com.juzhai.android.passport.exception.NeedLoginException;

public class InterestUserService implements IInterestUserService {
	private String interestMeUri = "home/interestMeList";
	private String interestUri = "home/interestList";

	@Override
	public UserListResult interestList(Context context, int page)
			throws HomeException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("page", page);
		ResponseEntity<UserListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, interestUri, values,
					UserListResult.class);
		} catch (NeedLoginException e) {
			UserListResult userListResult = new UserListResult();
			userListResult.setSuccess(false);
			userListResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return userListResult;
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "interestList is error", e);
			}
			throw new HomeException(context, R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();
	}

	@Override
	public UserListResult interestMeList(Context context, int page)
			throws HomeException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("page", page);
		ResponseEntity<UserListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, interestMeUri, values,
					UserListResult.class);
		} catch (NeedLoginException e) {
			UserListResult userListResult = new UserListResult();
			userListResult.setSuccess(false);
			userListResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return userListResult;
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "interestMeList is error", e);
			}
			throw new HomeException(context, R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();
	}

}

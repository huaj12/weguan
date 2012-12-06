package com.juzhai.android.idea.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.IdeaListResult;
import com.juzhai.android.core.model.Result.IdeaResult;
import com.juzhai.android.core.model.Result.IdeaUserListResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.passport.exception.NeedLoginException;

public class IdeaService implements IIdeaService {
	private String ideaListUri = "idea/allIdeas";
	private String ideaUsersUri = "idea/users";
	private String showIdeaUri = "idea/showIdea";

	@Override
	public IdeaListResult list(Context context, long categoryId,
			String orderType, int page) throws IdeaException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("categoryId", categoryId);
		values.put("orderType", orderType);
		values.put("page", page);
		ResponseEntity<IdeaListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, ideaListUri, values,
					IdeaListResult.class);
		} catch (NeedLoginException e) {
			IdeaListResult ideaListResult = new IdeaListResult();
			ideaListResult.setSuccess(false);
			ideaListResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return ideaListResult;
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"get IdeaListResult is  error", e);
			}
			throw new IdeaException(context, R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();

	}

	@Override
	public IdeaUserListResult listIdeaUser(Context context, long ideaId,
			long city, Integer gender, int page) throws IdeaException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("ideaId", ideaId);
		values.put("page", page);
		values.put("cityId", city);
		if (gender != null) {
			values.put("gender", gender);
		}
		ResponseEntity<IdeaUserListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, ideaUsersUri, values,
					IdeaUserListResult.class);
		} catch (NeedLoginException e) {
			IdeaUserListResult ideaUserListResult = new IdeaUserListResult();
			ideaUserListResult.setSuccess(false);
			ideaUserListResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return ideaUserListResult;
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "login error", e);
			}
			throw new IdeaException(context, R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();

	}

	@Override
	public IdeaResult showIdea(Context context, long ideaId)
			throws IdeaException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("ideaId", ideaId);
		ResponseEntity<IdeaResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, showIdeaUri, values,
					IdeaResult.class);
		} catch (NeedLoginException e) {
			IdeaResult ideaResult = new IdeaResult();
			ideaResult.setSuccess(false);
			ideaResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return ideaResult;
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "get showIdea is  error", e);
			}
			throw new IdeaException(context, R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();
	}

}

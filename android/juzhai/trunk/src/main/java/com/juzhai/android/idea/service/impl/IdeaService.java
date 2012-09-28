package com.juzhai.android.idea.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.IdeaListResult;
import com.juzhai.android.core.model.Result.IdeaUserListResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.passport.data.UserCache;

public class IdeaService implements IIdeaService {
	private String ideaListUri = "idea/list";
	private String ideaUsersUri = "idea/users";

	@Override
	public IdeaListResult list(long categoryId, String orderType, int page)
			throws IdeaException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("categoryId", categoryId);
		values.put("orderType", orderType);
		values.put("page", page);
		String uri = HttpUtils.createHttpParam(ideaListUri, values);
		ResponseEntity<IdeaListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(uri, UserCache.getUserStatus(),
					IdeaListResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"get IdeaListResult is  error", e);
			}
			throw new IdeaException(R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();

	}

	@Override
	public IdeaUserListResult listIdeaUser(long ideaId, int page)
			throws IdeaException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("ideaId", ideaId);
		values.put("page", page);
		String url = HttpUtils.createHttpParam(ideaUsersUri, values);
		ResponseEntity<IdeaUserListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(url, UserCache.getUserStatus(),
					IdeaUserListResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "login error", e);
			}
			throw new IdeaException(R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();

	}

}

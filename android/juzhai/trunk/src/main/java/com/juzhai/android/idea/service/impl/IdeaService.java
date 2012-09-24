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
	public IdeaListResult list(int categoryId, String orderType, int page)
			throws IdeaException {
		//TODO (review) 不能是<String,Object>
		Map<String, String> values = new HashMap<String, String>();
		values.put("categoryId", String.valueOf(categoryId));
		values.put("orderType", orderType);
		values.put("page", String.valueOf(page));
		String uri = ideaListUri + HttpUtils.createHttpParam(values);
		ResponseEntity<IdeaListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(uri, UserCache.getUserStatus(),
					IdeaListResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "login error", e);
			}
			throw new IdeaException(R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();

	}

	@Override
	public IdeaUserListResult listIdeaUser(long ideaId, int page)
			throws IdeaException {
		Map<String, String> values = new HashMap<String, String>();
		values.put("ideaId", String.valueOf(ideaId));
		values.put("page", String.valueOf(page));
		//TODO (review) 不能是<String,Object>
		String url = ideaUsersUri + HttpUtils.createHttpParam(values);
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

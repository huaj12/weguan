package com.juzhai.android.idea.service.impl;

import org.springframework.http.ResponseEntity;

import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.IdeaResult;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.passport.data.UserCache;

public class IdeaService implements IIdeaService {
	private String ideaListUri = "idea/list";

	@Override
	public IdeaResult list(int categoryId, String orderType, int page)
			throws IdeaException {
		String url = ideaListUri + "?categoryId=" + categoryId + "&orderType="
				+ orderType + "&page=" + page;
		ResponseEntity<IdeaResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(url, UserCache.getUserStatus(),
					IdeaResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "login error", e);
			}
			throw new IdeaException(R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();

	}

}

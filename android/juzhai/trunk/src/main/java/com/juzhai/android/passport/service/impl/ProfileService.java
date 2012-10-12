package com.juzhai.android.passport.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.exception.ProfileException;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.passport.service.IProfileService;

public class ProfileService implements IProfileService {
	private final String updateUserUri = "profile/save";

	@Override
	public void updateUser(User user, Context context) throws ProfileException {
		ResponseEntity<UserResult> responseEntity = null;
		try {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("nickname", user.getNickname());
			values.put("gender", user.getGender());
			values.put("feature", user.getFeature());
			values.put("professionId", user.getProfessionId());
			values.put("profession", user.getProfession());
			values.put("cityId", user.getCityId());
			values.put("birth",
					user.getBirthYear() + "-" + user.getBirthMonth() + "-"
							+ user.getBirthDay());
			responseEntity = HttpUtils.uploadFile(context, updateUserUri,
					values, UserCache.getUserStatus(), "logo",
					user.getLogoImage(), UserResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "updateUser  is error", e);
			}
			throw new ProfileException(R.string.system_internet_erorr, e);
		}
		if (responseEntity == null || responseEntity.getBody() == null) {
			throw new ProfileException(R.string.system_internet_erorr);
		}

		if (!responseEntity.getBody().getSuccess()
				|| responseEntity.getBody().getResult() == null) {
			throw new ProfileException(responseEntity.getBody().getErrorInfo(),
					0);
		} else {
			UserCacheManager.updateUserCache(responseEntity.getBody()
					.getResult());
		}

	}

}

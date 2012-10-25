package com.juzhai.android.passport.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.exception.NeedLoginException;
import com.juzhai.android.passport.exception.ProfileException;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.passport.service.IProfileService;
import com.umeng.analytics.MobclickAgent;

public class ProfileService implements IProfileService {
	private final String updateUserUri = "profile/save";
	private final String userGuideUri = "profile/guide";

	@Override
	public void updateUser(User user, Context context) throws ProfileException {
		save(user, context, updateUserUri);
	}

	@Override
	public void guide(User user, Context context) throws ProfileException {
		save(user, context, userGuideUri);
		MobclickAgent.onEvent(context, UmengEvent.USER_GUIDE);
	}

	private void save(User user, Context context, String url)
			throws ProfileException {
		ResponseEntity<UserResult> responseEntity = null;
		try {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("nickname", user.getNickname());
			values.put("gender", user.getGender());
			if (StringUtils.isNotEmpty(user.getFeature())) {
				values.put("feature", user.getFeature());
			}
			values.put("professionId", user.getProfessionId());
			values.put("profession", user.getProfession());
			values.put("cityId", user.getCityId());
			values.put("birth",
					user.getBirthYear() + "-" + user.getBirthMonth() + "-"
							+ user.getBirthDay());
			responseEntity = HttpUtils.uploadFile(context, url, values,
					UserCache.getUserStatus(), "logo", user.getLogoImage(),
					UserResult.class);
		} catch (NeedLoginException e) {
			throw new ProfileException(context, R.string.login_status_error, e);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "save " + url + "  is error",
						e);
			}
			throw new ProfileException(context, R.string.system_internet_erorr,
					e);
		}
		if (responseEntity == null || responseEntity.getBody() == null) {
			throw new ProfileException(context, R.string.system_internet_erorr);
		}

		if (!responseEntity.getBody().getSuccess()
				|| responseEntity.getBody().getResult() == null) {
			throw new ProfileException(context, responseEntity.getBody()
					.getErrorInfo());
		} else {
			UserCacheManager.updateUserCache(responseEntity.getBody()
					.getResult());
			if (user.getLogoImage() != null) {
				MobclickAgent.onEvent(context, UmengEvent.UPLOAD_LOGO);
			}
		}
	}

}

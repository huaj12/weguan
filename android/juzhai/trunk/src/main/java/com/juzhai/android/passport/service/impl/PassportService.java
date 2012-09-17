package com.juzhai.android.passport.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.content.SharedPreferences;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.bean.UserCacheManager;
import com.juzhai.android.passport.model.UserResults;
import com.juzhai.android.passport.service.IPassportService;

public class PassportService implements IPassportService {

	private static final String loginUri = "passport/login";

	@Override
	public boolean checkLogin(Context context) {
		// TODO (review) 有bug，记不住登录状态
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"juzhai-android", Context.MODE_PRIVATE);
		String p_token = null;
		// TODO (review) 此处contains方法是否多余
		if (sharedPreferences.contains("p_token")) {
			p_token = sharedPreferences.getString("p_token", null);
		}
		if (StringUtils.isEmpty(p_token)) {
			return false;
		} else {
			// 有记录登录状态直接登录
			Map<String, String> cookies = new HashMap<String, String>();
			cookies.put("p_token", p_token);
			ResponseEntity<UserResults> responseEntity = null;
			try {
				responseEntity = HttpUtils.post(loginUri, null, cookies,
						UserResults.class);
			} catch (Exception e) {
				DialogUtils.showToastText(context,
						R.string.system_internet_erorr);
				// 登录失败跳转到登录页面
				return false;
			}
			UserCacheManager.initUserCacheManager(responseEntity, context);
			return true;
		}
	}

}

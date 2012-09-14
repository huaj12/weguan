package com.juzhai.android.passport.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.content.SharedPreferences;

import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.UserResults;

public class UserCacheManager {
	public static void initUserCacheManager(
			ResponseEntity<UserResults> responseEntity, Context context) {
		UserCache.setUserInfo(responseEntity.getBody().getResult());
		List<String> list = responseEntity.getHeaders().get("Set-Cookie");
		Map<String, String> cookies = new HashMap<String, String>();
		for (String str : list) {
			String[] cookiesStr = str.split(";");
			for (String cookieStr : cookiesStr) {
				String[] values = cookieStr.split("=");
				if (values != null && values.length > 1) {
					cookies.put(values[0], values[1]);
				}
			}
		}
		UserCache.setlToken(cookies.get("l_token"));
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"juzhai-android", context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("p_token", cookies.get("p_token"));
		editor.commit();
	}
}

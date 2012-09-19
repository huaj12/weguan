package com.juzhai.android.passport.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import android.content.Context;
import android.content.SharedPreferences;

import com.juzhai.android.passport.model.User;
import com.juzhai.android.passport.model.UserResults;

public class UserCacheManager {

	private final static String SHARED_PREFERENCES_NAME = "juzhai-android";

	public static void cache(Context context,
			ResponseEntity<UserResults> responseEntity) {
		UserCache.setUserInfo(responseEntity.getBody().getResult());
		Map<String, String> cookies = parseCookies(responseEntity.getHeaders()
				.get("Set-Cookie"));
		String lToken = cookies.get("l_token");
		if (StringUtils.hasText(lToken))
			UserCache.setlToken(lToken);
		String pToken = cookies.get("p_token");
		if (StringUtils.hasText(pToken))
			UserCache.setpToken(pToken);
	}

	public static void updateUserCache(User user) {
		UserCache.setUserInfo(user);
	}

	public static void persistToken(Context context,
			ResponseEntity<UserResults> responseEntity) {
		Map<String, String> cookies = parseCookies(responseEntity.getHeaders()
				.get("Set-Cookie"));
		String pToken = cookies.get("p_token");

		// TODO (review) 今后如果有其他信息需要持久化，下面持久化的操作需要封装出来
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("p_token", pToken);
		editor.commit();
	}

	private static Map<String, String> parseCookies(List<String> list) {
		Map<String, String> cookies = new HashMap<String, String>();
		if (null != list) {
			for (String str : list) {
				String[] cookiesStr = str.split(";");
				for (String cookieStr : cookiesStr) {
					String[] values = cookieStr.split("=");
					if (values != null && values.length > 1) {
						cookies.put(values[0], values[1]);
					}
				}
			}
		}
		return cookies;
	}
}

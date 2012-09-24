package com.juzhai.android.passport.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import android.content.Context;

import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.passport.model.User;

public class UserCacheManager {

	public static void cache(Context context,
			ResponseEntity<UserResult> responseEntity) {
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
			ResponseEntity<UserResult> responseEntity) {
		Map<String, String> cookies = parseCookies(responseEntity.getHeaders()
				.get("Set-Cookie"));
		String pToken = cookies.get("p_token");
		new SharedPreferencesManager(context).commit("p_token", pToken);
	}

	public static String getPersistToken(Context context) {
		return new SharedPreferencesManager(context).getString("p_token");
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

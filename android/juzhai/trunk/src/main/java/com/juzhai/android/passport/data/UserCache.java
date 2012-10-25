package com.juzhai.android.passport.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.juzhai.android.passport.model.User;

public class UserCache {
	private static User userInfo;
	private static String lToken;
	private static String pToken;

	static void setUserInfo(User user) {
		userInfo = user;
	}

	public static User getUserInfo() {
		return userInfo;
	}

	public static String getlToken() {
		return lToken;
	}

	static void setlToken(String lToken) {
		UserCache.lToken = lToken;
	}

	public static String getpToken() {
		return pToken;
	}

	static void setpToken(String pToken) {
		UserCache.pToken = pToken;
	}

	public static Map<String, String> getUserStatus() {
		Map<String, String> cookies = new HashMap<String, String>();

		if (org.apache.commons.lang.StringUtils.isNotEmpty(UserCache
				.getlToken()) && StringUtils.hasText(UserCache.getlToken())) {
			cookies.put("l_token", UserCache.getlToken());
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(UserCache
				.getpToken()) && StringUtils.hasText(UserCache.getpToken())) {
			cookies.put("p_token", UserCache.getpToken());
		}
		return cookies;
	}

	public static long getUid() {
		return userInfo.getUid();
	}

	static void clear() {
		userInfo = null;
		lToken = null;
		pToken = null;
	}

	public static boolean hasLogin() {
		if (null != userInfo && userInfo.getUid() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static User getCopyUserInfo() {
		return userInfo.clone();
	}
}

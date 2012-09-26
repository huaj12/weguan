package com.juzhai.android.passport.data;

import java.util.HashMap;
import java.util.Map;

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
		cookies.put("l_token", UserCache.getlToken());
		cookies.put("p_token", UserCache.getpToken());
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
}

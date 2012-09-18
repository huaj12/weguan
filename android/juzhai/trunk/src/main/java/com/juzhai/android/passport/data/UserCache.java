package com.juzhai.android.passport.data;

import java.util.HashMap;
import java.util.Map;

import com.juzhai.android.passport.model.User;

public class UserCache {
	private static User userInfo;
	private static String lToken;
	private static String pToken;

	public static void setUserInfo(User user) {
		userInfo = user;
	}

	public static User getUserInfo() {
		return userInfo;
	}

	public static String getlToken() {
		return lToken;
	}

	public static void setlToken(String lToken) {
		UserCache.lToken = lToken;
	}

	public static String getpToken() {
		return pToken;
	}

	public static void setpToken(String pToken) {
		UserCache.pToken = pToken;
	}

	public static Map<String, String> getUserStatus() {
		Map<String, String> cookies = new HashMap<String, String>();
		cookies.put("l_token", UserCache.getlToken());
		cookies.put("p_token", UserCache.getpToken());
		return cookies;
	}

}

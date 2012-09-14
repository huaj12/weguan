package com.juzhai.android.passport.data;

import com.juzhai.android.passport.model.User;

public class UserCache {
	private static User userInfo;
	private static String lToken;

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

}

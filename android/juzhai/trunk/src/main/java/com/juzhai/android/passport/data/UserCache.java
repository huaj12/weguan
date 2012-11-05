package com.juzhai.android.passport.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.juzhai.android.passport.model.User;

public class UserCache {
	private User userInfo;
	private String lToken;
	private String pToken;

	void setUserInfo(User user) {
		userInfo = user;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public String getlToken() {
		return lToken;
	}

	void setlToken(String lToken) {
		this.lToken = lToken;
	}

	public String getpToken() {
		return pToken;
	}

	void setpToken(String pToken) {
		this.pToken = pToken;
	}

	public Map<String, String> getUserStatus() {
		Map<String, String> cookies = new HashMap<String, String>();

		if (StringUtils.hasText(getlToken())) {
			cookies.put("l_token", getlToken());
		}
		if (StringUtils.hasText(getpToken())) {
			cookies.put("p_token", getpToken());
		}
		return cookies;
	}

	public long getUid() {
		return userInfo.getUid();
	}

	void clear() {
		userInfo = null;
		lToken = null;
		pToken = null;
	}

	public boolean hasLogin() {
		if (null != userInfo && userInfo.getUid() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public User getCopyUserInfo() {
		return userInfo.clone();
	}
}

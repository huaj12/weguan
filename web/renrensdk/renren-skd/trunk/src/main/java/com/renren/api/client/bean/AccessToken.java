package com.renren.api.client.bean;

import org.json.simple.JSONObject;

public class AccessToken {

	private String accessToken;
	private Long expireIn;
	private String refreshToken;

	public AccessToken(JSONObject tokenJson) {
		this.accessToken = (String) tokenJson.get("access_token");
		this.expireIn = (Long) tokenJson.get("expires_in");// 距离过期时的时间段（秒数）
		this.refreshToken = (String) tokenJson.get("refresh_token");
	}

	public String getAccessToken() {
		return accessToken;
	}

	public Long getExpireIn() {
		return expireIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

}

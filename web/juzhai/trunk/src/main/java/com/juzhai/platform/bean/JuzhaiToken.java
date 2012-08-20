package com.juzhai.platform.bean;

public class JuzhaiToken {
	private String accessToken;
	private String expireIn;
	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(String expireIn) {
		this.expireIn = expireIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}

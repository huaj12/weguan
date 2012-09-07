/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.bean;

import java.io.Serializable;

import org.codehaus.jackson.JsonGenerationException;

import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.passport.model.Thirdparty;

/**
 * @author wujiajun Created on 2011-2-15
 */
public class AuthInfo implements Serializable {

	private static final long serialVersionUID = -2477174021935374878L;

	private String tpIdentity;
	private String thirdpartyName;
	private String joinType;
	private String appKey;
	private String appSecret;
	private String token;
	private String tokenSecret;
	private String sessionKey;
	private String authorizeUrl;
	private long expiresTime;

	public AuthInfo() {
		super();
	}

	public AuthInfo(String thirdpartyName, String joinType, String appKey,
			String appSecret, String token, String tokenSecret) {
		super();
		this.thirdpartyName = thirdpartyName;
		this.joinType = joinType;
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.token = token;
		this.tokenSecret = tokenSecret;
	}

	public AuthInfo(Thirdparty thirdparty, String token, String tokenSecret) {
		this(thirdparty.getName(), thirdparty.getJoinType(), thirdparty
				.getAppKey(), thirdparty.getAppSecret(), token, tokenSecret);
	}

	public AuthInfo(String thirdpartyName, String joinType, String appKey,
			String appSecret, String sessionKey) {
		super();
		this.thirdpartyName = thirdpartyName;
		this.joinType = joinType;
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.sessionKey = sessionKey;
	}

	public AuthInfo(Thirdparty thirdparty, String sessionKey) {
		this(thirdparty.getName(), thirdparty.getJoinType(), thirdparty
				.getAppKey(), thirdparty.getAppSecret(), sessionKey);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getThirdpartyName() {
		return thirdpartyName;
	}

	public void setThirdpartyName(String thirdpartyName) {
		this.thirdpartyName = thirdpartyName;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getTpIdentity() {
		return tpIdentity;
	}

	public void setTpIdentity(String tpIdentity) {
		this.tpIdentity = tpIdentity;
	}

	public long getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}

	public void setThirdparty(Thirdparty tp) {
		this.thirdpartyName = tp.getName();
		this.joinType = tp.getJoinType();
		this.appKey = tp.getAppKey();
		this.appSecret = tp.getAppSecret();
	}

	public String toJsonString() throws JsonGenerationException {
		return JackSonSerializer.toString(this);
	}

	public static AuthInfo convertToBean(String jsonAsString)
			throws JsonGenerationException {
		return JackSonSerializer.toBean(jsonAsString, AuthInfo.class);
	}
}

package com.weguan.passport.core.web;

import javax.servlet.http.HttpServletRequest;

import com.weguan.passport.core.web.util.HttpRequestUtil;

/**
 * 适用于manager层的用户上下文环境。可用于模拟用户的登录状态、用户当前的session ID、用户当前的IP地址。
 * 
 * @author Topbit 2006-3-23 19:04:25
 */
public class UserContext {

	public static final String UID_ATTRIBUTE_NAME = "uid";

	private long uid = 0L;

	private String remoteAddress = null;

	private String sessionId = null;

	private String userAgentPermanentCode = null;

	private UserContext() {
	}

	public static UserContext getUserContext(HttpServletRequest request) {
		UserContext userContext = new UserContext();
		userContext.update(request);
		return userContext;
	}

	public void update(HttpServletRequest request) {
		this.uid = HttpRequestUtil.getSessionAttributeAsLong(request,
				UID_ATTRIBUTE_NAME, 0L);
		this.sessionId = HttpRequestUtil.getSessionId(request);
		this.remoteAddress = HttpRequestUtil.getRemoteAddress(request);
		// this.userAgentPermanentCode = request.getUserAgentPermanentCode();
	}

	/**
	 * 判断用户是否已登录。
	 * 
	 * @return <code>true</code>，用户已登录；<code>false</code>，用户未登录。
	 */
	public boolean hasLogin() {
		return 0L != uid;
	}

	/**
	 * 获取用户的POIN。0表示未登录。
	 * 
	 * @return 用户的POIN。
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * 获取用户的远程IP地址。
	 * 
	 * @return 远程IP地址。
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * 获取用户当前的session ID。
	 * 
	 * @return 当前的session ID。
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * 获取用户的cookie
	 * 
	 * @return 用户的cookie
	 */
	public String getUserAgentPermanentCode() {
		return userAgentPermanentCode;
	}

}
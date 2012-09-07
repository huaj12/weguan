package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginSessionManager {

	/**
	 * 从request里获取UserContext
	 * 
	 * @param request
	 * @return
	 */
	UserContext getUserContextAndUpdateExpire(HttpServletRequest request);

	// UserContext getUserContext(HttpServletRequest request, Long uid,
	// String sessionId, Long tpId, Boolean admin);

	// void updateLoginExpire(HttpServletRequest request);

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param uid
	 * @param tpId
	 * @param isAdmin
	 * @param persistent
	 */
	void login(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId, boolean isAdmin, boolean persistent);

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 */
	void logout(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 获取记住登录状态的uid
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	long persistentLoginUid(HttpServletRequest request,
			HttpServletResponse response);

	// TODO 临时方法。绑定多tpid时 需从memcached里移除tpid
	void updateTpId(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId);
}

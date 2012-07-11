package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginSessionManager {

	UserContext getUserContext(HttpServletRequest request);

	UserContext getUserContext(HttpServletRequest request, Long uid,
			String sessionId, Long tpId, Boolean admin);

	void updateLoginExpire(HttpServletRequest request);

	void login(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId, boolean isAdmin, boolean persistent);

	void logout(HttpServletRequest request, HttpServletResponse response);

	long persistentLoginUid(HttpServletRequest request,
			HttpServletResponse response);
}

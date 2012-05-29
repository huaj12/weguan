package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginSessionManager {

	UserContext getUserContext(HttpServletRequest request);

	void updateLoginExpire(HttpServletRequest request);

	void login(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId, boolean isAdmin, boolean persistent);

	void logout(HttpServletRequest request, HttpServletResponse response);

	long persistentLoginUid(HttpServletRequest request,
			HttpServletResponse response);
}

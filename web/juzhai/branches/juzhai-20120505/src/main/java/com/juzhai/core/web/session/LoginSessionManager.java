package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginSessionManager {

	public UserContext getUserContext(HttpServletRequest request);

	public void updateLoginExpire(HttpServletRequest request);

	public void login(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId, boolean isAdmin, boolean persistent);

	public void logout(HttpServletRequest request, HttpServletResponse response);

	public boolean autoLogin(HttpServletRequest request,
			HttpServletResponse response);
}

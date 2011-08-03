package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;

public interface LoginSessionManager {

	public UserContext getUserContext(HttpServletRequest request);

	public void updateLoginExpire(UserContext userContext);

	public void login(HttpServletRequest request, long uid);

	public void logout(HttpServletRequest request);
}

package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.juzhai.core.web.util.HttpRequestUtil;

@Component(value = "loginSessionManager")
public class TomcatLoginSessionManager implements LoginSessionManager {

	public static final String UID_ATTRIBUTE_NAME = "uid";

	@Override
	public UserContext getUserContext(HttpServletRequest request) {
		long uid = HttpRequestUtil.getSessionAttributeAsLong(request,
				UID_ATTRIBUTE_NAME, 0L);
		String sessionId = HttpRequestUtil.getSessionId(request);
		String remoteAddress = HttpRequestUtil.getRemoteAddress(request);
		// this.userAgentPermanentCode = request.getUserAgentPermanentCode();
		return new UserContext(uid, remoteAddress, sessionId, null);
	}

	@Override
	public void updateLoginExpire(UserContext userContext) {
		// TODO Auto-generated method stub

	}

	public void login(HttpServletRequest request, long uid) {
		HttpRequestUtil.setSessionAttribute(request, UID_ATTRIBUTE_NAME, uid);
	}

	public void logout(HttpServletRequest request) {
		HttpRequestUtil.removeSessionAttribute(request, UID_ATTRIBUTE_NAME);
	}
}

package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.juzhai.core.web.util.HttpRequestUtil;

@Component(value = "loginSessionManager")
public class TomcatLoginSessionManager implements LoginSessionManager {

	public static final String UID_ATTRIBUTE_NAME = "uid";
	public static final String TPID_ATTRIBUTE_NAME = "tpId";

	@Override
	public UserContext getUserContext(HttpServletRequest request) {
		long uid = HttpRequestUtil.getSessionAttributeAsLong(request,
				UID_ATTRIBUTE_NAME, 0L);
		String sessionId = HttpRequestUtil.getSessionId(request);
		String remoteAddress = HttpRequestUtil.getRemoteAddress(request);
		long tpId = HttpRequestUtil.getSessionAttributeAsLong(request,
				TPID_ATTRIBUTE_NAME, 0L);
		// this.userAgentPermanentCode = request.getUserAgentPermanentCode();
		return new UserContext(uid, remoteAddress, sessionId, null, tpId);
	}

	@Override
	public void updateLoginExpire(UserContext userContext) {
		// handled by tomcat session.
	}

	public void login(HttpServletRequest request, long uid, long tpId) {
		HttpRequestUtil.setSessionAttribute(request, UID_ATTRIBUTE_NAME, uid);
		HttpRequestUtil.setSessionAttribute(request, TPID_ATTRIBUTE_NAME, tpId);
		// 不过期
		HttpRequestUtil.setMaxInactiveInterval(request, -1);
	}

	public void logout(HttpServletRequest request) {
		HttpRequestUtil.removeSessionAttribute(request, UID_ATTRIBUTE_NAME);
		HttpRequestUtil.removeSessionAttribute(request, TPID_ATTRIBUTE_NAME);
	}
}

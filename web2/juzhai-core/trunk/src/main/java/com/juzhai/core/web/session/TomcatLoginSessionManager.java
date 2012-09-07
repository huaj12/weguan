package com.juzhai.core.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.core.web.util.HttpRequestUtil;

//@Component(value = "loginSessionManager")
public class TomcatLoginSessionManager extends AbstractLoginSessionManager {

	@Override
	public UserContext getUserContextAndUpdateExpire(HttpServletRequest request) {
		long uid = HttpRequestUtil.getSessionAttributeAsLong(request,
				UID_ATTRIBUTE_NAME, 0L);
		String sessionId = HttpRequestUtil.getSessionId(request);
		String remoteAddress = HttpRequestUtil.getRemoteIp(request);
		long tpId = HttpRequestUtil.getSessionAttributeAsLong(request,
				TPID_ATTRIBUTE_NAME, 0L);
		boolean admin = HttpRequestUtil.getSessionAttributeAsBoolean(request,
				ADMIN_ATTRIBUTE_NAME, false);
		String userAgentPermanentCode = request.getHeader("User-Agent");
		return new UserContext(uid, remoteAddress, sessionId,
				userAgentPermanentCode, tpId, admin);
	}

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId, boolean isAdmin, boolean persistent) {
		HttpRequestUtil.setSessionAttribute(request, UID_ATTRIBUTE_NAME, uid);
		HttpRequestUtil.setSessionAttribute(request, TPID_ATTRIBUTE_NAME, tpId);
		HttpRequestUtil.setSessionAttribute(request, ADMIN_ATTRIBUTE_NAME,
				isAdmin);
		HttpRequestUtil.setMaxInactiveInterval(request, loginExpireTimeSeconds);
		if (persistent) {
			savePersistLoginToken(request, response, uid);
		}
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpRequestUtil.removeSessionAttribute(request, UID_ATTRIBUTE_NAME);
		HttpRequestUtil.removeSessionAttribute(request, TPID_ATTRIBUTE_NAME);
		HttpRequestUtil.removeSessionAttribute(request, ADMIN_ATTRIBUTE_NAME);
		delPersistLogin(request, response);
	}

	@Override
	public void updateTpId(HttpServletRequest request,
			HttpServletResponse response, long uid, long tpId) {

	}
}

/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.core.web.util.HttpRequestUtil;

@Service
public class TomcatLoginService extends AbstractLoginService {

	@Autowired
	private LoginSessionManager loginSessionManager;

	@Override
	protected void doLogin(HttpServletRequest request, long uid, long tpId,
			boolean admin) {
		loginSessionManager.login(request, uid, tpId, admin);
	}

	@Override
	public boolean isDayFirstLoginAndDel(HttpServletRequest request) {
		boolean dfl = HttpRequestUtil.getSessionAttributeAsBoolean(request,
				DAY_FIRST_LOGIN, false);
		if (dfl) {
			HttpRequestUtil.removeSessionAttribute(request, DAY_FIRST_LOGIN);
		}
		return dfl;
	}

}

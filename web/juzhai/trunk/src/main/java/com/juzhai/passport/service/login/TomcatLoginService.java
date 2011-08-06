/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.web.session.LoginSessionManager;

@Service
public class TomcatLoginService extends AbstractLoginService {

	@Autowired
	private LoginSessionManager loginSessionManager;

	@Override
	protected void doLogin(HttpServletRequest request, long uid, long tpId) {
		loginSessionManager.login(request, uid, tpId);
	}

}

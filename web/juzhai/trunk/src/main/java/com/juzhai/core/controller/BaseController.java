package com.juzhai.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.core.web.session.UserContext;

public class BaseController {

	@Autowired
	private LoginSessionManager loginSessionManager;

	protected UserContext checkLoginReturnContext(HttpServletRequest request)
			throws NeedLoginException {
		UserContext context = loginSessionManager.getUserContext(request);
		if (!context.hasLogin()) {
			throw new NeedLoginException();
		}
		return context;
	}
}

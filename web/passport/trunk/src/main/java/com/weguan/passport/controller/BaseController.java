package com.weguan.passport.controller;

import javax.servlet.http.HttpServletRequest;

import com.weguan.passport.core.web.UserContext;
import com.weguan.passport.exception.NeedLoginException;

public class BaseController {

	protected UserContext checkLoginReturnContext(HttpServletRequest request)
			throws NeedLoginException {
		UserContext context = UserContext.getUserContext(request);
		if (!context.hasLogin()) {
			throw new NeedLoginException();
		}
		return context;
	}
}

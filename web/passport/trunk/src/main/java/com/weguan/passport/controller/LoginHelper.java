package com.weguan.passport.controller;

import javax.servlet.http.HttpServletRequest;

import com.weguan.passport.core.web.UserContext;
import com.weguan.passport.core.web.util.HttpRequestUtil;

public class LoginHelper {

	public static void login(HttpServletRequest request, long uid) {
		HttpRequestUtil.setSessionAttribute(request,
				UserContext.UID_ATTRIBUTE_NAME, uid);
	}
}

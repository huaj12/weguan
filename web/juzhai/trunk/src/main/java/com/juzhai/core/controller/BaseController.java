package com.juzhai.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.juzhai.account.service.IAccountService;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.ErrorPageDispatcher;
import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;

public class BaseController {

	protected final Log log = LogFactory.getLog(getClass());

	protected final String error_404 = ErrorPageDispatcher.ERROR_404;
	protected final String error_500 = ErrorPageDispatcher.ERROR_500;

	@Autowired
	private LoginSessionManager loginSessionManager;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IProfileService profileService;

	protected UserContext checkLoginForApp(HttpServletRequest request)
			throws NeedLoginException {
		return checkLogin(request, NeedLoginException.RunType.APP);
	}

	private UserContext checkLogin(HttpServletRequest request,
			NeedLoginException.RunType runType) throws NeedLoginException {
		UserContext context = loginSessionManager.getUserContext(request);
		if (!context.hasLogin()) {
			throw new NeedLoginException(runType);
		}
		return context;
	}

	protected void queryPoint(long uid, Model model) {
		model.addAttribute("point", accountService.queryPoint(uid));
	}

	protected ProfileCache queryProfile(long uid, Model model) {
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (null != model) {
			model.addAttribute("profile", profileCache);
		}
		return profileCache;
	}
}

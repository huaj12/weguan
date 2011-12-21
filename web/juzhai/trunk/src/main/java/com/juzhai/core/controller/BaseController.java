package com.juzhai.core.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;

import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.ErrorPageDispatcher;
import com.juzhai.core.web.filter.CityChannelFilter;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;

public class BaseController {

	protected final Log log = LogFactory.getLog(getClass());

	protected final String error_404 = ErrorPageDispatcher.ERROR_404;
	protected final String error_500 = ErrorPageDispatcher.ERROR_500;

	private static final String MYACT_PAGE_TIPS = "myAct.page.tips";
	private static final String DAY_FIRST_LOGIN_TIP = "day.first.login.tip";
	private static final String NOT_SUB_EMAIL_TIP = "not.sub.email.tip";

	// @Autowired
	// private LoginSessionManager loginSessionManager;
	// @Autowired
	// private IAccountService accountService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;

	protected UserContext checkLoginForApp(HttpServletRequest request)
			throws NeedLoginException {
		return checkLogin(request, NeedLoginException.RunType.APP);
	}

	protected UserContext checkLoginForWeb(HttpServletRequest request)
			throws NeedLoginException {
		return checkLogin(request, NeedLoginException.RunType.WEB);
	}

	private UserContext checkLogin(HttpServletRequest request,
			NeedLoginException.RunType runType) throws NeedLoginException {
		// UserContext context = loginSessionManager.getUserContext(request);
		UserContext context = (UserContext) request.getAttribute("context");
		if (context == null || !context.hasLogin()) {
			throw new NeedLoginException(runType);
		}
		return context;
	}

	// protected void queryPoint(long uid, Model model) {
	// model.addAttribute("point", accountService.queryPoint(uid));
	// }

	public AuthInfo getAuthInfo(long uid, long tpId) {
		return tpUserAuthService.getAuthInfo(uid, tpId);
	}

	protected ProfileCache queryProfile(long uid, Model model) {
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (null != model) {
			model.addAttribute("profile", profileCache);
		}
		return profileCache;
	}

	protected void showMyActTips(Model model) {
		fetchTips(model, MYACT_PAGE_TIPS);
	}

	protected void showDayFirstLoginTip(Model model) {
		fetchTips(model, DAY_FIRST_LOGIN_TIP);
	}

	protected void showNotSubEmailTip(Model model) {
		fetchTips(model, NOT_SUB_EMAIL_TIP);
	}

	private void fetchTips(Model model, String tipsKey) {
		String tipStrings = messageSource.getMessage(tipsKey, null,
				Locale.SIMPLIFIED_CHINESE);
		if (StringUtils.isNotEmpty(tipStrings)) {
			String[] tips = tipStrings.split("\\|");
			model.addAttribute("tips", tips);
		}
	}

	protected long fetchCityId(HttpServletRequest request) {
		return HttpRequestUtil.getSessionAttributeAsLong(request,
				CityChannelFilter.SESSION_CHANNEL_NAME, 0L);
	}
}

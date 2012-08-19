package com.juzhai.mobile.passport.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.DeviceName;
import com.juzhai.passport.controller.form.LoginForm;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ReportAccountException;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.IUserService;

@Controller
@RequestMapping("m/passport")
public class LoginMController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IUserService userService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private IUserMViewHelper userMViewHelper;

	@RequestMapping(value = "/tpLogin/{tpId}")
	public String webLogin(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId)
			throws UnsupportedEncodingException {
		Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return error_404;
		}
		String url = userService.getAuthorizeURLforCode(request, response, tp,
				Terminal.MOBILE, null, null, tp.getAppUrl());
		if (StringUtils.isEmpty(url)) {
			return error_404;
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/tpAccess/{tpId}")
	@ResponseBody
	public AjaxResult webAccess(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId,
			String turnTo, String incode, String error_code, Model model)
			throws UnsupportedEncodingException, MalformedURLException,
			ReportAccountException {
		AjaxResult result = new AjaxResult();
		Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
		if (null == tp) {
			result.setError(JuzhaiException.ILLEGAL_OPERATION, messageSource);
			return result;
		}
		if (StringUtils.isNotEmpty(error_code)) {
			result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
			return result;
		}
		long uid = 0;
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			uid = context.getUid();
		} else {
			if (null != tp) {
				if (log.isDebugEnabled()) {
					log.debug("thirdparty access [tpName=" + tp.getName()
							+ ", joinType=" + tp.getJoinType() + "]");
				}
				AuthInfo authInfo = null;
				uid = userService.access(request, response, authInfo, tp,
						decryptInviteUid(incode), DeviceName.IPHONE);
			}
			if (uid <= 0) {
				log.error("access failed.[tpName=" + tp.getName()
						+ ", joinType=" + tp.getJoinType() + "].");
				result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
				return result;
			}
			try {
				loginService.login(request, response, uid, tp.getId(),
						RunType.CONNET, true);
				context = (UserContext) request.getAttribute("context");

			} catch (PassportAccountException e) {
				result.setError(e.getErrorCode(), messageSource);
				return result;
			}
		}
		// Profile profile = profileService.getProfile(uid);
		result.setResult(userMViewHelper.createUserMView(context,
				profileService.getProfileCacheByUid(uid),
				userGuideService.isCompleteGuide(uid)));
		return result;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult login(HttpServletRequest request,
			HttpServletResponse response, LoginForm loginForm, String token)
			throws ReportAccountException {
		AjaxResult result = new AjaxResult();
		UserContext context = (UserContext) request.getAttribute("context");
		long uid = 0L;
		if (!context.hasLogin()) {
			try {

				uid = loginService.login(request, response,
						loginForm.getAccount(), loginForm.getPassword(),
						loginForm.isRemember());
				context = (UserContext) request.getAttribute("context");
			} catch (PassportAccountException e) {
				result.setError(e.getErrorCode(), messageSource);
				return result;
			}
			if (uid <= 0) {
				result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
			}
		} else {
			uid = context.getUid();
		}
		// Profile profile = profileService.getProfile(uid);
		result.setResult(userMViewHelper.createUserMView(context,
				profileService.getProfileCacheByUid(uid),
				userGuideService.isCompleteGuide(uid)));
		return result;
	}

	@RequestMapping(value = "/logout")
	@ResponseBody
	public AjaxResult logout(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxResult result = new AjaxResult();
		try {
			UserContext context = checkLoginForWeb(request);
			loginService.logout(request, response, context.getUid());
		} catch (NeedLoginException e) {
		}
		return result;
	}
}

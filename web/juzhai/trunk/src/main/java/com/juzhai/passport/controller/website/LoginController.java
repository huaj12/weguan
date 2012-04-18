/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.controller.website;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.controller.form.LoginForm;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.verifycode.service.IVerifyCodeService;

/**
 * @author wujiajun Created on 2011-2-15
 */
@Controller
public class LoginController extends BaseController {

	private static final Log log = LogFactory.getLog(LoginController.class);
	@Autowired
	private ILoginService loginService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private IVerifyCodeService verifyCodeService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model,
			LoginForm loginForm) throws UnsupportedEncodingException {
		try {
			checkLoginForWeb(request);
			return "redirect:/home";
		} catch (NeedLoginException e) {
			// 判断是否需要
			if (loginService
					.useVerifyCode(HttpRequestUtil.getRemoteIp(request))) {
				String key = verifyCodeService.getVerifyCodeKey();
				loginForm.setVerifyKey(key);
				model.addAttribute("t", System.currentTimeMillis());
			}
			model.addAttribute("loginForm", loginForm);
			return "web/login/login";
		}
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String postLogin(HttpServletRequest request, Model mdoel,
			LoginForm loginForm, Model model) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			return "redirect:/home";
		}
		String ip = HttpRequestUtil.getRemoteIp(request);
		boolean needVerify = loginService.useVerifyCode(ip);
		long uid = 0L;
		try {
			// 增加登录次数
			loginService.incrLoginCount(ip);
			if (StringUtils.isNotEmpty(loginForm.getVerifyKey()) || needVerify) {
				if (!verifyCodeService.verify(loginForm.getVerifyKey(),
						loginForm.getVerifyCode())) {
					throw new PassportAccountException(
							PassportAccountException.VERIFY_CODE_ERROR);
				}
			}

			uid = loginService.login(request, loginForm.getAccount(),
					loginForm.getPassword());
		} catch (PassportAccountException e) {
			if (StringUtils.equals(e.getErrorCode(),
					PassportAccountException.USER_IS_SHIELD)) {
				model.addAttribute("shieldTime", new Date(e.getShieldTime()));
				return "web/login/login_error";
			}
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));

			if (loginService.useVerifyCode(ip)) {
				String key = verifyCodeService.getVerifyCodeKey();
				loginForm.setVerifyKey(key);
				model.addAttribute("t", System.currentTimeMillis());
			}
			model.addAttribute("loginForm", loginForm);
			return "web/login/login";
		}
		if (uid <= 0) {
			return error_500;
		}
		if (!userGuideService.isCompleteGuide(uid)) {
			return "redirect:/home/guide";
		} else if (StringUtils.isNotEmpty(loginForm.getTurnTo())) {
			return "redirect:" + loginForm.getTurnTo();
		} else {
			return "redirect:/home";
		}
	}

	@RequestMapping(value = "ajaxlogin", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult ajaxLogin(HttpServletRequest request,
			LoginForm loginForm, Model model) {
		AjaxResult result = new AjaxResult();
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			result.setResult("/home");
			return result;
		}
		long uid = 0L;
		try {
			uid = loginService.login(request, loginForm.getAccount(),
					loginForm.getPassword());
		} catch (PassportAccountException e) {
			if (StringUtils.equals(e.getErrorCode(),
					PassportAccountException.USER_IS_SHIELD)) {
				result.setError(PassportAccountException.USER_IS_SHIELD,
						messageSource,
						DateFormat.SDF.format(new Date(e.getShieldTime())));
				return result;
			}
			result.setError(e.getErrorCode(), messageSource);
			return result;
		}
		if (uid <= 0) {
			result.setError(JuzhaiException.ILLEGAL_OPERATION, messageSource);
		} else if (!userGuideService.isCompleteGuide(uid)) {
			result.setResult("/home/guide");
		} else if (StringUtils.isNotEmpty(loginForm.getTurnTo())) {
			result.setResult(loginForm.getTurnTo());
		} else {
			result.setResult("/home");
		}
		return result;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String webLogout(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		loginService.logout(request, context.getUid());
		return "redirect:/login";
	}
}

package com.weguan.passport.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.weguan.passport.core.SystemConfig;
import com.weguan.passport.core.web.UserContext;
import com.weguan.passport.exception.LoginException;
import com.weguan.passport.form.LoginForm;
import com.weguan.passport.model.Passport;
import com.weguan.passport.service.ILoginService;

@Controller
public class LoginController {

	public static final Log log = LogFactory.getLog(LoginController.class);

	@Autowired
	private ILoginService loginService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, LoginForm loginForm,
			Model model) {
		UserContext context = UserContext.getUserContext(request);
		if (context.hasLogin()) {
			return "redirect:" + SystemConfig.BASIC_DOMAIN;
		}
		if (StringUtils.isNotEmpty(loginForm.getRu())) {
			model.addAttribute("returnLink", loginForm.getRu());
		}
		return "PrepareLoginPage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object[] login(HttpServletRequest request,
			HttpServletResponse reponse, LoginForm loginForm, Model model) {
		UserContext context = UserContext.getUserContext(request);
		if (context.hasLogin()) {
			return _result(true, SystemConfig.BASIC_DOMAIN, null, null);
		}
		try {
			Passport passport = loginService.authenticate(
					loginForm.getLoginName(), loginForm.getLoginPassword());
			LoginHelper.login(request, passport.getId());
			return _result(
					true,
					StringUtils.isEmpty(loginForm.getRu()) ? SystemConfig.BASIC_DOMAIN
							: loginForm.getRu(), null, null);
		} catch (LoginException e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage(), e);
			}
			// 处理错误
			return _result(false, null, null, messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
		}
	}

	private Object[] _result(boolean isSuccess, String returnLink,
			String token, String errorMessage) {
		return new Object[] { isSuccess, returnLink, token, errorMessage };
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		LoginHelper.logout(request);
		return "redirect:" + SystemConfig.BASIC_DOMAIN;
	}
}

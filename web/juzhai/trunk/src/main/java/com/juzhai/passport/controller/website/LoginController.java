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

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.controller.form.LoginForm;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.service.ILoginService;

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

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model, String turnTo)
			throws UnsupportedEncodingException {
		try {
			checkLoginForWeb(request);
			return "redirect:/home";
		} catch (NeedLoginException e) {
			if (StringUtils.isNotEmpty(turnTo)) {
				model.addAttribute("turnTo", turnTo);
			}
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
		try {
			loginService.login(request, loginForm.getAccount(),
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
			model.addAttribute("acount", loginForm.getAccount());
			return "web/login/login";
		}
		if (StringUtils.isNotEmpty(loginForm.getTurnTo())) {
			return "redirect:" + loginForm.getTurnTo();
		} else {
			return "redirect:/home";
		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String webLogout(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		loginService.logout(request, context.getUid());
		return "redirect:/login";
	}
}

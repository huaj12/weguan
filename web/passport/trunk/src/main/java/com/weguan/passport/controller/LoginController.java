package com.weguan.passport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weguan.passport.exception.LoginException;
import com.weguan.passport.form.LoginForm;
import com.weguan.passport.model.Passport;
import com.weguan.passport.service.ILoginService;
import com.weguan.passport.web.UserContext;

@Controller
public class LoginController {

	public static final Log log = LogFactory.getLog(LoginController.class);

	@Autowired
	private ILoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, LoginForm loginForm,
			Model model) {
		UserContext context = UserContext.getUserContext(request);
		if (context.hasLogin()) {
			return "redirect:/";
		}
		if (StringUtils.isNotEmpty(loginForm.getRu())) {
			model.addAttribute("returnLink", loginForm.getRu());
		}
		return "PrepareLoginPage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request,
			HttpServletResponse reponse, LoginForm loginForm, Model model) {
		UserContext context = UserContext.getUserContext(request);
		if (context.hasLogin()) {
			return "redirect:/";
		}
		try {
			Passport passport = loginService.authenticate(
					loginForm.getLoginName(), loginForm.getLoginPassword());
		} catch (LoginException e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage(), e);
			}
			// 处理错误
		}

		return "PrepareLoginPage";
	}
}

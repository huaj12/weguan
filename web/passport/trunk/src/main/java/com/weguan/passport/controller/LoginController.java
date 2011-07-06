package com.weguan.passport.controller;

import java.awt.image.RescaleOp;

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
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public Object[] login(HttpServletRequest request,
			HttpServletResponse reponse, LoginForm loginForm, Model model) {
		UserContext context = UserContext.getUserContext(request);
		if (context.hasLogin()) {
			return result(true, "/", null, null);
		}
		try {
			Passport passport = loginService.authenticate(
					loginForm.getLoginName(), loginForm.getLoginPassword());
			LoginHelper.login(request, passport.getId());
			return result(true, StringUtils.isEmpty(loginForm.getRu()) ? "/"
					: loginForm.getRu(), null, null);
		} catch (LoginException e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage(), e);
			}
			// 处理错误
			return result(false, null, null, "");
		}
	}

	private Object[] result(boolean isSuccess, String returnLink, String token,
			String errorMessage) {
		return new Object[] { isSuccess, returnLink, token, errorMessage };
	}
}

package com.weguan.passport.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weguan.passport.exception.RegisterException;
import com.weguan.passport.form.RegisterForm;
import com.weguan.passport.service.IRegisterService;

@Controller
public class RegisterController {

	@Autowired
	private IRegisterService registerService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "register/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(HttpServletRequest request,
			HttpServletResponse response, RegisterForm registerForm, Model model) {
		try {
			long uid = registerService.register(registerForm);
			if (uid > 0) {
				LoginHelper.login(request, uid);
			}
			model.addAttribute("uid", uid);
		} catch (RegisterException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("error", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			model.addAttribute("registerForm", registerForm);
		}
		return "register/Register";
	}
}

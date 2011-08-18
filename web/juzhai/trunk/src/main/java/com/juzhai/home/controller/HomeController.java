package com.juzhai.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;

public class HomeController extends BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginReturnContext(request);

		return null;
	}

	@RequestMapping(value = "/ajax/inboxNext", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxNext(HttpServletRequest request, Model model) {
		return null;
	}

	public String ajaxDeal() {
		return null;
	}
}

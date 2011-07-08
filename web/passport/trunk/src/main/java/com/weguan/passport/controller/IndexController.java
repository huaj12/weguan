package com.weguan.passport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weguan.passport.core.web.UserContext;

@Controller
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		UserContext context = UserContext.getUserContext(request);
		request.setAttribute("loginedUserPoin", context.getUid());
		request.setAttribute("hasLogin", context.hasLogin());
		return "index/Index";
	}
}

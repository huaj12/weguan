package com.weguan.passport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weguan.passport.web.UserContext;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		UserContext context = UserContext.getUserContext(request);
		if (!context.hasLogin()) {
			return null;
		}
		long uid = context.getUid();
		request.setAttribute("loginedUserPoin", uid);
		return "home/HomePage";
	}
}

package com.juzhai.lab.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;

//@Controller
@RequestMapping(value = "/chat")
public class ChatController extends BaseController {

	@RequestMapping(value = { "/", "" })
	public String index(HttpServletRequest request) throws NeedLoginException {
		checkLoginForWeb(request);
		return "web/chat/index";
	}
}

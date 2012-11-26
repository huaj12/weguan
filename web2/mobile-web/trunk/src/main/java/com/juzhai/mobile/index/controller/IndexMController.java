package com.juzhai.mobile.index.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;

@Controller
public class IndexMController extends BaseController {

	@RequestMapping(value = "/about/{pageType}", method = RequestMethod.GET)
	public String aboutUs(HttpServletRequest request, Model model,
			@PathVariable String pageType) {
		return "mobile/index/about/" + pageType;
	}

}

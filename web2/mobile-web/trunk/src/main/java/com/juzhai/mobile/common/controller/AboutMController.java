package com.juzhai.mobile.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;

@Controller
@RequestMapping(value = "about")
public class AboutMController extends BaseController {

	@RequestMapping(value = "/{pageType}", method = RequestMethod.GET)
	public String aboutUs(HttpServletRequest request, Model model,
			@PathVariable String pageType) {
		return "mobile/common/about/" + pageType;
	}

}

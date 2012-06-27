package com.juzhai.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.service.IStaticVersionService;

@Controller
@RequestMapping("cms")
public class CmsStaticVersionController {
	@Autowired
	private IStaticVersionService staticVersionService;

	@ResponseBody
	@RequestMapping(value = "/static/updateVersion", method = RequestMethod.GET)
	public String updateVersion(HttpServletRequest request, Model model) {
		staticVersionService.updateVersion();
		return "success";
	}

}

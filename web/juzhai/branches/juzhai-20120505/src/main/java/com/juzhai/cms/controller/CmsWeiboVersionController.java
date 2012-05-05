package com.juzhai.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.platform.Version;

@Controller
@RequestMapping("/cms")
public class CmsWeiboVersionController {
	@RequestMapping(value = "/show/weibo/version", method = RequestMethod.GET)
	public String showWeiboVersion(Model model) {
		model.addAttribute("weiboVersion", Version.getWeiboVersion());
		return "cms/weibo_version";
	}

	@RequestMapping(value = "/switch/weibo/version", method = RequestMethod.GET)
	public String switchWeiboVersion(Model model) {
		Version.switchWeiboVersion();
		model.addAttribute("weiboVersion", Version.getWeiboVersion());
		return "cms/weibo_version";
	}

}

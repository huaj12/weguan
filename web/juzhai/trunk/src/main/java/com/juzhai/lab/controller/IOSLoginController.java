package com.juzhai.lab.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.web.AjaxResult;

@Controller
@RequestMapping("app/ios")
public class IOSLoginController extends BaseController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult login(HttpServletRequest request, String account,
			String pwd) {
		log.debug("ios login...");
		AjaxResult result = new AjaxResult();
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account);
		map.put("nickname", "中文昵称");
		result.setResult(map);
		log.debug("ios login success");
		return result;
	}
}

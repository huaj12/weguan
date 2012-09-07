package com.juzhai.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.service.IRescueboyService;

/**
 * 宅男自救器（自动发送消息）
 * 
 * @author kooks
 * 
 */
@Controller
@RequestMapping(value = "rescueboy")
public class RescueboyController extends BaseController {
	@Autowired
	private IRescueboyService rescueboyService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		model.addAttribute("open",
				rescueboyService.isOpenRescueboy(context.getUid()));
		return "web/profile/rescueboy";
	}

	@RequestMapping(value = "/open", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult open(HttpServletRequest request)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		rescueboyService.open(context.getUid());
		return new AjaxResult();
	}

	@RequestMapping(value = "/close", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult close(HttpServletRequest request)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		rescueboyService.close(context.getUid());
		return new AjaxResult();
	}

}

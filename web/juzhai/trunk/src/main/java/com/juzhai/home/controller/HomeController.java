package com.juzhai.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.bean.ActDealType;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;

@Controller
public class HomeController extends BaseController {

	@Autowired
	private IUserActService userActService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginReturnContext(request);
		getNextFeed(context.getUid(), 1, model);
		return "home/home";
	}

	@RequestMapping(value = "/ajax/dealFeed", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxDealFeed(HttpServletRequest request, Model model,
			long actId, long friendId, int type, int times)
			throws NeedLoginException {
		UserContext context = checkLoginReturnContext(request);
		ActDealType actDealType = ActDealType.valueOf(type);
		userActService.dealAct(context.getUid(), actId, friendId, actDealType);
		getNextFeed(context.getUid(), times, model);
		return "home/feedFragment";
	}

	/**
	 * 获取下一个feed
	 * 
	 * @param uid
	 *            当前用户ID
	 * @param times
	 *            第几次
	 * @param model
	 *            调用请求的Model
	 */
	private void getNextFeed(long uid, int times, Model model) {
		if (times == 3) {
			// TODO 获取随机
		} else {
			// 获取指定
		}
		model.addAttribute("times", times == 4 ? 1 : (times + 1));

	}
}

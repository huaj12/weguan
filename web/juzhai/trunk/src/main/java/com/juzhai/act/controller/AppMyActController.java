package com.juzhai.act.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;

@Controller
@RequestMapping(value = "app")
public class AppMyActController extends BaseController {

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IActCategoryService actCategoryService;

	private int hotCategorySize = 5;

	@RequestMapping(value = "/myAct", method = RequestMethod.GET)
	public String myAct(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		List<Act> myActList = userActService.getUserActFromCache(context
				.getUid());
		model.addAttribute("myActList", myActList);
		model.addAttribute("hotCategoryList",
				actCategoryService.listHotCategories(hotCategorySize));

		return "act/myAct";
	}

	@RequestMapping(value = "/addAct")
	public String addAct(HttpServletRequest request, long actId,
			String actName, Model model) {
		return null;
	}

	@RequestMapping(value = "/removeAct")
	public String removeAct(HttpServletRequest request, long actId,
			String actName, Model model) {
		return null;
	}
}

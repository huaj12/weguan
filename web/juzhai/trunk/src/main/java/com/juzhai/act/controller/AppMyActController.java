package com.juzhai.act.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;

@Controller
@RequestMapping(value = "app")
public class AppMyActController extends BaseController {

	@Autowired
	private IUserActService userActService;

	@RequestMapping(value = { "/myAct" }, method = RequestMethod.GET)
	public String myAct(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		List<Act> myActList = userActService.getUserActFromCache(context
				.getUid());
		model.addAttribute("myActList", myActList);

		// TODO 热门Act

		return "act/myAct";
	}

	// TODO 创建，添加，删除
}

package com.juzhai.passport.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.service.impl.UserGuideService;

@Controller
public class UserGuideController extends BaseController {

	@Autowired
	private UserGuideService userGuideService;

	@RequestMapping(value = "/app/guide/{step}", method = RequestMethod.GET)
	public String guide(HttpServletRequest request, @PathVariable int step,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		if (step <= 0 || step > InitData.GUIDE_STEPS.size()) {
			return error_500;
		}
		UserGuide userGuide = userGuideService.getUserGuide(context.getUid());
		if (null == userGuide || userGuide.getGuideStep() >= step) {
			return error_500;
		}
		Long actCategoryId = InitData.GUIDE_STEPS.get(step - 1);

		return "";
	}
}

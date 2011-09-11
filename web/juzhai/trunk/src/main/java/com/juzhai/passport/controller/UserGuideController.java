package com.juzhai.passport.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActCategoryService;
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
	@Autowired
	private IActCategoryService actCategoryService;

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
		List<Act> actList = actCategoryService.getHotActList(context.getTpId(),
				actCategoryId);
		model.addAttribute("actList", actList);

		return "";
	}

	@RequestMapping(value = "/app/guide/next", method = RequestMethod.POST)
	public String next(HttpServletRequest request, long[] actIds,
			String actNames[], Model model) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		return "";
	}
	
	@RequestMapping(value="test")
	public String test(){
		return "passport/kaixin001/index";
	}
}

package com.juzhai.home.controller.website;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.controller.view.UserActView;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IInterestUserService;

@Controller
public class HomeController extends BaseController {

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInterestUserService interestUserService;
	@Value("${web.my.act.max.rows}")
	private int webMyActMaxRows;
	@Value("${web.interest.user.max.rows}")
	private int webInterestUserMaxRows;
	@Value("${web.interest.me.max.rows}")
	private int webInterestMeMaxRows;

	@RequestMapping(value = { "/myActs", "/myActs/${page}", "/home" }, method = RequestMethod.GET)
	public String myActs(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		int totalCount = userActService.countUserActByUid(context.getUid());
		PagerManager pager = new PagerManager(page, webMyActMaxRows, totalCount);
		List<UserActView> userActViewList = userActService.pageUserActView(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("userActViewList", userActViewList);
		model.addAttribute("pager", pager);
		return null;
	}

	@RequestMapping(value = "/myInterests/${page}", method = RequestMethod.GET)
	public String myInterests(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		int totalCount = interestUserService
				.countInterestUser(context.getUid());
		PagerManager pager = new PagerManager(page, webInterestUserMaxRows,
				totalCount);
		List<ProfileCache> profileList = interestUserService.listInterestUser(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("profileList", profileList);
		model.addAttribute("pager", pager);
		return null;
	}

	@RequestMapping
	public String myDatings(HttpServletRequest request, Model model) {
		return null;
	}

	@RequestMapping(value = "/myInterestMes/${page}")
	public String myInterestMe(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		int totalCount = interestUserService.countInteresteMeUser(context
				.getUid());
		PagerManager pager = new PagerManager(page, webInterestMeMaxRows,
				totalCount);
		List<ProfileCache> profileList = interestUserService
				.listInteresteMeUser(context.getUid(), pager.getFirstResult(),
						pager.getMaxResult());
		model.addAttribute("profileList", profileList);
		model.addAttribute("pager", pager);
		return null;
	}

	@RequestMapping
	public String myBeDated(HttpServletRequest request, Model model) {
		return null;
	}
}

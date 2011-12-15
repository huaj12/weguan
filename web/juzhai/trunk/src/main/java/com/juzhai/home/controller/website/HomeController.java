package com.juzhai.home.controller.website;

import java.util.ArrayList;
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
import com.juzhai.act.model.Dating;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IDatingService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.DatingView;
import com.juzhai.home.bean.InterestUserView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.login.ILoginService;

@Controller
@RequestMapping(value = "home")
public class HomeController extends BaseController {

	@Autowired
	private IActService actService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IDatingService datingService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private ILoginService loginService;
	@Value("${web.my.act.max.rows}")
	private int webMyActMaxRows;
	@Value("${web.interest.user.max.rows}")
	private int webInterestUserMaxRows;
	@Value("${web.interest.me.max.rows}")
	private int webInterestMeMaxRows;
	@Value("${web.dating.max.rows}")
	private int webDatingMaxRows;
	@Value("${web.dating.me.max.rows}")
	private int webDatingMeMaxRows;
	@Value("${interest.user.show.act.count}")
	private int interestUserShowActCount;

	@RequestMapping(value = { "/acts", "/", "" }, method = RequestMethod.GET)
	public String myActs(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForWeb(request);
		return pageMyActs(request, model, 1);
	}

	@RequestMapping(value = "/acts/{page}", method = RequestMethod.GET)
	public String pageMyActs(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeHeader(context, context.getUid(), model);
		int totalCount = userActService.countUserActByUid(context.getUid());
		PagerManager pager = new PagerManager(page, webMyActMaxRows, totalCount);
		List<UserActView> userActViewList = userActService.pageUserActView(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("userActViewList", userActViewList);
		model.addAttribute("pager", pager);
		return "web/home/act/acts";
	}

	@RequestMapping(value = "/interests/{page}", method = RequestMethod.GET)
	public String myInterests(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeHeader(context, context.getUid(), model);
		int totalCount = interestUserService
				.countInterestUser(context.getUid());
		PagerManager pager = new PagerManager(page, webInterestUserMaxRows,
				totalCount);
		List<ProfileCache> profileList = interestUserService.listInterestUser(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		assembleInterestUserView(model, context, profileList, true);
		model.addAttribute("pager", pager);
		return "web/home/interest/interests";
	}

	@RequestMapping(value = "/interestMes/{page}", method = RequestMethod.GET)
	public String myInterestMe(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeHeader(context, context.getUid(), model);
		int totalCount = interestUserService.countInterestMeUser(context
				.getUid());
		PagerManager pager = new PagerManager(page, webInterestMeMaxRows,
				totalCount);
		List<ProfileCache> profileList = interestUserService
				.listInterestMeUser(context.getUid(), pager.getFirstResult(),
						pager.getMaxResult());
		assembleInterestUserView(model, context, profileList, null);
		model.addAttribute("pager", pager);
		return "web/home/interest/interestMes";
	}

	private void assembleInterestUserView(Model model, UserContext context,
			List<ProfileCache> profileList, Boolean hasInterest) {
		List<InterestUserView> interestUserViewList = new ArrayList<InterestUserView>();
		for (ProfileCache profileCache : profileList) {
			InterestUserView view = new InterestUserView();
			view.setProfileCache(profileCache);
			view.setUserActViewList(userActService.pageUserActView(
					profileCache.getUid(), 1, interestUserShowActCount));
			view.setDating(datingService.fetchDating(context.getUid(),
					profileCache.getUid()));
			view.setHasInterest(hasInterest != null ? hasInterest
					: interestUserService.isInterest(context.getUid(),
							profileCache.getUid()));
			interestUserViewList.add(view);
		}
		model.addAttribute("interestUserViewList", interestUserViewList);
	}

	@RequestMapping(value = "/myDatings/{page}", method = RequestMethod.GET)
	public String myDatings(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeHeader(context, context.getUid(), model);
		int totalCount = datingService.countDating(context.getUid());
		PagerManager pager = new PagerManager(page, webDatingMaxRows,
				totalCount);
		List<Dating> datingList = datingService.listDating(context.getUid(),
				pager.getFirstResult(), pager.getMaxResult());
		assembleDatingView(model, datingList, false);
		model.addAttribute("pager", pager);
		return null;
	}

	@RequestMapping(value = "/myDatingMes/{page}", method = RequestMethod.GET)
	public String myDatingMe(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeHeader(context, context.getUid(), model);
		int totalCount = datingService.countDatingMe(context.getUid());
		PagerManager pager = new PagerManager(page, webDatingMeMaxRows,
				totalCount);
		List<Dating> datingList = datingService.listDatingMe(context.getUid(),
				pager.getFirstResult(), pager.getMaxResult());
		assembleDatingView(model, datingList, true);
		model.addAttribute("pager", pager);
		return null;
	}

	private void assembleDatingView(Model model, List<Dating> datingList,
			boolean isShowDatingMe) {
		List<DatingView> datingViewList = new ArrayList<DatingView>();
		for (Dating dating : datingList) {
			DatingView view = new DatingView(dating,
					actService.getActById(dating.getActId()),
					profileService.getProfileCacheByUid(isShowDatingMe ? dating
							.getStarterUid() : dating.getReceiverUid()));
			datingViewList.add(view);
		}
		model.addAttribute("datingViewList", datingViewList);
	}

	@RequestMapping(value = { "/{uid}", "/{uid}/acts" }, method = RequestMethod.GET)
	public String userHome(HttpServletRequest request, Model model,
			@PathVariable long uid) {
		return userHomeActs(request, model, uid, 1);
	}

	@RequestMapping(value = "/{uid}/acts/{page}", method = RequestMethod.GET)
	public String userHomeActs(HttpServletRequest request, Model model,
			@PathVariable long uid, @PathVariable int page) {
		UserContext context = null;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
		}
		if (context != null && context.getUid() == uid) {
			return "redirect:/home";
		}
		showHomeHeader(context, uid, model);
		pageOtherUserActs(context, uid, page, model);
		return "web/home/act/user_acts";
	}

	private void pageOtherUserActs(UserContext context, long uid, int page,
			Model model) {
		int totalCount = userActService.countUserActByUid(uid);
		PagerManager pager = new PagerManager(page, webMyActMaxRows, totalCount);
		List<UserActView> userActViewList = userActService.pageUserActView(uid,
				pager.getFirstResult(), pager.getMaxResult());
		if (context != null && context.getUid() > 0) {
			for (UserActView userActView : userActViewList) {
				userActView.setHasUsed(userActService.hasAct(context.getUid(),
						userActView.getAct().getId()));
			}
		}
		model.addAttribute("userActViewList", userActViewList);
		model.addAttribute("pager", pager);
	}

	private void showHomeHeader(UserContext context, long uid, Model model) {
		queryProfile(uid, model);
		if (context == null || context.getUid() != uid) {
			model.addAttribute("online", loginService.isOnline(uid));
			if (context != null && context.hasLogin()) {
				model.addAttribute("isInterest",
						interestUserService.isInterest(context.getUid(), uid));
				Dating dating = datingService
						.fetchDating(context.getUid(), uid);
				if (null != dating) {
					model.addAttribute("datingView", new DatingView(dating,
							actService.getActById(dating.getActId()), null));
				}
			}
		}
	}
}

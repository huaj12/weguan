package com.juzhai.index.controller.website;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.controller.view.CategoryActView;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.ICategoryService;
import com.juzhai.act.service.IDatingService;
import com.juzhai.act.service.IShowActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.bean.ShowActOrder;
import com.juzhai.index.controller.view.ShowUserView;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.login.ILoginService;

@Controller
public class IndexController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IShowActService showActService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IDatingService datingService;
	@Value("${web.show.category.size}")
	private int webShowCategorySize;
	@Value("${web.show.acts.max.rows}")
	private int webShowActsMaxRows;
	@Value("${web.show.users.max.rows}")
	private int webShowUsersMaxRows;
	@Value("${show.user.show.act.count}")
	private int showUserShowActCount;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		return showActs(request, model);
	}

	@RequestMapping(value = "/showActs", method = RequestMethod.GET)
	public String showActs(HttpServletRequest request, Model model) {
		return pageShowActs(request, model, ShowActOrder.HOT_TIME.getType(),
				0L, 1);
	}

	@RequestMapping(value = "/showActs/{orderType}/{categoryId}/{page}", method = RequestMethod.GET)
	public String pageShowActs(HttpServletRequest request, Model model,
			@PathVariable String orderType, @PathVariable long categoryId,
			@PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		List<Category> categoryList = categoryService
				.listCategories(webShowCategorySize);
		ShowActOrder order = ShowActOrder.getShowActOrderByType(orderType);
		long cityId = fetchCityId(request);
		PagerManager pager = new PagerManager(page, webShowActsMaxRows,
				showActService.countShowActs(cityId, categoryId));
		List<Act> actList = showActService.listShowActs(cityId, categoryId,
				order, pager.getFirstResult(), pager.getMaxResult());
		List<CategoryActView> categoryActViewList = new ArrayList<CategoryActView>();
		for (Act act : actList) {
			CategoryActView view = new CategoryActView();
			view.setAct(act);
			if (context.hasLogin()) {
				view.setHasUsed(userActService.hasAct(context.getUid(),
						act.getId()));
			}
			categoryActViewList.add(view);
		}
		model.addAttribute("categoryActViewList", categoryActViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("orderType", orderType);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("pageType", "cqw");
		return "web/index/cqw/show_acts";
	}

	@RequestMapping(value = "/showUsers", method = RequestMethod.GET)
	public String showUsers(HttpServletRequest request, Model model) {
		return pageShowUsers(request, model, "all", 1);
	}

	@RequestMapping(value = "/showUsers/{genderType}/{page}", method = RequestMethod.GET)
	public String pageShowUsers(HttpServletRequest request, Model model,
			@PathVariable String genderType, @PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		long cityId = fetchCityId(request);
		Integer gender = null;
		if (genderType.equals("male")) {
			gender = 1;
		} else if (genderType.equals("female")) {
			gender = 0;
		}
		List<Long> exceptUids = null;
		if (context.hasLogin()) {
			exceptUids = new ArrayList<Long>();
			exceptUids.add(context.getUid());
		}
		PagerManager pager = new PagerManager(page, webShowUsersMaxRows,
				profileService.countProfile(gender, cityId, exceptUids));
		List<Profile> profileList = profileService
				.listProfileOrderByLoginWebTime(gender, cityId, exceptUids,
						pager.getFirstResult(), pager.getMaxResult());
		List<ShowUserView> showUserViewList = new ArrayList<ShowUserView>();
		for (Profile profile : profileList) {
			ShowUserView view = new ShowUserView();
			view.setProfile(profile);
			view.setUserActViewList(userActService.pageUserActView(
					profile.getUid(), 0, showUserShowActCount));
			if (context.hasLogin()) {
				view.setHasDating(datingService.hasDating(context.getUid(),
						profile.getUid()));
				view.setHasInterest(interestUserService.isInterest(
						context.getUid(), profile.getUid()));
			}
			view.setOnline(loginService.isOnline(profile.getUid()));
			showUserViewList.add(view);
		}
		model.addAttribute("showUserViewList", showUserViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("genderType", genderType);
		model.addAttribute("pageType", "zbe");
		return "web/index/zbe/show_users";
	}
}

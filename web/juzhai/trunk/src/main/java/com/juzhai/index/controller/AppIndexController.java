package com.juzhai.index.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juzhai.act.InitData;
import com.juzhai.act.controller.view.CategoryActView;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.ICategoryService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.controller.view.ActLiveView;
import com.juzhai.index.service.IActLiveService;
import com.juzhai.index.service.IActRankService;

@Controller
@RequestMapping(value = "app")
public class AppIndexController extends BaseController {

	@Autowired
	private IActLiveService actLiveService;
	@Autowired
	private IActRankService actRankService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IUserActService userActService;
	@Value("${live.act.max.results}")
	private int liveActMaxResults;
	@Value("${rank.act.count}")
	private int rankActCount;
	@Value("${show.category.size}")
	private int showCategorySize = 5;
	@Value("${category.act.max.rows}")
	private int categoryActMaxRows = 8;

	@RequestMapping(value = "/showLive", method = RequestMethod.GET)
	public String showLive(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForApp(request);
		pageLive(request, model, 1);
		return "index/app/live";
	}

	@RequestMapping(value = "/ajax/pageLive", method = RequestMethod.GET)
	public String pageLive(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int page)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		PagerManager pager = new PagerManager(page, liveActMaxResults,
				actLiveService.countActivists(context.getTpId()));
		List<ActLiveView> actLiveViewList = actLiveService
				.listActivists(context.getTpId(), pager.getFirstResult(),
						pager.getMaxResult());
		model.addAttribute("pager", pager);
		model.addAttribute("actLiveViewList", actLiveViewList);
		return "index/app/live_list";
	}

	@RequestMapping(value = "/showRank", method = RequestMethod.GET)
	public String showRank(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForApp(request);
		List<Act> actList = actRankService.listActRank(
				DateUtils.addDays(new Date(), -1), rankActCount);
		model.addAttribute("actList", actList);
		return "index/app/rank";
	}

	@RequestMapping(value = "/showCategoryActs", method = RequestMethod.GET)
	public String showHotActs(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") long categoryId)
			throws NeedLoginException {
		checkLoginForApp(request);
		List<Category> categoryList = categoryService
				.listCategories(showCategorySize);
		model.addAttribute("categoryList", categoryList);
		if (categoryId <= 0) {
			categoryId = categoryList.get(0).getId();
		}
		pageCategoryAct(request, model, categoryId, 1);
		return "index/app/category_act";
	}

	@RequestMapping(value = "/ajax/pageCategoryAct", method = RequestMethod.GET)
	public String pageCategoryAct(HttpServletRequest request, Model model,
			long categoryId, @RequestParam(defaultValue = "1") int page)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		pageCategoryAct(context, model, categoryId, page);
		model.addAttribute("categoryId", categoryId);
		return "index/app/category_act_list";
	}

	private void pageCategoryAct(UserContext context, Model model,
			long categoryId, int page) {
		List<Long> myActIds = userActService.getUserActIdsFromCache(
				context.getUid(), Integer.MAX_VALUE);
		List<Act> hotActList = InitData.CATEGORY_ACT_LIST_MAP.get(categoryId);
		int totalCount = hotActList.size();
		PagerManager pager = new PagerManager(page, categoryActMaxRows,
				totalCount);
		List<CategoryActView> categoryActViewList = new ArrayList<CategoryActView>(
				categoryActMaxRows);
		if (pager.getFirstResult() < hotActList.size()) {
			for (Act act : hotActList.subList(pager.getFirstResult(), Math.min(
					pager.getFirstResult() + pager.getMaxResult(),
					hotActList.size()))) {
				CategoryActView view = new CategoryActView();
				view.setAct(act);
				view.setHasUsed(myActIds.contains(act.getId()));
				categoryActViewList.add(view);
			}
		}
		model.addAttribute("categoryActViewList", categoryActViewList);
		model.addAttribute("pager", pager);
	}
}

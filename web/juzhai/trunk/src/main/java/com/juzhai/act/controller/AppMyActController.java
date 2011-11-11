package com.juzhai.act.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.InitData;
import com.juzhai.act.controller.form.AddActForm;
import com.juzhai.act.controller.view.CategoryActView;
import com.juzhai.act.controller.view.UserActView;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.ICategoryService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.service.IFriendService;

@Controller
@RequestMapping(value = "app")
public class AppMyActController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserActService userActService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IFriendService friendService;
	@Value("${show.category.size}")
	private int showCategorySize = 5;
	@Value("${my.act.max.rows}")
	private int myActMaxRows = 20;
	@Value("${category.act.max.rows}")
	private int categoryActMaxRows = 8;
	@Value("${same.act.friends.num}")
	private int sameActFriendsNum = 2;

	@RequestMapping(value = "/myAct", method = RequestMethod.GET)
	public String myAct(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		// queryPoint(context.getUid(), model);
		showMyActTips(model);
		queryProfile(context.getUid(), model);
		pageMyAct(request, model, 1);
		return "act/app/my_act";
	}

	@RequestMapping(value = "/ajax/addAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addAct(HttpServletRequest request, AddActForm addActForm,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (addActForm.getActId() > 0) {
				// 通过actId添加
				userActService.addAct(context.getUid(), addActForm.getActId(),
						addActForm.isSyn());
			} else if (StringUtils.isNotEmpty(addActForm.getActName())) {
				// 通过名字添加
				userActService.addAct(context.getUid(),
						StringUtils.trim(addActForm.getActName()).trim(),
						addActForm.isSyn());
			}
			ajaxResult.setSuccess(true);
		} catch (ActInputException e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			ajaxResult.setSuccess(false);
			ajaxResult.setErrorCode(e.getErrorCode());
			ajaxResult.setErrorInfo(messageSource.getMessage(e.getErrorCode(),
					null, e.getErrorCode(), Locale.SIMPLIFIED_CHINESE));
		}

		return ajaxResult;
	}

	@RequestMapping(value = "/ajax/removeAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult removeAct(HttpServletRequest request, long actId,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult ajaxResult = new AjaxResult();
		userActService.removeAct(context.getUid(), actId);
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	@RequestMapping(value = "/ajax/pageMyAct", method = RequestMethod.GET)
	public String pageMyAct(HttpServletRequest request, Model model, int page)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		int totalCount = userActService.countUserActByUid(context.getUid());
		PagerManager pager = new PagerManager(page, myActMaxRows, totalCount);
		List<UserActView> userActViewList = userActService.pageUserActView(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		for (UserActView view : userActViewList) {
			view.setFriendList(friendService.findSameActFriends(
					context.getUid(), view.getAct().getId(), sameActFriendsNum));
		}
		model.addAttribute("userActViewList", userActViewList);
		model.addAttribute("pager", pager);
		return "act/app/my_act_list";
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
		return "act/app/category_act";
	}

	@RequestMapping(value = "/ajax/pageCategoryAct", method = RequestMethod.GET)
	public String pageCategoryAct(HttpServletRequest request, Model model,
			long categoryId, @RequestParam(defaultValue = "1") int page)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		pageCategoryAct(context, model, categoryId, 1);
		model.addAttribute("categoryId", categoryId);
		return "act/app/my_act_list";
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

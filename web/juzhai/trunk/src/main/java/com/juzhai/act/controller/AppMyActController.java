package com.juzhai.act.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.controller.form.AddActForm;
import com.juzhai.act.controller.view.HotActView;
import com.juzhai.act.controller.view.UserActView;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActCategory;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;

@Controller
@RequestMapping(value = "app")
public class AppMyActController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IActCategoryService actCategoryService;
	@Autowired
	private MessageSource messageSource;
	@Value("${hot.category.size}")
	private int hotCategorySize = 5;
	@Value("${my.act.max.rows}")
	private int myActMaxRows = 20;

	@RequestMapping(value = "/myAct", method = RequestMethod.GET)
	public String myAct(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		queryPoint(context.getUid(), model);
		queryProfile(context.getUid(), model);
		pageMyAct(request, model, 1);

		List<ActCategory> hotCategoryList = actCategoryService
				.listHotCategories(hotCategorySize);
		model.addAttribute("hotCategoryList", hotCategoryList);
		if (CollectionUtils.isNotEmpty(hotCategoryList)) {
			listHotAct(model, hotCategoryList.get(0).getId(), context);
		}
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
		model.addAttribute("userActViewList", userActViewList);
		model.addAttribute("pager", pager);
		return "act/app/my_act_list";
	}

	@RequestMapping(value = "/ajax/showHotActs", method = RequestMethod.GET)
	public String showHotActs(HttpServletRequest request, Model model,
			long actCategoryId) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		listHotAct(model, actCategoryId, context);
		return "act/app/hot_act_list";
	}

	private void listHotAct(Model model, long actCategoryId, UserContext context) {
		List<Long> myActIds = userActService.getUserActIdsFromCache(
				context.getUid(), Integer.MAX_VALUE);
		List<Act> hotActList = actCategoryService.getHotActList(
				context.getTpId(), actCategoryId);
		List<HotActView> hotActViewList = new ArrayList<HotActView>(
				hotActList.size());
		for (Act act : hotActList) {
			HotActView view = new HotActView();
			view.setAct(act);
			view.setHasUsed(myActIds.contains(act.getId()));
			hotActViewList.add(view);
		}

		model.addAttribute("hotActViewList", hotActViewList);
	}
}

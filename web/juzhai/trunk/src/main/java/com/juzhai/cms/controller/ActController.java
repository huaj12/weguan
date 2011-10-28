package com.juzhai.cms.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.cms.controller.form.SearchActForm;
import com.juzhai.cms.controller.view.CmsActView;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;

@Controller
@RequestMapping("/cms")
public class ActController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IActService actService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/searchActs")
	public String searchActs(HttpServletRequest request, Model model,
			SearchActForm form) {
		if (null != form && StringUtils.isNotEmpty(form.getStartDate())
				&& StringUtils.isNotEmpty(form.getEndDate())) {
			try {
				Date startDate = DateUtils.parseDate(form.getStartDate(),
						new String[] { "yyyy.MM.dd" });
				Date endDate = DateUtils.parseDate(form.getEndDate(),
						new String[] { "yyyy.MM.dd" });

				PagerManager pager = new PagerManager(form.getPageId(), 10,
						actService.countNewActs(startDate, endDate));
				List<Act> actList = actService.searchNewActs(startDate,
						endDate, form.getOrder(), pager.getFirstResult(),
						pager.getMaxResult());
				List<CmsActView> viewList = new ArrayList<CmsActView>(
						actList.size());
				for (Act act : actList) {
					viewList.add(new CmsActView(act, actService
							.listSynonymActs(act.getId()), actService
							.isShieldAct(act.getId())));
				}
				model.addAttribute("cmsActViewList", viewList);
				model.addAttribute("pager", pager);
			} catch (ParseException e) {
				log.error("parse search date error.", e);
			}
			model.addAttribute("searchActForm", form);
		}
		return "cms/act_list";
	}

	@RequestMapping(value = "/showSynonym", method = RequestMethod.GET)
	public String showSynonym(HttpServletRequest request, Model model,
			long actId) {
		Act act = actService.getActById(actId);
		if (null != act) {
			List<Act> synonymActList = actService.listSynonymActs(actId);
			List<String> maybeSynonymList = actService.indexSearchName(
					act.getName(), 10);
			maybeSynonymList.remove(act.getName());
			model.addAttribute("maybeSynonymList", maybeSynonymList);
			model.addAttribute("synonymActList", synonymActList);
			model.addAttribute("act", act);
		}
		return "cms/synonym_list";
	}

	@RequestMapping(value = "/addSynonym", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addSynonym(HttpServletRequest request, Model model,
			long actId, String synonymActName) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actService.actExist(actId)) {
			try {
				actService.addSynonym(actId, synonymActName);
				ajaxResult.setSuccess(true);
			} catch (ActInputException e) {
				ajaxResult.setSuccess(false);
				ajaxResult.setErrorCode(e.getErrorCode());
				ajaxResult.setErrorInfo(messageSource.getMessage(
						e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			}
		} else {
			ajaxResult.setErrorInfo("The act is null");
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/removeSynonym", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult removeSynonym(HttpServletRequest request, Model model,
			long actId, long removeActId) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actService.actExist(actId)) {
			actService.removeSynonym(actId, removeActId);
			ajaxResult.setSuccess(true);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/addShield", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addShield(HttpServletRequest request, Model model,
			long actId) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actService.actExist(actId)) {
			actService.addActShield(actId);
			ajaxResult.setSuccess(true);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/removeShield", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult removeSynonym(HttpServletRequest request, Model model,
			long actId) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actService.actExist(actId)) {
			actService.removeActShield(actId);
			ajaxResult.setSuccess(true);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/showShield", method = RequestMethod.GET)
	public String showShield(HttpServletRequest request, Model model) {
		List<Act> shieldActList = actService.listShieldActs();
		model.addAttribute("shieldActList", shieldActList);
		return "cms/shield_list";
	}
}

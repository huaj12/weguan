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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.bean.SuitAge;
import com.juzhai.act.bean.SuitGender;
import com.juzhai.act.bean.SuitStatus;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActDetail;
import com.juzhai.act.service.IActAdService;
import com.juzhai.act.service.IActDetailService;
import com.juzhai.act.service.IActService;
import com.juzhai.cms.controller.form.AddActForm;
import com.juzhai.cms.controller.view.CmsActMagerView;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;

//@Controller
@RequestMapping("/cms")
public class CmsActController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IActService actService;
	@Autowired
	private IActDetailService actDetailService;
	@Autowired
	private IActAdService actAdService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/showCreateAct", method = RequestMethod.GET)
	public String showCreateAct(HttpServletRequest request, Model model,
			AddActForm addActForm) {
		assembleBaseDates(model);
		model.addAttribute("addActForm", addActForm);
		return "cms/act/create_act";
	}

	@RequestMapping(value = "/createAct", method = RequestMethod.POST)
	public String createAct(AddActForm addActForm, Long addUid,
			HttpServletRequest request, Model model) {
		UserContext context = (UserContext) request.getAttribute("context");
		try {
			actService.cmsCreateAct(context.getUid(), addActForm);
		} catch (JuzhaiException e) {
			log.error("create act is error.", e);
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			return showCreateAct(request, model, addActForm);
		} catch (ParseException e2) {
			log.error("create act is error.", e2);
			model.addAttribute("errorInfo", "date format error.");
			return showCreateAct(request, model, addActForm);
		}
		return "redirect:/cms/showCreateAct";
	}

	@RequestMapping(value = "/showActManager", method = RequestMethod.GET)
	public String showActManager(HttpServletRequest request, Model model) {
		assembleBaseDates(model);
		return "cms/act/act_manager";
	}

	@RequestMapping(value = "/queryAct", method = RequestMethod.GET)
	public String searchAct(Model model, String bDate, String eDate,
			String name, Integer pageId) {
		List<Act> acts = null;
		if (pageId == null)
			pageId = 1;
		Date startDate = null;
		Date endDate = null;
		try {
			if (!StringUtils.isEmpty(bDate)) {
				startDate = DateUtils.parseDate(bDate, DateFormat.DATE_PATTERN);
			}
			if (!StringUtils.isEmpty(eDate)) {
				endDate = DateUtils.parseDate(eDate, DateFormat.DATE_PATTERN);
			}
		} catch (ParseException e) {
			log.error("parse search date error.", e);
		}
		PagerManager pager = new PagerManager(pageId, 10,
				actService.queryActsCount(startDate, endDate, name));
		acts = actService.queryActs(startDate, endDate, name,
				pager.getFirstResult(), pager.getMaxResult());

		List<CmsActMagerView> viewList = new ArrayList<CmsActMagerView>(
				acts.size());
		for (Act act : acts) {
			viewList.add(new CmsActMagerView(act));
		}
		model.addAttribute("cmsActMagerViews", viewList);
		model.addAttribute("pager", pager);
		model.addAttribute("acts", acts);
		model.addAttribute("eDate", eDate);
		model.addAttribute("bDate", bDate);
		model.addAttribute("name", name);
		return "cms/act/act_manager_list";
	}

	@RequestMapping(value = "/showUpdate", method = RequestMethod.GET)
	public String showUpdate(HttpServletRequest request, Model model, long actId) {
		Act act = actService.getActById(actId);
		ActDetail actDetail = actDetailService.getActDetail(actId);
		if (null == act) {
			return error_500;
		}
		assembleBaseDates(model);
		AddActForm addActForm = new AddActForm();
		addActForm.setId(act.getId());
		addActForm.setName(act.getName());
		addActForm.setFullName(act.getFullName());
		addActForm.setIntro(act.getIntro());
		if (actDetail != null) {
			addActForm.setDetail(actDetail.getDetail());
		}
		List<Long> catIds = new ArrayList<Long>();
		if (StringUtils.isNotEmpty(act.getCategoryIds())) {
			for (String catId : StringUtils.split(act.getCategoryIds(), ",")) {
				if (StringUtils.isNotEmpty(catId)) {
					catIds.add(Long.valueOf(catId));
				}
			}
		}
		addActForm.setCatIds(catIds);
		addActForm.setProvince(act.getProvince());
		addActForm.setCity(act.getCity());
		addActForm.setTown(act.getTown());
		addActForm.setAddress(act.getAddress());
		addActForm.setSuitAge(SuitAge.getByIndex(act.getSuitAge()).name());
		addActForm.setSuitGender(SuitGender.getByIndex(act.getSuitGender())
				.name());
		addActForm.setSuitStatus(SuitStatus.getByIndex(act.getSuitStatus())
				.name());
		addActForm.setMinCharge(act.getMinCharge());
		addActForm.setMaxCharge(act.getMaxCharge());
		addActForm.setMinRoleNum(act.getMinRoleNum());
		addActForm.setMaxRoleNum(act.getMaxRoleNum());
		addActForm.setKeyWords(act.getKeyWords());
		if (null != act.getStartTime()) {
			addActForm.setStartTime(DateFormat.SDF.format(act.getStartTime()));
		}
		if (null != act.getEndTime()) {
			addActForm.setEndTime(DateFormat.SDF.format(act.getEndTime()));
		}

		model.addAttribute("addActForm", addActForm);
		model.addAttribute("logoWebPath",
				JzResourceFunction.actLogo(act.getId(), act.getLogo(), 0));
		model.addAttribute("ads", actAdService.getActAds(actId));
		return "cms/act/update_act";
	}

	@RequestMapping(value = "/updateAct", method = RequestMethod.POST)
	public String updateAct(HttpServletRequest request, AddActForm addActForm,
			Model model) {
		UserContext context = (UserContext) request.getAttribute("context");
		try {
			actService.cmsUpdateAct(context.getUid(), addActForm);
		} catch (JuzhaiException e) {
			log.error("update act is error.", e);
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			model.addAttribute("addActForm", addActForm);
			assembleBaseDates(model);
			return "cms/act/update_act";
		} catch (ParseException e2) {
			log.error("create act is error.", e2);
			model.addAttribute("errorInfo", "date format error.");
			model.addAttribute("addActForm", addActForm);
			assembleBaseDates(model);
			return "cms/act/update_act";
		}

		return "redirect:/cms/showActManager";
	}
}

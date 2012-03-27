package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.controller.view.CmsReportView;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Report;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IReportService;

@Controller
@RequestMapping("/cms")
public class CmsReportController {

	@Autowired
	private IReportService reportService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IDialogService dialogService;

	@RequestMapping(value = "/show/report", method = RequestMethod.GET)
	public String showReport(Model model,
			@RequestParam(defaultValue = "1") int pageId,
			@RequestParam(defaultValue = "0") int type) {
		PagerManager pager = new PagerManager(pageId, 20,
				reportService.listReportCount(type));
		List<Report> list = reportService.listReport(pager.getFirstResult(),
				pager.getMaxResult(), type);
		List<CmsReportView> views = assembleCmsReportView(list);
		model.addAttribute("views", views);
		model.addAttribute("pager", pager);
		model.addAttribute("type", type);
		return "/cms/report/list";
	}

	private List<CmsReportView> assembleCmsReportView(List<Report> list) {
		List<CmsReportView> views = new ArrayList<CmsReportView>();
		for (Report report : list) {
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(report.getUid());
			ProfileCache reportProfileCache = profileService
					.getProfileCacheByUid(report.getReportUid());
			CmsReportView view = new CmsReportView(
					reportProfileCache.getNickname(),
					profileCache.getNickname(), report);
			views.add(view);
		}
		return views;
	}

	@RequestMapping(value = "/handle/report", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult handleReport(long id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			reportService.handleReport(id);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/shield/report", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult shieldReport(long id, long time, long uid) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			Date date = new Date();
			reportService.shieldUser(id, uid, date.getTime() + time);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/un/shield/report", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult unShieldReport(long id, long uid) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			reportService.unShieldUser(id, uid);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/delete/report", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteReport(long id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			reportService.deleteReport(id);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/report/message/{tagerUid}/{uid}", method = RequestMethod.GET)
	public String reportMessage(@PathVariable long uid,
			@PathVariable long tagerUid,
			@RequestParam(defaultValue = "1") int pageId, Model model) {
		PagerManager pager = new PagerManager(pageId, 50,
				dialogService.countDialogContent(uid, tagerUid));
		List<DialogContentView> dialogContentViewList = dialogService
				.listDialogContent(uid, tagerUid, pager.getFirstResult(),
						pager.getMaxResult());
		model.addAttribute("dialogContentViewList", dialogContentViewList);
		model.addAttribute("uid", uid);
		model.addAttribute("tagerUid", tagerUid);
		model.addAttribute("pager", pager);
		return "/cms/report/message";
	}

}

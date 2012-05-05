package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.controller.view.CmsReportView;
import com.juzhai.cms.controller.view.UserLockView;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.LockUserLevel;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Report;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IReportService;

@Controller
@RequestMapping("/cms")
public class CmsReportController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IReportService reportService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IDialogService dialogService;
	@Autowired
	private IPassportService passportService;

	@RequestMapping(value = "/report/show", method = RequestMethod.GET)
	public String showReport(Model model,
			@RequestParam(defaultValue = "1") int pageId,
			@RequestParam(defaultValue = "0") int type) {
		PagerManager pager = new PagerManager(pageId, 20,
				reportService.countListReport(type));
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
			ProfileCache createProfile = profileService
					.getProfileCacheByUid(report.getCreateUid());
			ProfileCache reportProfileCache = profileService
					.getProfileCacheByUid(report.getReportUid());
			CmsReportView view = new CmsReportView(reportProfileCache,
					createProfile, report);
			views.add(view);
		}
		return views;
	}

	@RequestMapping(value = "/report/ignore", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult handleReport(long id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			reportService.ignoreReport(id);
		} catch (Exception e) {
			log.error("handleReport is error", e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/report/shield", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult shieldReport(long id, int level, long uid) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			reportService.shieldUser(id, uid,
					LockUserLevel.getLockUserLevelEnum(level));
		} catch (Exception e) {
			log.error("shieldReport is error", e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/report/unshield", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult unShieldReport(long uid) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			reportService.unShieldUser(uid);
		} catch (Exception e) {
			log.error("unShieldReport is error", e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/report/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteReport(long id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			reportService.deleteReport(id);
		} catch (Exception e) {
			log.error("deleteReport is error", e);
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

	@RequestMapping(value = "/show/lock/user", method = RequestMethod.GET)
	public String showLockUser(Model model,
			@RequestParam(defaultValue = "1") int pageId) {
		PagerManager pager = new PagerManager(pageId, 50,
				passportService.countLockUser());
		List<Passport> list = passportService.listLockUser(
				pager.getFirstResult(), pager.getMaxResult());
		List<UserLockView> views = new ArrayList<UserLockView>();
		for (Passport passport : list) {
			UserLockView view = new UserLockView();
			view.setPassport(passport);
			view.setProfile(profileService.getProfileCacheByUid(passport
					.getId()));
			views.add(view);
		}
		model.addAttribute("views", views);
		model.addAttribute("pager", pager);
		return "/cms/report/lock_user_list";
	}
}

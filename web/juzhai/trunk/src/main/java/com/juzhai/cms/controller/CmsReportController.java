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
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.LockUserLevel;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Report;
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

	@RequestMapping(value = "/report/show/", method = RequestMethod.GET)
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
			// TODO (done) 为什么不放两个profile？如果需要显示头像或者链接，性别，怎么办？再改java？
			CmsReportView view = new CmsReportView(reportProfileCache,
					createProfile, report);
			views.add(view);
		}
		return views;
	}

	@RequestMapping(value = "/report/handle", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult handleReport(long id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			reportService.handleReport(id);
		} catch (Exception e) {
			// TODO (done) handleReport会抛异常吗？就算这里为了安全，捕获异常，也应该把错误打出来。
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
			// TODO (done) 锁定时间不应该页面传进来，页面仅仅传入锁定级别，具体时间应该是服务器里配置的时间
			reportService.shieldUser(id, uid, LockUserLevel.getLockTime(level));
		} catch (Exception e) {
			// TODO (done) handleReport会抛异常吗？就算这里为了安全，捕获异常，也应该把错误打出来。
			log.error("shieldReport is error", e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	// TODO (done) 这个请求url不好，通常应该是“/模块/操作”,"/un/shield/"比较奇怪
	@RequestMapping(value = "/report/unshield", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult unShieldReport(long id, long uid) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			// TODO (done) 解锁不应该还原report的状态
			reportService.unShieldUser(id, uid);
		} catch (Exception e) {
			// TODO (done) handleReport会抛异常吗？就算这里为了安全，捕获异常，也应该把错误打出来。
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
			// TODO (done) handleReport会抛异常吗？就算这里为了安全，捕获异常，也应该把错误打出来。
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

}

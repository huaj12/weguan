package com.juzhai.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juzhai.cms.service.IUserActionService;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.model.LoginLog;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping("/cms")
public class CmsUserActionControll {
	@Autowired
	private IDialogService dialogService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserActionService userActionService;

	@RequestMapping(value = "/find/dialog", method = RequestMethod.GET)
	public String dialogList(Model model,
			@RequestParam(defaultValue = "1") int pageId, Long uid) {
		if (uid != null && uid > 0) {
			PagerManager pager = new PagerManager(pageId, 20,
					dialogService.countDialong(uid));
			List<DialogView> dialogViewList = dialogService.listDialog(uid,
					pager.getFirstResult(), pager.getMaxResult());
			model.addAttribute("dialogViewList", dialogViewList);
			model.addAttribute("pager", pager);
			model.addAttribute("profile",
					profileService.getProfileCacheByUid(uid));
		}
		return "cms/find_dialog";
	}

	@RequestMapping(value = "/find/userLoginLog", method = RequestMethod.GET)
	public String userLoginLogList(Model model,
			@RequestParam(defaultValue = "1") int pageId, Long uid) {
		if (uid != null && uid > 0) {
			PagerManager pager = new PagerManager(pageId, 50,
					userActionService.countUserLoginInfo(uid));

			List<LoginLog> list = userActionService.listUserLoginInfo(uid,
					pager.getFirstResult(), pager.getMaxResult());
			model.addAttribute("loginLogList", list);
			model.addAttribute("pager", pager);
			model.addAttribute("profile",
					profileService.getProfileCacheByUid(uid));
		}
		return "cms/user_login_log";
	}

}

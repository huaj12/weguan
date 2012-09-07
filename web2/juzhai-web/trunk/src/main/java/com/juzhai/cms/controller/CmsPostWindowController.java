package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.controller.form.PostWindowSortListForm;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.index.controller.view.PostWindowView;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.exception.InputPostWindowException;
import com.juzhai.post.model.PostWindow;
import com.juzhai.post.service.IPostWindowService;

@Controller
@RequestMapping("/cms")
public class CmsPostWindowController {
	@Autowired
	private IPostWindowService postWindowService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/show/postwindow/list", method = RequestMethod.GET)
	public String showPostWindowList(Model model) {
		List<PostWindow> list = postWindowService.listPostWindow();
		List<PostWindowView> postWindowViews = new ArrayList<PostWindowView>();
		for (PostWindow window : list) {
			PostWindowView view = new PostWindowView();
			view.setPostWindow(window);
			view.setProfileCache(profileService.getProfileCacheByUid(window
					.getUid()));
			postWindowViews.add(view);
		}
		model.addAttribute("postWindowViews", postWindowViews);
		return "/cms/postwindow/list";
	}

	@RequestMapping(value = "/add/postwindow", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addPostWindow(
			@RequestParam(defaultValue = "0") long postId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			postWindowService.addPostWindow(postId);
		} catch (InputPostException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/del/postwindow", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult delPostWindow(@RequestParam(defaultValue = "0") long id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			postWindowService.removePostWindow(id);
		} catch (InputPostWindowException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/sort/postwindow", method = RequestMethod.POST)
	public String sortPostWindow(PostWindowSortListForm form) {
		postWindowService.sortPostWindow(form.getPostWindowSortForm());
		return "redirect:/../cms/show/postwindow/list";
	}

}

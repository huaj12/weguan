package com.juzhai.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.web.AjaxResult;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.service.IPostSearchService;
import com.juzhai.search.service.IProfileSearchService;

@Controller
@RequestMapping("/cms/lunece")
public class CmsIndexManagerController {
	@Autowired
	private IPostService postService;
	@Autowired
	private IPostSearchService postSearchService;
	@Autowired
	private IProfileSearchService profileSearchService;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = "/manager/show", method = RequestMethod.GET)
	public String show(Model model) {
		return "/cms/lunece/show";
	}

	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult postAdd(Model model, long postId) {
		if (postService.getPostById(postId) != null) {
			postSearchService.createIndex(postId);
		}
		return new AjaxResult();
	}

	@RequestMapping(value = "/post/del", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult postDel(Model model, long postId) {
		if (postService.getPostById(postId) != null) {
			postSearchService.deleteIndex(postId);
		}
		return new AjaxResult();
	}

	@RequestMapping(value = "/profile/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult profileAdd(Model model, long uid) {
		ProfileCache cache = profileService.getProfileCacheByUid(uid);
		if (cache != null) {
			profileSearchService.createIndex(uid);
		}
		return new AjaxResult();
	}

	@RequestMapping(value = "/profile/del", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult profileDel(Model model, long uid) {
		ProfileCache cache = profileService.getProfileCacheByUid(uid);
		if (cache != null) {
			profileSearchService.deleteIndex(uid);
		}
		return new AjaxResult();
	}
}

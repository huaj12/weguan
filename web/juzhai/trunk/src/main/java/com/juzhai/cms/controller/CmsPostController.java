package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.controller.view.CmsPostView;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;

@Controller
@RequestMapping("/cms")
public class CmsPostController {

	@Autowired
	private IPostService postService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/show/post/unhandle", method = RequestMethod.GET)
	public String showPostUnhandle(Model model,
			@RequestParam(defaultValue = "0") long city,
			@RequestParam(defaultValue = "1") int pageId, Boolean isIdea) {
		PagerManager pager = new PagerManager(pageId, 30,
				postService.countUnhandlePost(city, isIdea));
		List<Post> list = postService.listUnhandlePost(city, isIdea,
				pager.getFirstResult(), pager.getMaxResult());
		List<CmsPostView> listView = new ArrayList<CmsPostView>();
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			listView.add(new CmsPostView(post, cache));
		}
		model.addAttribute("postView", listView);
		model.addAttribute("pager", pager);
		model.addAttribute("city", city);
		model.addAttribute("isIdea", isIdea);
		return "/cms/post/unhandle";
	}

	@RequestMapping(value = "/show/post/shield", method = RequestMethod.GET)
	public String showPostShield(Model model,
			@RequestParam(defaultValue = "1") int pageId,
			@RequestParam(defaultValue = "0") long city) {
		PagerManager pager = new PagerManager(pageId, 30,
				postService.countShieldPost(city));
		List<Post> list = postService.listShieldPost(city,
				pager.getFirstResult(), pager.getMaxResult());
		List<CmsPostView> listView = new ArrayList<CmsPostView>();
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			listView.add(new CmsPostView(post, cache));
		}
		model.addAttribute("postView", listView);
		model.addAttribute("pager", pager);
		model.addAttribute("city", city);
		return "/cms/post/shield";
	}

	@RequestMapping(value = "/show/post/handle", method = RequestMethod.GET)
	public String showPosthandle(Model model,
			@RequestParam(defaultValue = "0") long city, Long province,
			Integer gender, @RequestParam(defaultValue = "1") int pageId,
			Boolean isIdea) {
		PagerManager pager = new PagerManager(pageId, 30,
				postService.countHandlePost(city, gender, isIdea));
		List<Post> list = postService.listHandlePost(city, gender, isIdea,
				pager.getFirstResult(), pager.getMaxResult());
		List<CmsPostView> listView = new ArrayList<CmsPostView>();
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			listView.add(new CmsPostView(post, cache));
		}
		model.addAttribute("postView", listView);
		model.addAttribute("pager", pager);
		model.addAttribute("city", city);
		model.addAttribute("province", province);
		model.addAttribute("gender", gender);
		model.addAttribute("isIdea", isIdea);
		return "/cms/post/handle";
	}

	@RequestMapping(value = "/post/unhandle/del", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult postUnhandleDel(Integer postId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			postService.deletePost(postId);
		} catch (InputPostException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/post/shield", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult postShield(Integer postId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			postService.shieldPost(postId);
		} catch (InputPostException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/post/handle", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult postHandle(String postIds) {
		AjaxResult ajaxResult = new AjaxResult();
		List<Long> list = new ArrayList<Long>();
		try {
			if (StringUtils.isNotEmpty(postIds)) {
				for (String str : postIds.split(",")) {
					list.add(Long.valueOf(str));
				}
			}
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		try {
			postService.handlePost(list);
		} catch (InputPostException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/post/query", method = RequestMethod.GET)
	public String queryPost(@RequestParam(defaultValue = "0") int type,
			@RequestParam(defaultValue = "0") int id, Model model) {
		if (type != 0 && id != 0) {
			model.addAttribute("views", postService.getpost(type, id));
		}
		return "cms/post/query";
	}
}

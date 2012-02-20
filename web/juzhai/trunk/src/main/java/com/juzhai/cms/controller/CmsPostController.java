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
			@RequestParam(defaultValue = "1") int pageId) {
		PagerManager pager = new PagerManager(pageId, 30,
				postService.countUnhandlePost());
		List<Post> list = postService.listUnhandlePost(pager.getFirstResult(),
				pager.getMaxResult());
		List<CmsPostView> listView = new ArrayList<CmsPostView>();
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			listView.add(new CmsPostView(post, cache));
		}
		model.addAttribute("postView", listView);
		model.addAttribute("pager", pager);
		return "/cms/post/unhandle";
	}

	@RequestMapping(value = "/show/post/shield", method = RequestMethod.GET)
	public String showPostShield(Model model,
			@RequestParam(defaultValue = "1") int pageId) {
		PagerManager pager = new PagerManager(pageId, 30,
				postService.countShieldPost());
		List<Post> list = postService.listShieldPost(pager.getFirstResult(),
				pager.getMaxResult());
		List<CmsPostView> listView = new ArrayList<CmsPostView>();
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			listView.add(new CmsPostView(post, cache));
		}
		model.addAttribute("postView", listView);
		model.addAttribute("pager", pager);
		return "/cms/post/shield";
	}

	@RequestMapping(value = "/show/post/handle", method = RequestMethod.GET)
	public String showPosthandle(Model model,
			@RequestParam(defaultValue = "1") int pageId) {
		PagerManager pager = new PagerManager(pageId, 30,
				postService.countHandlePost());
		List<Post> list = postService.listHandlePost(pager.getFirstResult(),
				pager.getMaxResult());
		List<CmsPostView> listView = new ArrayList<CmsPostView>();
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			listView.add(new CmsPostView(post, cache));
		}
		model.addAttribute("postView", listView);
		model.addAttribute("pager", pager);
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

	@RequestMapping(value = "/post/unshield", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult postUnShield(Integer postId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			postService.unShieldPost(postId);
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

}

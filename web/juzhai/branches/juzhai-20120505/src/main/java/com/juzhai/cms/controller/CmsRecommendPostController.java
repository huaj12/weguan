package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.cms.controller.view.CmsPostView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IRecommendPostService;

@Controller
@RequestMapping("/cms")
public class CmsRecommendPostController {
	@Autowired
	private IRecommendPostService recommendPostService;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = "/show/recommend/post", method = RequestMethod.GET)
	public String showRecommendPost(Model model) {
		List<CmsPostView> listView = new ArrayList<CmsPostView>();
		List<Post> list = recommendPostService.listRecommendPost();
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			listView.add(new CmsPostView(post, cache));
		}
		model.addAttribute("postView", listView);
		return "/cms/show_recommend_post";
	}

	@RequestMapping(value = "/update/recommend/post", method = RequestMethod.GET)
	public String updateRecommendPost(Model model) {
		recommendPostService.updateRecommendPost();
		return showRecommendPost(model);
	}

}

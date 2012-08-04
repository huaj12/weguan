package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.controller.view.CmsPostView;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IRecommendIdeaService;
import com.juzhai.post.service.IRecommendPostService;

@Controller
@RequestMapping("/cms")
public class CmsRecommendDataController {
	@Autowired
	private IRecommendPostService recommendPostService;
	@Autowired
	private IRecommendIdeaService recommendIdeaService;
	@Value("${recommend.idea.max.rows}")
	private int recommendIdeaMaxRows;
	@Value("${recommend.post.max.rows}")
	private int recommendPostMaxRows;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = "/show/recommend/post", method = RequestMethod.GET)
	public String showRecommendPost(Model model) {
		List<CmsPostView> listView = new ArrayList<CmsPostView>();
		List<Post> list = recommendPostService
				.listRecommendPost(recommendPostMaxRows);
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

	@RequestMapping(value = "/show/recommend/idea", method = RequestMethod.GET)
	public String showRecommendIdea(Model model) {
		List<Idea> list = recommendIdeaService
				.listRecommendIdea(recommendIdeaMaxRows);
		model.addAttribute("ideas", list);
		return "/cms/show_recommend_idea";
	}

	@RequestMapping(value = "/update/recommend/idea", method = RequestMethod.GET)
	public String updateRecommendIdea(Model model) {
		recommendIdeaService.updateRecommendIdea();
		return showRecommendIdea(model);
	}

	@RequestMapping(value = "/show/billboard/idea", method = RequestMethod.GET)
	public String showBillboardIdea(Model model) {
		List<Idea> list = recommendIdeaService.listRecentTopIdeas();
		model.addAttribute("ideas", list);
		return "/cms/show_billboard_idea";
	}

	@RequestMapping(value = "/update/billboard/idea", method = RequestMethod.GET)
	public String updateBillboardIdea(Model model) {
		// recommendIdeaService.updateRecentTopIdeas();
		return showBillboardIdea(model);
	}

	@RequestMapping(value = "/show/index/idea", method = RequestMethod.GET)
	public String showIdexIdea(Model model) {
		Set<Idea> list = recommendIdeaService.listIndexIdeas();
		model.addAttribute("ideas", list);
		return "/cms/show_index_idea";
	}

	@ResponseBody
	@RequestMapping(value = "/add/index/idea", method = RequestMethod.POST)
	public AjaxResult addIdexIdea(Model model,
			@RequestParam(defaultValue = "0") long ideaId) {
		recommendIdeaService.addIndexIdea(ideaId);
		return new AjaxResult();
	}

	@ResponseBody
	@RequestMapping(value = "/del/index/idea", method = RequestMethod.POST)
	public AjaxResult delIdexIdea(Model model,
			@RequestParam(defaultValue = "0") long ideaId) {
		recommendIdeaService.removeIndexIdea(ideaId);
		return new AjaxResult();
	}
}

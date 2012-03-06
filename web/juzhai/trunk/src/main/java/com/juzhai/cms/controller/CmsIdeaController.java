package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.model.Category;
import com.juzhai.cms.controller.form.IdeaForm;
import com.juzhai.cms.controller.view.CmsIdeaView;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;

@Controller
@RequestMapping("/cms")
public class CmsIdeaController extends BaseController {

	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/show/idea", method = RequestMethod.GET)
	public String showIdea(Model model,
			@RequestParam(defaultValue = "1") int pageId) {
		PagerManager pager = new PagerManager(pageId, 30,
				ideaService.countIdeaByCity(0l));
		List<Idea> list = ideaService.listIdeaByCity(0l,
				ShowIdeaOrder.HOT_TIME, pager.getFirstResult(),
				pager.getMaxResult());
		List<CmsIdeaView> ideaViews = new ArrayList<CmsIdeaView>();
		for (Idea idea : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(idea
					.getFirstUid());
			String username = null;
			if (cache != null) {
				username = cache.getNickname();
			}
			Category cat = com.juzhai.post.InitData.CATEGORY_MAP.get(idea
					.getCategoryId());
			String categoryName = null;
			if (cat != null) {
				categoryName = cat.getName();
			}
			ideaViews.add(new CmsIdeaView(idea, username, categoryName));
		}
		model.addAttribute("ideaViews", ideaViews);
		model.addAttribute("pager", pager);
		return "/cms/idea/list";
	}

	@RequestMapping(value = "/show/idea/add", method = RequestMethod.GET)
	public String showIdeaAdd(Model model, String msg, IdeaForm ideaForm,
			Long postId) {
		if (postId != null) {
			Post post = postService.getPostById(postId);
			if (post != null) {
				ideaForm.setContent(post.getContent());
				try {
					ideaForm.setDateString(DateFormat.SDF.format(post
							.getDateTime()));
				} catch (Exception e) {
				}
				ideaForm.setPic(post.getPic());
				ideaForm.setPlace(post.getPlace());
				ideaForm.setPostId(postId);
				ideaForm.setCreateUid(post.getCreateUid());
			}
		}
		model.addAttribute("ideaForm", ideaForm);
		model.addAttribute("msg", msg);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("categoryList",
				com.juzhai.post.InitData.CATEGORY_MAP.values());
		return "/cms/idea/add";
	}

	@RequestMapping(value = "/show/idea/update", method = RequestMethod.GET)
	public String showIdeaUpdate(Model model, Long id, String msg,
			IdeaForm ideaForm) {
		Idea idea = ideaService.getIdeaById(id);
		if (idea != null) {
			if (ideaForm == null || ideaForm.getIdeaId() == null) {
				ideaForm = new IdeaForm();
				ideaForm.setCategoryId(idea.getCategoryId());
				ideaForm.setCity(idea.getCity());
				ideaForm.setGender(idea.getGender());
				ideaForm.setRandom(idea.getRandom());
				ideaForm.setContent(idea.getContent());
				try {
					ideaForm.setDateString(DateFormat.SDF.format(idea.getDate()));
				} catch (Exception e) {
				}
				ideaForm.setPlace(idea.getPlace());
				ideaForm.setPic(idea.getPic());
				ideaForm.setIdeaId(id);
				ideaForm.setLink(idea.getLink());
			}
			model.addAttribute("ideaForm", ideaForm);
			model.addAttribute("idea", idea);
			model.addAttribute("msg", msg);
			model.addAttribute("citys", InitData.CITY_MAP.values());
			loadCategoryList(model);
		}
		return "/cms/idea/update";
	}

	@RequestMapping(value = "/add/idea", method = RequestMethod.POST)
	public String addIdea(IdeaForm ideaForm, HttpServletRequest request,
			Model model) {
		try {
			ideaService.addIdea(ideaForm);
		} catch (Exception e) {
			String msg = messageSource.getMessage(e.getMessage(), null,
					Locale.SIMPLIFIED_CHINESE);
			return showIdeaAdd(model, msg, ideaForm, null);
		}
		return showIdea(model, 1);
	}

	@RequestMapping(value = "/update/idea", method = RequestMethod.POST)
	public String updateIdea(Model model, IdeaForm ideaForm) {
		try {
			ideaService.updateIdea(ideaForm);
		} catch (Exception e) {
			return showIdeaUpdate(model, ideaForm.getIdeaId(),
					messageSource.getMessage(e.getMessage(), null,
							Locale.SIMPLIFIED_CHINESE), ideaForm);
		}
		return showIdea(model, 1);
	}

	@RequestMapping(value = "/idea/del", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult ideaDel(@RequestParam(defaultValue = "0") long ideaId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ideaService.removeIdea(ideaId);
		} catch (Exception e) {
			ajaxResult.setError(e.getMessage(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/operate/idea/random", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult operateIdeaRandow(
			@RequestParam(defaultValue = "0") long ideaId,
			@RequestParam(defaultValue = "false") boolean random) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ideaService.ideaRandom(ideaId, random);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/operate/idea/window", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult operateIdeaWindow(
			@RequestParam(defaultValue = "0") long ideaId,
			@RequestParam(defaultValue = "false") boolean window) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ideaService.ideaWindow(ideaId, window);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/operate/idea/update/window", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult operateIdeaUpdateWindow() {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ideaService.ideaWindowSort();
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

}

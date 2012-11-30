package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.controller.form.IdeaForm;
import com.juzhai.cms.controller.view.CmsIdeaView;
import com.juzhai.cms.controller.view.CmsRawIdeaView;
import com.juzhai.common.InitData;
import com.juzhai.common.model.City;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.idea.bean.ShowIdeaOrder;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.post.exception.InputRawIdeaException;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.RawIdea;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;
import com.juzhai.post.service.IRawIdeaService;
import com.juzhai.post.service.impl.IdeaDetailService;
import com.juzhai.spider.share.exception.SpiderIdeaException;
import com.juzhai.spider.share.service.ISpiderIdeaService;

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
	@Autowired
	private IdeaDetailService ideaDetailService;
	@Autowired
	private IRawIdeaService rawIdeaService;
	@Autowired
	private ISpiderIdeaService spiderIdeaService;

	@RequestMapping(value = "/show/idea", method = RequestMethod.GET)
	public String showIdea(Model model,
			@RequestParam(defaultValue = "1") int pageId, Long city,
			@RequestParam(defaultValue = "0") long categoryId, Boolean window,
			Boolean random,
			@RequestParam(defaultValue = "false") boolean defunct) {
		PagerManager pager = new PagerManager(pageId, 20,
				ideaService.countCmsIdeaByCityAndCategory(window, city,
						categoryId, random, defunct));
		List<Idea> list = ideaService.listCmsIdeaByCityAndCategory(window,
				city, categoryId, random, defunct, ShowIdeaOrder.HOT_TIME,
				pager.getFirstResult(), pager.getMaxResult());
		List<CmsIdeaView> ideaViews = assembleCmsIdeaView(list);
		model.addAttribute("ideaViews", ideaViews);
		model.addAttribute("pager", pager);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("categoryList",
				com.juzhai.post.InitData.CATEGORY_MAP.values());
		model.addAttribute("city", city);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("window", window);
		model.addAttribute("random", random);
		model.addAttribute("defunct", defunct);
		return "/cms/idea/list";
	}

	private List<CmsIdeaView> assembleCmsIdeaView(List<Idea> list) {
		List<CmsIdeaView> ideaViews = new ArrayList<CmsIdeaView>();
		for (Idea idea : list) {
			ProfileCache createUser = profileService.getProfileCacheByUid(idea
					.getCreateUid());
			ideaViews.add(new CmsIdeaView(idea, createUser));
		}
		return ideaViews;
	}

	@RequestMapping(value = "/show/idea/add", method = RequestMethod.GET)
	public String showIdeaAdd(Model model, String msg, IdeaForm ideaForm,
			Long postId) {
		if (postId != null) {
			Post post = postService.getPostById(postId);
			if (post != null) {
				ideaForm.setContent(post.getContent());
				try {
					ideaForm.setEndDateString(DateFormat.SDF_TIME.format(post
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
				ideaForm.setTown(idea.getTown());
				City city = InitData.CITY_MAP.get(idea.getCity());
				if (city != null) {
					ideaForm.setProvince(city.getProvinceId());
				}
				ideaForm.setGender(idea.getGender());
				ideaForm.setRandom(idea.getRandom());
				ideaForm.setContent(idea.getContent());
				try {
					if (idea.getStartTime() != null) {
						ideaForm.setStartDateString(DateFormat.SDF_TIME
								.format(idea.getStartTime()));
					}
					if (idea.getEndTime() != null) {
						ideaForm.setEndDateString(DateFormat.SDF_TIME
								.format(idea.getEndTime()));
					}
				} catch (Exception e) {
				}
				ideaForm.setPlace(idea.getPlace());
				ideaForm.setPic(idea.getPic());
				ideaForm.setIdeaId(id);
				ideaForm.setLink(idea.getLink());
				ideaForm.setBuyLink(idea.getBuyLink());
				ideaForm.setCharge(idea.getCharge());
				ideaForm.setTown(idea.getTown());
				ideaForm.setDetail(idea.getDetail());
			}
			model.addAttribute("ideaForm", ideaForm);
			model.addAttribute("idea", idea);
			model.addAttribute("msg", msg);
			model.addAttribute("detail", ideaDetailService.getIdeaDetail(id));
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
		return showIdea(model, 1, null, 0, null, null, false);
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
		return showIdea(model, 1, null, 0, null, null, false);
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

	@RequestMapping(value = "/idea/defunct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult defunctIdea(@RequestParam(defaultValue = "0") long ideaId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ideaService.defunctIdea(ideaId);
		} catch (Exception e) {
			ajaxResult.setError(e.getMessage(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/idea/canceldefunct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult cancelDefunctIdea(
			@RequestParam(defaultValue = "0") long ideaId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ideaService.cancelDefunctIdea(ideaId);
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

	@RequestMapping(value = "/del/rawIdea", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult delRawIdea(@RequestParam(defaultValue = "0") long id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			rawIdeaService.delRawIdea(id);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/list/rawIdea", method = RequestMethod.GET)
	public String listRawIdea(@RequestParam(defaultValue = "1") int pageId,
			Model model) {
		PagerManager pager = new PagerManager(pageId, 20,
				rawIdeaService.countRawIdea());
		List<RawIdea> list = rawIdeaService.listRawIdea(pager.getFirstResult(),
				pager.getMaxResult());
		List<CmsRawIdeaView> views = new ArrayList<CmsRawIdeaView>(list.size());
		for (RawIdea rawIdea : list) {
			ProfileCache createUser = profileService
					.getProfileCacheByUid(rawIdea.getCreateUid());
			CmsRawIdeaView view = new CmsRawIdeaView(rawIdea, createUser, null);
			views.add(view);
		}
		model.addAttribute("pager", pager);
		model.addAttribute("rawIdeaViews", views);
		return "/cms/idea/list_rawIdea";
	}

	@RequestMapping(value = "/list/correction/rawIdea", method = RequestMethod.GET)
	public String listCorrectionRawIdea(
			@RequestParam(defaultValue = "1") int pageId, Model model) {
		PagerManager pager = new PagerManager(pageId, 20,
				rawIdeaService.countCorrectionRawIdea());
		List<RawIdea> list = rawIdeaService.listCorrectionRawIdea(
				pager.getFirstResult(), pager.getMaxResult());
		List<CmsRawIdeaView> views = new ArrayList<CmsRawIdeaView>(list.size());
		for (RawIdea rawIdea : list) {
			ProfileCache createUser = profileService
					.getProfileCacheByUid(rawIdea.getCreateUid());
			ProfileCache correctionUser = profileService
					.getProfileCacheByUid(rawIdea.getCorrectionUid());
			CmsRawIdeaView view = new CmsRawIdeaView(rawIdea, createUser,
					correctionUser);
			views.add(view);
		}
		model.addAttribute("pager", pager);
		model.addAttribute("rawIdeaViews", views);
		return "/cms/idea/list_correction_rawIdea";
	}

	@RequestMapping(value = "/show/rawIdea/update", method = RequestMethod.GET)
	public String showRawIdeaUpdate(Model model, String msg,
			RawIdeaForm rawIdeaForm, Long rawIdeaId) {
		RawIdea rawIdea = rawIdeaService.getRawIdea(rawIdeaId);
		if (rawIdea != null) {
			if (rawIdeaForm == null || rawIdeaForm.getId() == null) {
				rawIdeaForm = new RawIdeaForm();
				rawIdeaForm.setCategoryId(rawIdea.getCategoryId());
				rawIdeaForm.setCity(rawIdea.getCity());
				rawIdeaForm.setContent(rawIdea.getContent());
				try {
					rawIdeaForm.setStartDateString(DateFormat.SDF_TIME
							.format(rawIdea.getStartTime()));
					rawIdeaForm.setEndDateString(DateFormat.SDF_TIME
							.format(rawIdea.getEndTime()));
				} catch (Exception e) {
				}
				rawIdeaForm.setPlace(rawIdea.getPlace());
				rawIdeaForm.setPic(rawIdea.getPic());
				rawIdeaForm.setLink(rawIdea.getLink());
				rawIdeaForm.setBuyLink(rawIdea.getBuyLink());
				rawIdeaForm.setCharge(rawIdea.getCharge());
				rawIdeaForm.setTown(rawIdea.getTown());
				rawIdeaForm.setDetail(rawIdea.getDetail());
				rawIdeaForm.setCreateUid(rawIdea.getCreateUid());
				City city = InitData.CITY_MAP.get(rawIdea.getCity());
				if (city != null) {
					rawIdeaForm.setProvince(city.getProvinceId());
				}
				rawIdeaForm.setId(rawIdeaId);
			}
		}
		model.addAttribute("rawIdeaForm", rawIdeaForm);
		model.addAttribute("msg", msg);
		loadCategoryList(model);
		return "/cms/idea/raw_idea_update";
	}

	@RequestMapping(value = "/update/rawIdea", method = RequestMethod.POST)
	public String updateRawIdea(RawIdeaForm rawIdeaForm,
			HttpServletRequest request, Model model, String detail) {
		try {
			rawIdeaService.passRawIdea(rawIdeaForm);
		} catch (InputRawIdeaException e) {
			String msg = messageSource.getMessage(e.getErrorCode(), null,
					Locale.SIMPLIFIED_CHINESE);
			return showRawIdeaUpdate(model, msg, rawIdeaForm, 0l);
		}
		return listRawIdea(1, model);
	}

	@RequestMapping(value = "/show/correction/rawIdea", method = RequestMethod.GET)
	public String showCorrectionRawIdea(Model model, Long ideaId, Long rawIdeaId) {
		RawIdea rawIdea = rawIdeaService.getRawIdea(rawIdeaId);
		model.addAttribute("rawIdea", rawIdea);
		try {
			model.addAttribute("startDate",
					(DateFormat.SDF_TIME.format(rawIdea.getStartTime())));
			model.addAttribute("endDate",
					(DateFormat.SDF_TIME.format(rawIdea.getEndTime())));
		} catch (Exception e) {
		}
		return showIdeaUpdate(model, ideaId, null, null);
	}

	@RequestMapping(value = "/list/ideaWindow", method = RequestMethod.GET)
	public String listIdeaWindow(Model model,
			@RequestParam(defaultValue = "1") int pageId,
			@RequestParam(defaultValue = "0") long city,
			@RequestParam(defaultValue = "0") long categoryId) {
		PagerManager pager = new PagerManager(pageId, 50,
				ideaService.countIdeaWindow(city, categoryId));
		List<Idea> list = ideaService.listIdeaWindow(city, categoryId,
				pager.getFirstResult(), pager.getMaxResult());
		List<CmsIdeaView> ideaViews = assembleCmsIdeaView(list);
		model.addAttribute("ideaViews", ideaViews);
		model.addAttribute("pager", pager);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("categoryList",
				com.juzhai.post.InitData.CATEGORY_MAP.values());
		model.addAttribute("city", city);
		model.addAttribute("categoryId", categoryId);
		return "/cms/idea/list_idea_window";
	}

	@RequestMapping(value = "/share/idea", method = RequestMethod.GET)
	public String shareIdea(Model model, String url) {
		String msg = null;
		RawIdeaForm rawIdeaForm = null;
		IdeaForm ideaForm = null;
		try {
			String result = spiderIdeaService.crawl(url);
			rawIdeaForm = JackSonSerializer.toBean(result, RawIdeaForm.class);
		} catch (SpiderIdeaException e) {
			msg = messageSource.getMessage(e.getErrorCode(), null,
					Locale.SIMPLIFIED_CHINESE);
		} catch (JsonGenerationException e) {
			msg = messageSource.getMessage("00001", null,
					Locale.SIMPLIFIED_CHINESE);
		}
		if (rawIdeaForm != null) {
			ideaForm = new IdeaForm();
			ideaForm.setProvince(rawIdeaForm.getProvince());
			ideaForm.setCity(rawIdeaForm.getCity());
			ideaForm.setTown(rawIdeaForm.getTown());
			ideaForm.setCategoryId(rawIdeaForm.getCategoryId());
			ideaForm.setContent(rawIdeaForm.getContent());
			ideaForm.setStartDateString(rawIdeaForm.getStartDateString());
			ideaForm.setEndDateString(rawIdeaForm.getEndDateString());
			ideaForm.setPlace(rawIdeaForm.getPlace());
			ideaForm.setPic(rawIdeaForm.getPic());
			ideaForm.setLink(rawIdeaForm.getLink());
			ideaForm.setBuyLink(rawIdeaForm.getBuyLink());
			ideaForm.setCharge(rawIdeaForm.getCharge());
			ideaForm.setDetail(rawIdeaForm.getDetail());
			ideaForm.setPicWeb(rawIdeaForm.getPicWeb());
		}
		model.addAttribute("ideaForm", ideaForm);
		model.addAttribute("msg", msg);
		model.addAttribute("categoryList",
				com.juzhai.post.InitData.CATEGORY_MAP.values());
		return "/cms/idea/add";
	}

	private void loadCategoryList(Model model) {
		model.addAttribute("categoryList",
				com.juzhai.post.InitData.CATEGORY_MAP.values());
	}
}

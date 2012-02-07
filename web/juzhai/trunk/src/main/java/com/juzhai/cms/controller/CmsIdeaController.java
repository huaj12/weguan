package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.cms.controller.form.AddIdeaForm;
import com.juzhai.cms.controller.form.IdeaForm;
import com.juzhai.cms.controller.view.CmsIdeaView;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.City;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.exception.InputIdeaException;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

@Controller
@RequestMapping("/cms")
public class CmsIdeaController {

	@Autowired
	private IIdeaService ideaService;
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
			String username=null;
			if(cache!=null){
				username=cache.getNickname();
			}
			//TODO (done) jstl可以获取cityName
			ideaViews.add(new CmsIdeaView(idea, username));
		}
		model.addAttribute("ideaViews", ideaViews);
		model.addAttribute("pager", pager);
		return "/cms/idea/list";
	}

	@RequestMapping(value = "/show/idea/add", method = RequestMethod.GET)
	public String showIdeaAdd(Model model, String msg,AddIdeaForm addIdeaForm) {
		//TODO (done) form不能用？
		model.addAttribute("addIdeaForm", addIdeaForm);
		model.addAttribute("msg", msg);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		return "/cms/idea/add";
	}

	@RequestMapping(value = "/show/idea/update", method = RequestMethod.GET)
	public String showIdeaUpdate(Model model, Long ideaId, String msg) {
		Idea idea = ideaService.getIdeaById(ideaId);
		//TODO (done) 为什么不用idea返回到页面？
		if (idea != null) {
			model.addAttribute("idea", idea);
			model.addAttribute("msg", msg);
			model.addAttribute("citys", InitData.CITY_MAP.values());
		}
		return "/cms/idea/update";
	}

	@RequestMapping(value = "/add/idea", method = RequestMethod.POST)
	public ModelAndView addIdea(IdeaForm ideaForm, HttpServletRequest request) {
		ModelMap mmap = new ModelMap();
		String url = "/cms/show/idea";
		try {
			ideaService.addIdea(ideaForm);
		} catch (Exception e) {
			url = "/cms/show/idea/add";
			mmap.addAttribute("msg", messageSource.getMessage(e.getMessage(),
					null, Locale.SIMPLIFIED_CHINESE));
		}
		return new ModelAndView("redirect:" + url, mmap);
	}

	@RequestMapping(value = "/update/idea", method = RequestMethod.POST)
	public ModelAndView updateIdea(IdeaForm ideaForm) {
		ModelMap mmap = new ModelMap();
		mmap.addAttribute("ideaId", ideaForm.getIdeaId());
		String url = "/cms/show/idea";
		try {
			ideaService.updateIdea(ideaForm);
		} catch (Exception e) {
			url = "/cms/show/idea/update";
			mmap.addAttribute("msg", messageSource.getMessage(e.getMessage(),
					null, Locale.SIMPLIFIED_CHINESE));
		}
		return new ModelAndView("redirect:" + url, mmap);
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

}

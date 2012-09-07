package com.juzhai.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.search.exception.InputSearchHotException;
import com.juzhai.search.service.ISearchHotService;

@Controller
@RequestMapping("/cms")
public class CmsSearchHotController {
	@Autowired
	private ISearchHotService searchHotService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/show/searchHot", method = RequestMethod.GET)
	public String showSearchHot(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") long city,
			@RequestParam(defaultValue = "1") int pageId) {
		PagerManager pager = new PagerManager(pageId, 40,
				searchHotService.countSearchHotByCity(city));
		model.addAttribute(
				"hots",
				searchHotService.getSearchHotByCity(city,
						pager.getFirstResult(), pager.getMaxResult()));
		model.addAttribute("city", city);
		model.addAttribute("pager", pager);
		return "cms/search_hot";
	}

	@RequestMapping(value = "/add/searchHot", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult add(String name,
			@RequestParam(defaultValue = "0") long city) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			searchHotService.add(name, city);
		} catch (InputSearchHotException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/del/searchHot", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult del(@RequestParam(defaultValue = "0") long id) {
		searchHotService.delete(id);
		return new AjaxResult();
	}

	@RequestMapping(value = "/update/searchWordHot", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult updateSearchWordHot() {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			searchHotService.updateWordHot();
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

}

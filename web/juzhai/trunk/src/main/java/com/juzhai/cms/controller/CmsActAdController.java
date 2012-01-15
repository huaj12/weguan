package com.juzhai.cms.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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

import com.juzhai.act.exception.ActAdInputException;
import com.juzhai.act.service.IActAdService;
import com.juzhai.act.service.IActService;
import com.juzhai.cms.exception.RawAdInputException;
import com.juzhai.cms.bean.AdSource;
import com.juzhai.cms.model.RawAd;
import com.juzhai.cms.service.IRawAdService;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.passport.InitData;

@Controller
@RequestMapping("/cms")
public class CmsActAdController {
	@Autowired
	private IRawAdService rawAdService;
	@Autowired
	private IActAdService actAdService;
	@Autowired
	private IActService actService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/show/raw/ad", method = RequestMethod.GET)
	public String showRawAd(String msg, Model model) {
		model.addAttribute("msg", msg);
		return "/cms/show_raw_ad";
	}

	@RequestMapping(value = "/raw/ad/import", method = RequestMethod.POST)
	public ModelAndView rawAdImport(HttpServletRequest request, Model model,
			@RequestParam("rawAd") MultipartFile rawAd) {
		ModelMap mmap = new ModelMap();
		String url = "/cms/show/ad/manager";
		int index = 0;
		try {
			index = rawAdService.importAd(rawAd);
			mmap.addAttribute("msg", index);
		} catch (RawAdInputException e) {
			url = "/cms/show/raw/ad";
			mmap.addAttribute("msg", messageSource.getMessage(e.getErrorCode(),
					null, Locale.SIMPLIFIED_CHINESE));
		}
		return new ModelAndView("redirect:" + url, mmap);
	}

	@RequestMapping(value = "/show/ad/manager")
	public String showAdManager(String msg, Model model,
			@RequestParam(defaultValue = "1") int pageId, Long cityId,
			String source, String status, String category) {
		PagerManager pager = new PagerManager(pageId, 20,
				rawAdService.countSearchRawAd(status, cityId, source, category));
		List<RawAd> ads = rawAdService.searchRawAd(status, cityId, source,
				category, pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("msg", msg);
		model.addAttribute("ads", ads);
		model.addAttribute("pager", pager);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("adSources", AdSource.values());
		model.addAttribute("cityMap", InitData.CITY_MAP);
		model.addAttribute("cityId", cityId);
		model.addAttribute("source", source);
		model.addAttribute("status", status);
		model.addAttribute("category", category);
		return "/cms/ad_manager";
	}

	@RequestMapping(value = "/add/act/ad", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addActAd(HttpServletRequest request, Model model,
			String actName, @RequestParam(defaultValue = "0") long rawAdId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			actAdService.createActAd(actName, rawAdId);
		} catch (ActAdInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/remove/act/ad", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult removeActAd(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") long actAdId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			actAdService.remove(actAdId);
		} catch (ActAdInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/remove/raw/ad", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult removeRawAd(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") long rawAdId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			rawAdService.remove(rawAdId);
		} catch (RawAdInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/ajax/show/act/ads", method = RequestMethod.GET)
	public String showActAds(Long rawAdId, Model model) {
		if (rawAdId != null) {
			model.addAttribute("acts", actAdService.getActByRawAd(rawAdId));
			model.addAttribute("rawId", rawAdId);
		}
		return "/cms/ajax/asd_list";
	}

	// TODO (review) 项目优惠信息列表单独做
}

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.RawAd;
import com.juzhai.cms.exception.RawAdInputException;
import com.juzhai.cms.service.IRawAdService;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.passport.InitData;

@Controller
@RequestMapping("/cms")
public class CmsActAdController {
	@Autowired
	private IRawAdService rawAdService; 
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/show/raw/ad",method = RequestMethod.GET)
	public String showRawAd(String msg,Model model){
		model.addAttribute("msg", msg);
		return "/cms/show_raw_ad";
	}
	
	@RequestMapping(value = "/raw/ad/import",method = RequestMethod.POST)
	public ModelAndView rawAdImport(HttpServletRequest request, Model model,
			@RequestParam("rawAd") MultipartFile rawAd) {
		ModelMap mmap = new ModelMap();
		String url="/show/ad/manager";
		int index=0;
		try {
			index=rawAdService.importAd(rawAd);
			mmap.addAttribute("msg",index);
		} catch (RawAdInputException e) {
			url="/cms/show/raw/ad";
			mmap.addAttribute("msg",messageSource.getMessage(e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
		}
		return new ModelAndView("redirect:"+url, mmap);
	}
	
	@RequestMapping(value = "/show/ad/manager",method = RequestMethod.GET)
	public String showAdManager(String msg,Model model,@RequestParam(defaultValue = "1") int pageId){
		PagerManager pager = new PagerManager(pageId, 20,
				rawAdService.countRawAd());
		List<RawAd> ads=rawAdService.showRawAdList(pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("msg", msg);
		model.addAttribute("ads", ads);
		model.addAttribute("pager", pager);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("cityMap", InitData.CITY_MAP);
		return "/cms/ad_manager";
	}
	
}

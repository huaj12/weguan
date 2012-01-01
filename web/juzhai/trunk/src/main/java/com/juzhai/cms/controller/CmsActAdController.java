package com.juzhai.cms.controller;

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

import com.juzhai.cms.exception.RawAdInputException;
import com.juzhai.cms.service.IRawAdService;

@Controller
@RequestMapping("/cms")
public class CmsActAdController {
	@Autowired
	private IRawAdService rawAdService; 
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/show/raw/ad",method = RequestMethod.GET)
	public String showRawAd(String errorMsg,String successMsg,Model model){
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("successMsg", successMsg);
		return "/cms/show_raw_ad";
	}
	
	@RequestMapping(value = "/raw/ad/import",method = RequestMethod.POST)
	public ModelAndView rawAdImport(HttpServletRequest request, Model model,
			@RequestParam("rawAd") MultipartFile rawAd) {
		ModelMap mmap = new ModelMap();
		int index=0;
		try {
			index=rawAdService.importAd(rawAd);
		} catch (RawAdInputException e) {
			mmap.addAttribute("errorMsg",messageSource.getMessage(e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
		}
		mmap.addAttribute("successMsg",index);
		return new ModelAndView("redirect:/cms/show/raw/ad", mmap);
	}
}

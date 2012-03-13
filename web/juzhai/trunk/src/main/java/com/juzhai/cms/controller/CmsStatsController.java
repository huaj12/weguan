package com.juzhai.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.cms.service.IStatsService;

@Controller
@RequestMapping("/cms")
public class CmsStatsController {

	@Autowired
	private IStatsService statsService;

	@RequestMapping(value = "/showStats", method = RequestMethod.GET)
	public String showStats(HttpServletRequest request, String beginDate,
			String endDate, Model model) {
		model.addAttribute("CounterStats",
				statsService.getStatsCunter(beginDate, endDate));
		if (StringUtils.isNotEmpty(beginDate))
			model.addAttribute("beginDate", beginDate);
		if (StringUtils.isNotEmpty(endDate))
			model.addAttribute("endDate", endDate);
		return "cms/show_stats";
	}

}

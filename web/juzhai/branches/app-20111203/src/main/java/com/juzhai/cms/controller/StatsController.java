package com.juzhai.cms.controller;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.web.AjaxResult;
import com.juzhai.stats.counter.service.ICounter;

@Controller
@RequestMapping("/cms")
public class StatsController {

	@Autowired
	private ICounter recommendWantCounter;

	@RequestMapping(value = "/recommendWantStats")
	public String recommendClickStat(HttpServletRequest request) {
		return "cms/recommend_want_stats";
	}

	@ResponseBody
	@RequestMapping(value = "/showRecommndWantStats", method = RequestMethod.POST)
	public AjaxResult showRecommndWantStats(HttpServletRequest request,
			String queryDate) {
		Date date;
		try {
			date = DateUtils
					.parseDate(queryDate, new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			return null;
		}
		long count = recommendWantCounter.get(null, date);
		AjaxResult result = new AjaxResult();
		result.setResult(count);
		result.setSuccess(true);
		return result;
	}
}

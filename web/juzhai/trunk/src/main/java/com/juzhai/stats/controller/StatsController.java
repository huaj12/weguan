package com.juzhai.stats.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.stats.counter.service.ICounter;

@Controller
@RequestMapping("stats")
public class StatsController {
	@Autowired
	private ICounter openEmailCounter;

	@RequestMapping(value = "/openEmail", method = RequestMethod.GET)
	@ResponseBody
	public String statOpenEmail() {
		openEmailCounter.incr(null, 1);
		return StringUtils.EMPTY;
	}
}

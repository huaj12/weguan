package com.juzhai.stats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("stats")
public class ApplicationStatsController {

	@RequestMapping(value = "{application}", method = RequestMethod.GET)
	public String stats(@PathVariable String application) {
		return "common/stats/" + application;
	}
}

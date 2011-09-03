package com.juzhai.act.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.service.IActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.util.JackSonSerializer;

@Controller
public class ActSearchController extends BaseController {

	@Autowired
	private IActService actService;
	@Value("${autoComplete.result.count}")
	private int autoCompleteResultCount = 10;

	@RequestMapping(value = "autoSearch", method = RequestMethod.GET)
	public String autoSearch(String qs, Model model) {
		List<String> results = Collections.emptyList();
		if (StringUtils.isNotEmpty(qs)) {
			results = actService.indexSearchName(qs, autoCompleteResultCount);
		}
		try {
			return JackSonSerializer.toString(results);
		} catch (JsonGenerationException e) {
			log.error(e.getMessage(), e);
			return "[]";
		}
	}
}

package com.juzhai.act.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.service.IActService;
import com.juzhai.core.controller.BaseController;

@Deprecated
public class ActSearchController extends BaseController {

	@Autowired
	private IActService actService;

	// @Value("${autoComplete.result.count}")
	// private int autoCompleteResultCount = 10;

	@RequestMapping(value = "autoSearch", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> autoSearch(String q, int limit, Model model) {
		if (log.isDebugEnabled()) {
			log.debug("search q:" + q + ", limit:" + limit + ".");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<String> queryResults = actService.indexSearchName(q, limit);
			for (String name : queryResults) {
				Map<String, Object> nameMap = new HashMap<String, Object>();
				nameMap.put("name", name);
				result.add(nameMap);
			}
		}
		// try {
		return result;
		// } catch (JsonGenerationException e) {
		// log.error(e.getMessage(), e);
		// return Collections.emptyList();
		// }
	}
}

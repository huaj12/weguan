package com.juzhai.index.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.passport.InitData;

@Controller
public class IndexController {
	private final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request, Model model) {
		model.addAttribute("tpMap", InitData.TP_MAP);
		return "index/welcome";
	}

	// @RequestMapping(value = "createIndex", method = RequestMethod.GET)
	// @ResponseBody
	// public String createIndex(HttpServletRequest request) {
	// for (Act act : com.juzhai.act.InitData.ACT_MAP.values()) {
	// try {
	// actIndexer.addIndex(act, true);
	// } catch (CorruptIndexException e) {FF
	// log.error("createIndex is CorruptIndexException", e);
	// } catch (IOException e) {
	// log.error("CorruptIndexException is IOException", e);
	// }
	// }
	// return "success";
	// }
}

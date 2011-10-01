package com.juzhai.index.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.index.CorruptIndexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.model.Act;
import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.passport.InitData;

@Controller
public class IndexController {

	@Autowired
	private Indexer<Act> actIndexer;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request, Model model) {
		model.addAttribute("tpMap", InitData.TP_MAP);
		return "index/welcome";
	}

	@RequestMapping(value = "createIndex", method = RequestMethod.GET)
	@ResponseBody
	public String createIndex(HttpServletRequest request) {
		for (Act act : com.juzhai.act.InitData.ACT_MAP.values()) {
			try {
				actIndexer.addIndex(act, true);
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "success";
	}
}

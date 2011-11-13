package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.ISynonymActService;
import com.juzhai.cms.mapper.AddActActionMapper;
import com.juzhai.cms.mapper.SearchActActionMapper;
import com.juzhai.cms.model.AddActAction;
import com.juzhai.cms.model.SearchActAction;
import com.juzhai.cms.service.impl.UserActionService;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;

@Controller
@RequestMapping("/cms")
public class UserActionController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IActService actService;
	@Autowired
	private UserActionService userActionService;
	@Autowired
	private SearchActActionMapper searchActActionMapper;
	@Autowired
	private AddActActionMapper addActActionMapper;
	@Autowired
	private ISynonymActService synonymActService;

	@RequestMapping(value = "/showSearchActAction", method = RequestMethod.GET)
	public String showSearchActAction(HttpServletRequest request, Model model,
			Integer pageId) {
		if (pageId == null) {
			pageId = 1;
		}
		PagerManager pager = new PagerManager(pageId, 1,
				userActionService.getSearchActActionCount());
		List<SearchActAction> list = userActionService.getSearchActAction(
				pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("lists", list);
		model.addAttribute("pager", pager);
		return "cms/searchActActionList";
	}
	
	@RequestMapping(value = "/showAddActAction", method = RequestMethod.GET)
	public String showAddActAction(HttpServletRequest request, Model model,
			Integer pageId) {
		if (pageId == null) {
			pageId = 1;
		}
		PagerManager pager = new PagerManager(pageId, 1,
				userActionService.getAddActActionCount());
		List<AddActAction> list = userActionService.getAddActAction(
				pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("lists", list);
		model.addAttribute("pager", pager);
		return "cms/addActActionList";
	}

	@RequestMapping(value = "/cmsSynonymAct")
	@ResponseBody
	public AjaxResult cmsSynonymAct(HttpServletRequest request, String name,
			String actName, Model model) {
		AjaxResult result = new AjaxResult();
		try {
			Act act = actService.getActByName(actName);
			if (act == null) {
				result.setSuccess(false);
				result.setErrorCode("-1");
			} else {
				if (synonymActService.synonymAct(name, actName)) {
					result.setSuccess(true);
				} else {
					result.setSuccess(false);
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("cmsSynonymAct is error", e);
		}
		return result;
	}

	@RequestMapping(value = "/deleteAddActAction", method = RequestMethod.GET)
	public String deleteAddActAction(Long id) {
		if (id != null) {
			addActActionMapper.deleteByPrimaryKey(id);
		}
		return null;
	}

	@RequestMapping(value = "/deleteSearchActAction", method = RequestMethod.GET)
	public String deleteSearchActAction(Long id) {
		if (id != null) {
			searchActActionMapper.deleteByPrimaryKey(id);
		}
		return null;
	}
}

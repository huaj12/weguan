package com.juzhai.cms.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IHotActService;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;

@Controller
@RequestMapping("/cms")
public class CmsHotActController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IHotActService hotActService;

	@RequestMapping(value = "/addHotAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addHotAct(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") long actId, String actName) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actId > 0) {
			hotActService.activeHotAct(actId);
			ajaxResult.setSuccess(true);
		} else if (StringUtils.isNotEmpty(actName)) {
			if (hotActService.activeHotAct(actName.trim())) {
				ajaxResult.setSuccess(true);
			} else {
				ajaxResult.setSuccess(false);
				ajaxResult.setErrorInfo("act[" + actName + "] is not exist.");
			}
		} else {
			ajaxResult.setSuccess(false);
			ajaxResult.setErrorInfo("system error.");
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/cancelHotAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult cancelHotAct(HttpServletRequest request, Model model,
			long actId) {
		AjaxResult ajaxResult = new AjaxResult();
		hotActService.cancelHotAct(actId);
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
	
	@RequestMapping(value = "/deleteHotAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteHotAct(HttpServletRequest request, Model model,
			long actId) {
		AjaxResult ajaxResult = new AjaxResult();
		hotActService.deleteHotAct(actId);
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	@RequestMapping(value = "/showHotAct", method = RequestMethod.GET)
	public String showHotAct(HttpServletRequest request, Model model,
			boolean active, @RequestParam(defaultValue = "1") int page) {
		PagerManager pager = new PagerManager(page, 10,
				hotActService.countHotAct(active));
		List<Act> hotActList = hotActService.listHotAct(active,
				pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("hotActList", hotActList);
		model.addAttribute("pager", pager);
		model.addAttribute("active", active);
		return "cms/hot_list";
	}
}

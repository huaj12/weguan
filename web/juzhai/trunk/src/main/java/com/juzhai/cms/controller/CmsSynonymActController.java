package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.mapper.SynonymActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.SynonymAct;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.ISynonymActService;
import com.juzhai.cms.controller.view.CmsSynonymActView;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;

@Controller
@RequestMapping("/cms")
public class CmsSynonymActController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IActService actService;
	@Autowired
	private ISynonymActService synonymActService;
	@Autowired
	private SynonymActMapper synonymActMapper;
	int cmsPage=20;
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

	@RequestMapping(value = "/showSynonymActs", method = RequestMethod.GET)
	public String showSynonymActs(Model model, Integer pageId) {
		if (pageId == null) {
			pageId = 1;
		}
		PagerManager pager = new PagerManager(pageId, cmsPage,
				synonymActService.countSysonymActs());
		List<SynonymAct> list = synonymActService.getSysonymActs(
				pager.getFirstResult(), pager.getMaxResult());
		List<CmsSynonymActView> synonymActViews=new ArrayList<CmsSynonymActView>();
		for(SynonymAct syn:list){
			Act act=actService.getActById(syn.getActId());
			synonymActViews.add(new CmsSynonymActView(act.getName(), syn));
		}
		model.addAttribute("synonymActViews", synonymActViews);
		model.addAttribute("pager", pager);
		return "cms/synonymActsList";
	}

	@RequestMapping(value = "/deleteSynonymAct", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult deleteSynonymAct(Long id) {
		AjaxResult result = new AjaxResult();
		try{
		if (id != null) {
			synonymActMapper.deleteByPrimaryKey(id);
			}
		} catch (Exception e) {
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}

	@RequestMapping(value = "/replaceSynonymAct", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult replaceSynonymAct(Long id, String actName) {
		AjaxResult result = new AjaxResult();
		Act act = actService.getActByName(actName);
		if (act == null) {
			result.setSuccess(false);
			result.setErrorCode("-1");
		} else {
			if (synonymActService.updateSynonymAct(id, act.getId())) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		}
		return result;
	}
}

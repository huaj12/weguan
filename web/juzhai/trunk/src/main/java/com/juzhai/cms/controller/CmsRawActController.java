package com.juzhai.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.exception.RawActInputException;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.cms.controller.form.AgreeRawActForm;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;

@Controller
@RequestMapping("/cms")
public class CmsRawActController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	private static final String ERROR = "cms/error";
	@Autowired
	private IRawActService rawActService;
	@Autowired
	private IActCategoryService actCategoryService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/showRawActs", method = RequestMethod.GET)
	public String showRawActs(@RequestParam(defaultValue = "1") int pageId,
			Model model) {
		// TODO（review）每页显示一个？
		PagerManager pager = new PagerManager(pageId, 1,
				rawActService.getRawActCount());
		List<RawAct> rawActs = rawActService.getRawActs(pager.getFirstResult(),
				pager.getMaxResult());
		model.addAttribute("rawActs", rawActs);
		model.addAttribute("pager", pager);
		return "cms/show_raw_act";
	}

	@RequestMapping(value = "/showManagerRawAct", method = RequestMethod.GET)
	public String showManagerRawAct(Model model,
			@RequestParam(defaultValue = "0") long id) {
		try {
			model.addAttribute("rawAct", rawActService.getRawAct(id));
			assembleCiteys(model);
		} catch (Exception e) {
			log.error("showManagerRawAct is error." + e.getMessage());
			return ERROR;
		}
		return "cms/show_manager_raw_act";
	}

	@RequestMapping(value = "/ajax/delRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult delRawAct(Model model,
			@RequestParam(defaultValue = "0") long id) {
		AjaxResult result = new AjaxResult();
		try {
			if (id > 0) {
				rawActService.delteRawAct(id);
			} else {
				result.setSuccess(false);
				result.setErrorInfo("showManagerRawAct is error. id is null");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorInfo("showManagerRawAct is error." + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/ajax/agreeRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult agreeRawAct(HttpServletRequest request, Model model,
			AgreeRawActForm agreeRawActForm) {
		// UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();

		try {
			rawActService.agreeRawAct(agreeRawActForm);
		} catch (ActInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (RawActInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}

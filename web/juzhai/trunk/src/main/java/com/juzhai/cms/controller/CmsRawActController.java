package com.juzhai.cms.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
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
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActDetailService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.act.service.IUploadImageService;
import com.juzhai.cms.controller.form.AgreeRawActForm;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.Town;

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
			// TODO (done) 这部分代码看到太多了，想过公用吗？
			assembleCiteys(model);
		} catch (Exception e) {
			log.error("showManagerRawAct is error." + e.getMessage());
			// TODO (done) 返回空字符串是什么意思？
			return ERROR;
		}
		return "cms/show_manager_raw_act";
	}

	@RequestMapping(value = "/ajax/delRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult delRawAct(Model model,
			@RequestParam(defaultValue = "0") long id) {
		// TODO (done) 为什么要有defaultValue=0?首先已经是0了，然后下面是不是应该判断一下是否>0呢？
		AjaxResult result = new AjaxResult();
		try {
			if (id > 0) {
				rawActService.delteRawAct(id);
			} else {
				result.setSuccess(false);
				result.setErrorInfo("showManagerRawAct is error. id is null" );
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorInfo("showManagerRawAct is error." + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/ajax/agreeRawAct", method = RequestMethod.POST)
	@ResponseBody
	// TODO (done) 参数过多，应该封装成一个form
	public AjaxResult agreeRawAct(HttpServletRequest request,Model model,AgreeRawActForm agreeRawActForm) {
		// UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();
		
		try {
			rawActService.agreeRawAct(agreeRawActForm);
		} catch (ActInputException e) {
			result.setError(e.getErrorCode(), messageSource);
			return result;
		}
		result.setSuccess(true);
		return result;
	}

	// TODO (done)想想怎么公用?另外下面的循环是否有必要?
}

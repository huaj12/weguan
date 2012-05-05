package com.juzhai.plug.controller.website;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.InputReportException;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IReportService;
import com.juzhai.plug.controller.form.ReportForm;

@Controller
@RequestMapping("/plug")
public class ReportContrller extends BaseController {

	@Autowired
	private IReportService reportService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = { "/report/show" }, method = RequestMethod.POST)
	public String showIvite(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "0") int uid, String content,
			@RequestParam(defaultValue = "1") int contentType, int contentId)
			throws NeedLoginException {
		checkLoginForWeb(request);
		ProfileCache cache = profileService.getProfileCacheByUid(uid);
		if (null != cache) {
			model.addAttribute("profile", cache);
		}

		model.addAttribute("content", content);
		model.addAttribute("contentType", contentType);
		model.addAttribute("contentId", contentId);
		model.addAttribute("uid", uid);
		return "web/plug/report/plug_report_dialog";
	}

	@RequestMapping(value = "/report/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult guide(HttpServletRequest request, Model model,
			ReportForm reportForm) throws NeedLoginException {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = checkLoginForWeb(request);
		try {
			reportService.save(reportForm, context.getUid());
		} catch (InputReportException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}
}

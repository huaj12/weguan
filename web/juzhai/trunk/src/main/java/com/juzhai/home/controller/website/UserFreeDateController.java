package com.juzhai.home.controller.website;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.service.IUserFreeDateService;

@Controller
@RequestMapping(value = "home")
public class UserFreeDateController extends BaseController {

	@Autowired
	private IUserFreeDateService userFreeDateService;

	@RequestMapping(value = "/setFreeDate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult setFreeDate(HttpServletRequest request, Model model,
			String freeDateString) throws NeedLoginException, ParseException {
		UserContext context = checkLoginForWeb(request);
		Date freeDate = DateUtils.parseDate(freeDateString,
				DateFormat.DATE_PATTERN);
		userFreeDateService.setFreeDate(context.getUid(), freeDate);
		return new AjaxResult();
	}

	@RequestMapping(value = "/unSetFreeDate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult unSetFreeDate(HttpServletRequest request, Model model,
			String freeDateString) throws NeedLoginException, ParseException {
		UserContext context = checkLoginForWeb(request);
		Date freeDate = DateUtils.parseDate(freeDateString,
				DateFormat.DATE_PATTERN);
		userFreeDateService.unSetFreeDate(context.getUid(), freeDate);
		return new AjaxResult();
	}
}

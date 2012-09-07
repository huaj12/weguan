package com.juzhai.home.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.core.controller.BaseController;

//@Controller
@RequestMapping(value = "home")
public class UserFreeDateController extends BaseController {

	// @Autowired
	// private IUserFreeDateService userFreeDateService;
	//
	// @RequestMapping(value = "/setFreeDate", method = RequestMethod.POST)
	// @ResponseBody
	// public AjaxResult setFreeDate(HttpServletRequest request, Model model,
	// String freeDateString) throws NeedLoginException, ParseException {
	// UserContext context = checkLoginForWeb(request);
	// Date freeDate = DateUtils.parseDate(freeDateString,
	// DateFormat.DATE_PATTERN);
	// userFreeDateService.setFreeDate(context.getUid(), freeDate);
	// return new AjaxResult();
	// }
	//
	// @RequestMapping(value = "/unSetFreeDate", method = RequestMethod.POST)
	// @ResponseBody
	// public AjaxResult unSetFreeDate(HttpServletRequest request, Model model,
	// String freeDateString) throws NeedLoginException, ParseException {
	// UserContext context = checkLoginForWeb(request);
	// Date freeDate = DateUtils.parseDate(freeDateString,
	// DateFormat.DATE_PATTERN);
	// userFreeDateService.unSetFreeDate(context.getUid(), freeDate);
	// return new AjaxResult();
	// }
}

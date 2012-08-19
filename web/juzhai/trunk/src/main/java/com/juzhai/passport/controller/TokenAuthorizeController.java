package com.juzhai.passport.controller;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.exception.TokenAuthorizeException;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.IAdminService;
import com.juzhai.platform.service.IUserService;

@Controller
public class TokenAuthorizeController extends BaseController {

	@Autowired
	private IAdminService adminService;
	@Autowired
	private IUserService userService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IAuthorizeService authorizeService;

	@RequestMapping(value = "/show/authorize", method = RequestMethod.GET)
	public String show(HttpServletRequest request,
			HttpServletResponse response, Model model, String errorInfo)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AuthInfo authInfo = getAuthInfo(context.getUid(), context.getTpId());
		model.addAttribute("isExpired", adminService.isTokenExpired(authInfo));
		model.addAttribute("errorInfo", errorInfo);
		return "web/profile/authorize";
	}

	@RequestMapping(value = "/authorize/token/{tpId}", method = RequestMethod.GET)
	public String authorize(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId)
			throws NeedLoginException, UnsupportedEncodingException {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return "404";
		}
		String callback = messageSource.getMessage(
				"authorize.token.callback.url", new Object[] { tpId },
				Locale.SIMPLIFIED_CHINESE);
		String url = userService.getAuthorizeURLforCode(request, response, tp,
				Terminal.PC, null, null, callback);
		if (StringUtils.isEmpty(url)) {
			return "404";
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/access/authorize/{tpId}", method = RequestMethod.GET)
	public ModelAndView access(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId, Model model)
			throws NeedLoginException {
		ModelMap mmap = new ModelMap();
		String url = "/show/authorize";
		UserContext context = checkLoginForWeb(request);
		try {
			authorizeService.tokenAuthorize(request, context.getUid(),
					context.getTpId(), tpId);
		} catch (TokenAuthorizeException e) {
			String errorInfo = messageSource.getMessage(e.getErrorCode(), null,
					Locale.SIMPLIFIED_CHINESE);
			mmap.addAttribute("errorInfo", errorInfo);
		}
		return new ModelAndView("redirect:" + url, mmap);
	}
}

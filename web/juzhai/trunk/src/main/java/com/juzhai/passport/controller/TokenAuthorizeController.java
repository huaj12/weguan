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
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.IAdminService;
import com.juzhai.platform.service.IUserService;

@Controller
//TODO (review) 没有一个模块？一会/show，一会/authorize，然后又/access。请求缺乏目录引导性
public class TokenAuthorizeController extends BaseController {

	@Autowired
	private IAdminService adminService;
	@Autowired
	private IUserService userService;
	@Autowired
	private MessageSource messageSource;

	//TODO (review) errorInfo要了干嘛？
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
			//TODO (review) 有404和500的常量，以后记得
			return "404";
		}
		String url = userService.getExpiredAuthorizeURLforCode(request,
				response, tp, Terminal.PC, null, null);
		if (StringUtils.isEmpty(url)) {
			//TODO (review) 有404和500的常量，以后记得
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
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		try {
			if (tp == null || context.getTpId() != tp.getId()) {
				throw new TokenAuthorizeException(
						TokenAuthorizeException.ILLEGAL_OPERATION);
			}
			userService.expireAccess(request, tp, context.getUid());
		} catch (TokenAuthorizeException e) {
			String errorInfo = messageSource.getMessage(e.getErrorCode(), null,
					Locale.SIMPLIFIED_CHINESE);
			mmap.addAttribute("errorInfo", errorInfo);
		}
		return new ModelAndView("redirect:" + url, mmap);
	}
}

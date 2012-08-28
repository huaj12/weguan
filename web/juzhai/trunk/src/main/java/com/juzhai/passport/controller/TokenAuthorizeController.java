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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.exception.TokenAuthorizeException;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.IUserService;

@Controller
@RequestMapping(value = "authorize")
public class TokenAuthorizeController extends BaseController {

	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IUserService userService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request,
			HttpServletResponse response, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		if (context.getTpId() > 0) {
			model.addAttribute(
					"isExpired",
					tpUserAuthService.isTokenExpired(context.getUid(),
							context.getTpId()));
		}
		return "web/profile/authorize";
	}

	@RequestMapping(value = "/token/{tpId}", method = RequestMethod.GET)
	public String authorize(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId)
			throws NeedLoginException, UnsupportedEncodingException {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return error_404;
		}
		String url = userService.getExpiredAuthorizeURLforCode(request,
				response, tp, Terminal.PC);
		if (StringUtils.isEmpty(url)) {
			return error_404;
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/access/{tpId}", method = RequestMethod.GET)
	public String access(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		try {
			if (tp == null || context.getTpId() != tp.getId()) {
				throw new TokenAuthorizeException(
						TokenAuthorizeException.ILLEGAL_OPERATION);
			}
			userService.expireAccess(request, tp, context.getUid());
		} catch (TokenAuthorizeException e) {
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			return show(request, response, model);
		}
		return "redirect:/authorize/show";
	}

	@RequestMapping(value = "/bind/{tpId}", method = RequestMethod.GET)
	public String bind(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId)
			throws NeedLoginException, UnsupportedEncodingException {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return error_404;
		}
		String url = userService.getBindAuthorizeURLforCode(request, response,
				tp, Terminal.PC);
		if (StringUtils.isEmpty(url)) {
			return error_404;
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/bindAccess/{tpId}", method = RequestMethod.GET)
	public String bindAccess(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		try {
			// TODO (review) 我说逻辑不对，就应该删掉？仔细点思考一下啊！！！
			userService.bindAccess(request, tp, context.getUid());
		} catch (TokenAuthorizeException e) {
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			return show(request, response, model);
		}
		return "redirect:/authorize/show";
	}

}

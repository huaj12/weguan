package com.juzhai.mobile.passport.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.bean.RequestParameter;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.InitData;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileRemoteService;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.exception.TokenAuthorizeException;
import com.juzhai.platform.service.IUserRemoteService;

@Controller
@RequestMapping("passport/authorize")
public class AuthorizeMController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IUserRemoteService userService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileRemoteService profileService;
	@Autowired
	private IUserMViewHelper userMViewHelper;
	@Autowired
	private ILoginService loginService;

	@RequestMapping(value = "/expired/{tpId}", method = RequestMethod.GET)
	public String expired(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId)
			throws NeedLoginException, UnsupportedEncodingException {
		checkLoginForWeb(request);
		Thirdparty tp = InitData.getTpMap().get(tpId);
		if (null == tp) {
			return error_404;
		}
		String url = userService.getExpiredAuthorizeURLforCode(tp,
				Terminal.MOBILE);
		if (StringUtils.isEmpty(url)) {
			return error_404;
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/expired/access/{tpId}")
	@ResponseBody
	public AjaxResult expiredAccess(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		Thirdparty tp = InitData.getTpMap().get(tpId);
		try {
			if (tp == null || context.getTpId() != tp.getId()) {
				throw new TokenAuthorizeException(
						TokenAuthorizeException.ILLEGAL_OPERATION);
			}
			userService.expireAccess(new RequestParameter(request), tp,
					context.getUid());
		} catch (TokenAuthorizeException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		result.setResult(userMViewHelper.createUserMView(context,
				profileService.getProfileCacheByUid(context.getUid()), true));
		return result;
	}

	@RequestMapping(value = "/bind/{tpId}", method = RequestMethod.GET)
	public String bind(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId)
			throws NeedLoginException, UnsupportedEncodingException {
		checkLoginForWeb(request);
		Thirdparty tp = InitData.getTpMap().get(tpId);
		if (null == tp) {
			return error_404;
		}
		String url = userService
				.getBindAuthorizeURLforCode(tp, Terminal.MOBILE);
		if (StringUtils.isEmpty(url)) {
			return error_404;
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/bind/access/{tpId}")
	@ResponseBody
	public AjaxResult bindAccess(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		Thirdparty tp = InitData.getTpMap().get(tpId);
		try {
			userService.bindAccess(new RequestParameter(request), tp,
					context.getUid());
			loginService.updateTpId(request, response, context.getUid(), tpId);
		} catch (TokenAuthorizeException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		context = (UserContext) request.getAttribute("context");
		result.setResult(userMViewHelper.createUserMView(context,
				profileService.getProfileCacheByUid(context.getUid()), true));
		return result;
	}
}

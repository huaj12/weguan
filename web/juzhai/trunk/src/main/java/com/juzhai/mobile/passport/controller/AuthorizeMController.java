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
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.passport.InitData;
import com.juzhai.passport.exception.TokenAuthorizeException;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.IUserService;

@Controller
@RequestMapping("m/passport/authorize")
public class AuthorizeMController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IUserService userService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserMViewHelper userMViewHelper;

	@RequestMapping(value = "/token/{tpId}", method = RequestMethod.GET)
	public String authorize(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId)
			throws NeedLoginException, UnsupportedEncodingException {
		checkLoginForWeb(request);
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return error_404;
		}
		String url = userService.getExpiredAuthorizeURLforCode(request,
				response, tp, Terminal.MOBILE);
		if (StringUtils.isEmpty(url)) {
			return error_404;
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/access/{tpId}")
	@ResponseBody
	public AjaxResult access(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		try {
			if (tp == null || context.getTpId() != tp.getId()) {
				throw new TokenAuthorizeException(
						TokenAuthorizeException.ILLEGAL_OPERATION);
			}
			userService.expireAccess(request, tp, context.getUid());
		} catch (TokenAuthorizeException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		result.setResult(userMViewHelper.createUserMView(context,
				profileService.getProfileCacheByUid(context.getUid()), true));
		return result;
	}
}

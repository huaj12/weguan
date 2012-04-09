/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.core.Constants;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.exception.LoginException;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.platform.service.IUserService;

/**
 * @author wujiajun Created on 2011-2-15
 */
@Controller
public class TpAuthorizeController extends BaseController {

	private static final Log log = LogFactory
			.getLog(TpAuthorizeController.class);
	private static final String REQUEST_TOKEN_ATTR_NAME = "authInfo";
	@Autowired
	private IUserService userService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IUserGuideService userGuideService;

	@RequestMapping(value = "web/login/{tpId}")
	public String webLogin(HttpServletRequest request, Model model,
			@PathVariable long tpId, String incode, String turnTo)
			throws UnsupportedEncodingException {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return "404";
		}
		String url = userService
				.getAuthorizeURLforCode(request, tp, turnTo == null ? ""
						: URLEncoder.encode(turnTo, Constants.UTF8), incode);
		if (StringUtils.isEmpty(url)) {
			return "404";
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "web/access/{tpId}")
	public String webAccess(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId,
			String turnTo, String incode, String error_code, Model model)
			throws UnsupportedEncodingException, MalformedURLException {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return null;
		}
		if (StringUtils.isNotEmpty(error_code)) {
			return "redirect:/";
		}
		try {
			checkLoginForWeb(request);
			return "redirect:"
					+ (StringUtils.isEmpty(turnTo) ? "/home" : StringUtil
							.encodeURI(turnTo, Constants.UTF8));
		} catch (NeedLoginException e) {
		}
		long uid = 0;
		if (null != tp) {
			if (log.isDebugEnabled()) {
				log.debug("thirdparty access [tpName=" + tp.getName()
						+ ", joinType=" + tp.getJoinType() + "]");
			}
			AuthInfo authInfo = getAuthInfoFromCookie(request);
			uid = userService.access(request, response, authInfo, tp,
					decryptInviteUid(incode));
		}
		if (uid <= 0) {
			log.error("access failed.[tpName=" + tp.getName() + ", joinType="
					+ tp.getJoinType() + "].");
			return error_500;
		}
		try {
			loginService.login(request, uid, tp.getId(), RunType.CONNET);
		} catch (LoginException e) {
			model.addAttribute("shieldTime", new Date(e.getShieldTime()));
			return "web/login/login_error";
		}
		if (!userGuideService.isCompleteGuide(uid)) {
			return "redirect:/home/guide";
		}
		return "redirect:"
				+ (StringUtils.isEmpty(turnTo) ? "/home" : StringUtil
						.encodeURI(turnTo, Constants.UTF8));
	}

	private AuthInfo getAuthInfoFromCookie(HttpServletRequest request) {
		if (request != null && request.getCookies() != null) {
			String value = null;
			for (Cookie cookie : request.getCookies()) {
				if (StringUtils.equals(cookie.getName(),
						REQUEST_TOKEN_ATTR_NAME)) {
					value = cookie.getValue();
					break;
				}
			}
			if (StringUtils.isNotEmpty(value)) {
				try {
					return AuthInfo.convertToBean(value);
				} catch (JsonGenerationException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return new AuthInfo();
	}
}

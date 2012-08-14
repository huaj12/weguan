/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

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
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.DeviceName;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.bean.ThirdpartyNameEnum;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ReportAccountException;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.IUserService;
import com.qplus.QOpenBean;
import com.qplus.QOpenService;

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
	public String webLogin(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId,
			String incode, String turnTo) throws UnsupportedEncodingException {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return "404";
		}
		String url = userService
				.getAuthorizeURLforCode(
						request,
						response,
						tp,
						Terminal.PC,
						turnTo == null ? "" : URLEncoder.encode(turnTo,
								Constants.UTF8), incode);
		if (StringUtils.isEmpty(url)) {
			return "404";
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "web/access/{tpId}")
	public String webAccess(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId,
			String turnTo, String incode, String error_code, Model model)
			throws UnsupportedEncodingException, MalformedURLException,
			ReportAccountException {
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
					decryptInviteUid(incode), DeviceName.BROWSER);
		}
		if (uid <= 0) {
			log.error("access failed.[tpName=" + tp.getName() + ", joinType="
					+ tp.getJoinType() + "].");
			return error_500;
		}
		try {
			loginService.login(request, response, uid, tp.getId(),
					RunType.CONNET, false);
		} catch (PassportAccountException e) {
			return "web/login/login_error";
		}
		if (!userGuideService.isCompleteGuide(uid)) {
			return "redirect:/home/guide";
		}
		return "redirect:"
				+ (StringUtils.isEmpty(turnTo) ? "/" : StringUtil.encodeURI(
						turnTo, Constants.UTF8));
	}

	@RequestMapping(value = "qplus/loginDialog/{tpId}")
	public String loginDialog(HttpServletRequest request,
			@PathVariable long tpId, Model model) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (JoinTypeEnum.PLUS.getName().equals(tp.getJoinType())
				&& ThirdpartyNameEnum.QQ.getName().equals(tp.getName())) {
			QOpenService service = QOpenService.createInstance(
					Integer.parseInt(tp.getAppId()), tp.getAppSecret());
			QOpenBean bean = new QOpenBean(null, null,
					HttpRequestUtil.getRemoteIp(request));
			try {
				model.addAttribute("loginParams", service.getLoginParams(bean));
				model.addAttribute("url", tp.getAppUrl());
			} catch (IOException e) {
				log.error("QQ plus get loginParams is error", e);
			}
			return "web/login/qplus_login";
		} else {
			return error_404;
		}

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

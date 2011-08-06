/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.controller;

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

import com.juzhai.core.web.ErrorPageDispatcher;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.login.ILoginService;

/**
 * @author wujiajun Created on 2011-2-15
 */
@Controller
public class TpAuthorizeController {

	private static final Log log = LogFactory
			.getLog(TpAuthorizeController.class);
	private static final String REQUEST_TOKEN_ATTR_NAME = "authInfo";
	@Autowired
	private IAuthorizeService authorizeService;
	@Autowired
	private ILoginService tomcatLoginService;

	@RequestMapping(value = "access/{tpId}")
	public String access(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId,
			String returnTo) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		long uid = 0;
		if (null != tp) {
			if (log.isDebugEnabled()) {
				log.debug("thirdparty access [tpName=" + tp.getName()
						+ ", joinType=" + tp.getJoinType() + "]");
			}
			AuthInfo authInfo = getAuthInfoFromCookie(request);
			uid = authorizeService.access(request, response, authInfo, tp);
		}
		if (uid <= 0) {
			log.error("access failed.[tpName=" + tp.getName() + ", joinType="
					+ tp.getJoinType() + "].");
			return ErrorPageDispatcher.ERROR_500;
		}

		tomcatLoginService.login(request, uid, tp.getId());

		return returnPage(returnTo, tp);
	}

	private String returnPage(String returnTo, Thirdparty tp) {
		if (StringUtils.isNotEmpty(returnTo)
				&& StringUtils.startsWith(returnTo, "http")) {
			return "redirect:" + returnTo;
		}
		return "passport/" + tp.getName() + "/index";
	}

	@RequestMapping(value = "auth/rr/appLogin")
	public String rrLogin(HttpServletRequest request,
			HttpServletResponse response, String fromUri, Model model) {
		String sessionKey = request.getParameter("xn_sig_session_key");
		String uid = request.getParameter("xn_sig_user");
		if (StringUtils.isEmpty(sessionKey) || StringUtils.isEmpty(uid)) {
			Thirdparty tp = InitData.TP_MAP.get(5L);
			if (null != tp) {
				if (log.isDebugEnabled()) {
					log.debug("thirdparty login[tpname=" + tp.getName() + "]");
				}
				model.addAttribute("tp", tp);
				model.addAttribute("fromUri", fromUri);
			}
			return "/renren/app_login";
		} else {
			return "forward:/access/2";
		}
	}

	@RequestMapping(value = "access/rr/xd_receiver")
	public String rrCrossDomain() {
		return "/renren/xd_receiver";
	}

	// private void saveAuthInfoToCookie(HttpServletRequest request,
	// HttpServletResponse response, AuthInfo authInfo) {
	// try {
	// cookieManager.addCookie(request, response, REQUEST_TOKEN_ATTR_NAME,
	// authInfo.toJsonString(), -1);
	// cookieManager.dealIEPrivacy(response);
	// } catch (JsonGenerationException e) {
	// log.error(e.getMessage(), e);
	// }
	// }

	private AuthInfo getAuthInfoFromCookie(HttpServletRequest request) {
		String value = null;
		if (request == null || request.getCookies() == null) {
			return null;
		}
		for (Cookie cookie : request.getCookies()) {
			if (StringUtils.equals(cookie.getName(), REQUEST_TOKEN_ATTR_NAME)) {
				value = cookie.getValue();
				break;
			}
		}
		if (StringUtils.isNotEmpty(value)) {
			try {
				return AuthInfo.convertToBean(value);
			} catch (JsonGenerationException e) {
				log.error(e.getMessage(), e);
				return null;
			}
		}
		return null;
	}
}

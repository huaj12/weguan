/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.SystemConfig;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.passport.service.login.ILoginService;

/**
 * @author wujiajun Created on 2011-2-15
 */
@Controller
public class TpAuthorizeController extends BaseController {

	private static final Log log = LogFactory
			.getLog(TpAuthorizeController.class);
	private static final String REQUEST_TOKEN_ATTR_NAME = "authInfo";
	@Autowired
	private IAuthorizeService authorizeService;
	@Autowired
	private ILoginService tomcatLoginService;
	@Autowired
	private IUserGuideService userGuideService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "appLoad/{tpId}", method = RequestMethod.GET)
	public String load(HttpServletRequest request, @PathVariable long tpId,
			Model model) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			parameters.put(name, request.getParameter(name));
		}
		parameters.put("tpId", tpId);
		try {
			model.addAttribute("data", JackSonSerializer.toString(parameters));
		} catch (JsonGenerationException e) {
			log.error(e);
		}
		return "common/app/loading";
	}

	@RequestMapping(value = "access", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult access(HttpServletRequest request,
			HttpServletResponse response, long tpId, String returnTo) {
		try {
			UserContext context = checkLoginForApp(request);
			Thirdparty loginTp = InitData.TP_MAP.get(context.getTpId());
			if (null == loginTp) {
				return returnError();
			}
			return returnPage(context.getUid(), loginTp, returnTo);
		} catch (NeedLoginException e) {
		}

		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return returnError();
		}
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
			return returnError();
		}

		tomcatLoginService.login(request, uid, tp.getId());

		return returnPage(uid, tp, returnTo);
	}

	private AjaxResult returnPage(long uid, Thirdparty tp, String returnTo) {
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		String returnUrl = null;
		if (!userGuideService.isCompleteGuide(uid)) {
			returnUrl = SystemConfig.BASIC_DOMAIN + "/app/guide";
		} else if (StringUtils.isNotEmpty(returnTo)
				&& StringUtils.startsWith(returnTo, "http")) {
			returnUrl = returnTo;
		} else {
			returnUrl = SystemConfig.BASIC_DOMAIN + "/app/index";
		}
		result.setResult(returnUrl);
		return result;
	}

	private AjaxResult returnError() {
		AjaxResult result = new AjaxResult();
		result.setSuccess(false);
		return result;
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

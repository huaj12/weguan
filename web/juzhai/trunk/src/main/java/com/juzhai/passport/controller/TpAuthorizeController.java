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

import weibo4j.Oauth;


import com.juzhai.core.SystemConfig;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.login.ILoginService;
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
	private ILoginService tomcatLoginService;

	// @Autowired
	// private IUserGuideService userGuideService;

	@RequestMapping(value = "login")
	public String login(HttpServletRequest request, Model model) {
		String requestUrl = HttpRequestUtil.getRemoteUrl(request);
		String thirdpartyName = SystemConfig.getThirdpartyName(requestUrl);
		Thirdparty tp = InitData.getTpByTpNameAndJoinType(thirdpartyName,
				JoinTypeEnum.APP);
		model.addAttribute("tp", tp);
		return "login/app/login";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "appLoad/{tpId}")
	public String load(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId, Model model) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			parameters.put(name, request.getParameter(name));
		}
		parameters.put("tpId", tpId);
		Thirdparty loginTp = InitData.TP_MAP.get(tpId);
		try {
			model.addAttribute("data", JackSonSerializer.toString(parameters));
		} catch (JsonGenerationException e) {
			log.error(e);
		}
		return "common/app/" + loginTp.getName() + "/loading";
	}

	@RequestMapping(value = "access")
	@ResponseBody
	public AjaxResult access(HttpServletRequest request,
			HttpServletResponse response, long tpId, String goUri) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return returnError();
		}
		String returnTo = convertGoUrl(tp, goUri);
		try {
			UserContext context = checkLoginForApp(request);
			Thirdparty loginTp = InitData.TP_MAP.get(context.getTpId());
			if (null == loginTp) {
				return returnError();
			}
			return returnPage(context.getUid(), loginTp, returnTo);
		} catch (NeedLoginException e) {
		}
		long uid = 0;
		if (null != tp) {
			if (log.isDebugEnabled()) {
				log.debug("thirdparty access [tpName=" + tp.getName()
						+ ", joinType=" + tp.getJoinType() + "]");
			}
			AuthInfo authInfo = getAuthInfoFromCookie(request);
			uid = userService.access(request, response, authInfo, tp);
		}
		if (uid <= 0) {
			log.error("access failed.[tpName=" + tp.getName() + ", joinType="
					+ tp.getJoinType() + "].");
			return returnError();
		}

		tomcatLoginService.login(request, uid, tp.getId());

		return returnPage(uid, tp, returnTo);
	}

	private String convertGoUrl(Thirdparty tp, String goUri) {
		if (StringUtils.isNotEmpty(goUri)) {
			String domain = SystemConfig.getDomain(tp == null ? null : tp
					.getName());
			if (StringUtils.isNotEmpty(domain)) {
				return domain + goUri;
			}
		}
		return null;
	}

	private AjaxResult returnPage(long uid, Thirdparty tp, String returnTo) {
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		String returnUrl = null;
		// if (!userGuideService.isCompleteGuide(uid)) {
		// returnUrl = SystemConfig
		// .getDomain(tp == null ? null : tp.getName()) + "/app/guide";
		// } else

		if (StringUtils.isNotEmpty(returnTo)
				&& StringUtils.startsWith(returnTo, "http")) {
			returnUrl = returnTo;
		} else {
			returnUrl = SystemConfig
					.getDomain(tp == null ? null : tp.getName()) + "/app/index";
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
			Thirdparty tp = InitData.TP_MAP.get(2L);
			if (null != tp) {
				if (log.isDebugEnabled()) {
					log.debug("thirdparty login[tpname=" + tp.getName() + "]");
				}
				model.addAttribute("tp", tp);
				model.addAttribute("fromUri", fromUri);
			}
			return "auth/renren/app_auth";
		} else {
			return "forward:/appLoad/2";
		}
	}
	
	@RequestMapping(value = "auth/weibo/appLogin")
	public String weiboLogin(HttpServletRequest request,HttpServletResponse response, String fromUri, Model model) {
		Thirdparty tp = InitData.TP_MAP.get(3L);
		Oauth oauth=new Oauth(tp.getAppKey(),tp.getAppSecret(),tp.getAppUrl());
		String signedRequest = request.getParameter("signed_request");
		String uid="";
		String accessToken="";
		try {
			oauth.parseSignedRequest(signedRequest);
			uid=oauth.user_id;
			accessToken=oauth.getToken();
		} catch (Exception e) {
		}
		if (StringUtils.isEmpty(uid)||StringUtils.isEmpty(accessToken)) {
			if (null != tp) {
				if (log.isDebugEnabled()) {
					log.debug("thirdparty login[tpname=" + tp.getName() + "]");
				}
				model.addAttribute("tp", tp);
				model.addAttribute("fromUri", fromUri);
			}
			return "auth/weibo/app_auth";
		} else {
			return "forward:/appLoad/3";
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

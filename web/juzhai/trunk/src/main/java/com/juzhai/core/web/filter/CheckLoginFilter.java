package com.juzhai.core.web.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.Constants;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.exception.ReportAccountException;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileService;

@Component
public class CheckLoginFilter implements Filter {

	public static final String LOGIN_USER_KEY = "loginUser";
	public static final String IS_FROM_QQ_PLUS = "isQplus";

	@Autowired
	private LoginSessionManager loginSessionManager;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IProfileService profileService;

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse rep = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		try {
			UserContext context = loginSessionManager.getUserContext(req);
			req.setAttribute("context", context);
			if (!context.hasLogin()) {
				loginService.persistentAutoLogin(req, rep);
				// context = loginSessionManager.getUserContext(req);
			}
			context = (UserContext) req.getAttribute("context");
			// else if (context.hasLogin()) {
			// loginService.isShield(context.getUid(), req, rep);
			// }
			if (context.hasLogin()) {
				loginService.isShield(context.getUid(), req, rep);
				loginService.updateOnlineState(context.getUid());
				// 获取登录用户信息
				req.setAttribute(LOGIN_USER_KEY,
						profileService.getProfileCacheByUid(context.getUid()));
			}
			// 判断是否来自q+
			// if (null == req.getSession().getAttribute(IS_FROM_QQ_PLUS)) {
			String userAgent = context.getUserAgentPermanentCode();
			req.setAttribute(
					IS_FROM_QQ_PLUS,
					StringUtils.isEmpty(userAgent) ? false : userAgent
							.contains("Qplus"));
			// }
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			if (e.getCause() instanceof NeedLoginException) {
				needLoginHandle(req, rep, (NeedLoginException) e.getCause());
			} else if (e instanceof ReportAccountException) {
				needReportHandle(req, rep, (ReportAccountException) e);
			} else if (e.getCause() instanceof ReportAccountException) {
				needReportHandle(req, rep,
						(ReportAccountException) e.getCause());
			} else {
				throw new ServletException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 需要登录处理
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @throws IOException
	 */
	private void needLoginHandle(HttpServletRequest request,
			HttpServletResponse response, NeedLoginException e)
			throws IOException {
		if (isAjaxRequest(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else if (isIOSRequest(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			if (RunType.APP.equals(e.getRunType())) {
				String returnLink = URLEncoder.encode(
						HttpRequestUtil.getRemoteUrl(request), Constants.UTF8);
				response.sendRedirect("/app/login?returnLink=" + returnLink);
			} else {
				String redirectURI = "/login";
				String turnTo = HttpRequestUtil.getRemoteUrl(request);
				if (StringUtils.isNotEmpty(turnTo)) {
					redirectURI = redirectURI + "?turnTo=" + turnTo;
				}
				response.sendRedirect(redirectURI);
			}
		}
	}

	private void needReportHandle(HttpServletRequest request,
			HttpServletResponse response, ReportAccountException e)
			throws IOException {
		if (isAjaxRequest(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			response.sendRedirect("/login/error/shield?shieldTime="
					+ e.getShieldTime());
		}
	}

	/**
	 * @return 是否ajax请求
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("x-requested-with");
		if (StringUtils.equals(requestedWith, "XMLHttpRequest")) {
			return true;
		}
		return false;
	}

	private boolean isIOSRequest(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isEmpty(userAgent)) {
			return false;
		}
		return userAgent.contains("iPhone OS") || userAgent.contains("iPad OS");
	}
}

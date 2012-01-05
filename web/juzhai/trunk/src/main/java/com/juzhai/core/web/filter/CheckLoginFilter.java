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

import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.login.ILoginService;

@Component
public class CheckLoginFilter implements Filter {

	public static final String LOGIN_USER_KEY = "loginUser";

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
			if (context.hasLogin()) {
				loginService.updateOnlineState(context.getUid());
				// 获取登录用户信息
				req.setAttribute(LOGIN_USER_KEY,
						profileService.getProfileCacheByUid(context.getUid()));
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			if (e.getCause().getCause() instanceof NeedLoginException) {
				needLoginHandle(req, rep, (NeedLoginException) e.getCause()
						.getCause());
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
		} else {
			if (RunType.APP.equals(e.getRunType())) {
				String returnLink = URLEncoder.encode(
						HttpRequestUtil.getRemoteUrl(request), "UTF-8");
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
}

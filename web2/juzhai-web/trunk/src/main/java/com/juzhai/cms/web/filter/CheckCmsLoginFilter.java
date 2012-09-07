package com.juzhai.cms.web.filter;

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
import org.springframework.stereotype.Component;

import com.juzhai.core.Constants;
import com.juzhai.core.SystemConfig;
import com.juzhai.core.web.session.UserContext;

@Component
public class CheckCmsLoginFilter implements Filter {

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
		if (!req.getRequestURI().endsWith("/login")) {
			UserContext context = (UserContext) req.getAttribute("context");
			if (null == context || !context.hasLogin() || !context.isAdmin()) {
				needLoginHandle(req, rep);
				return;
			}
		}
		filterChain.doFilter(request, response);
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
			HttpServletResponse response) throws IOException {
		if (isAjaxRequest(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			String returnLink = URLEncoder.encode(
					request.getRequestURL().toString()
							+ (request.getQueryString() == null ? "" : request
									.getQueryString()), Constants.UTF8);
			response.sendRedirect(SystemConfig.getDomain()
					+ "/login?returnLink=" + returnLink);
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

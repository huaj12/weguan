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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.Constants;
import com.juzhai.core.SystemConfig;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.core.web.session.UserContext;

@Component
public class CheckLoginFilter implements Filter {

	@Autowired
	private LoginSessionManager loginSessionManager;

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
			req.setAttribute("staticService", SystemConfig.STATIC_DOMAIN);
			req.setAttribute("httpService", SystemConfig.BASIC_DOMAIN);
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			if (e.getCause() instanceof NeedLoginException) {
				String returnLink = URLEncoder.encode(
						req.getRequestURL().toString()
								+ (req.getQueryString() == null ? "" : req
										.getQueryString()), Constants.UTF8);
				rep.sendRedirect(SystemConfig.BASIC_DOMAIN
						+ "/login?returnLink=" + returnLink);
			} else {
				throw new ServletException(e.getMessage(), e);
			}
		}
	}
}

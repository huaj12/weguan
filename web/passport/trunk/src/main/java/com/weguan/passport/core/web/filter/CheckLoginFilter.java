package com.weguan.passport.core.web.filter;

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

import org.springframework.stereotype.Component;

import com.weguan.passport.core.SystemConfig;
import com.weguan.passport.core.util.Constants;
import com.weguan.passport.exception.NeedLoginException;

@Component
public class CheckLoginFilter implements Filter {

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

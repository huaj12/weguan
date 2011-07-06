package com.weguan.passport.web.filter;

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

import com.weguan.passport.exception.NeedLoginException;
import com.weguan.passport.util.Constants;

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
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			if (e instanceof NeedLoginException) {
				String returnLink = URLEncoder.encode(req.getRequestURL()
						.toString(), Constants.UTF8);
				rep.sendRedirect("/login?returnLink=" + returnLink);
			} else {
				throw new ServletException(e.getCause());
			}
		}
	}
}

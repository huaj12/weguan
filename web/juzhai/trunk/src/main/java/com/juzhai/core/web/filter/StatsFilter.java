package com.juzhai.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.stats.counter.service.ICounter;

@Component
public class StatsFilter implements Filter {
	@Autowired
	private ICounter clickEmailCounter;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String from = req.getParameter("from");
		if ("mail".equals(from)) {
			clickEmailCounter.incr(null, 1);
		}
		filterChain.doFilter(request, response);

	}
}

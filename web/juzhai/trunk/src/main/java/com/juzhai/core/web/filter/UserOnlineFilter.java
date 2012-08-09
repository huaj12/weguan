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

import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.service.IUserOnlineService;

@Component
public class UserOnlineFilter implements Filter {
	@Autowired
	private IUserOnlineService userOnlineService;

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
		UserContext context = (UserContext) req.getAttribute("context");
		if (context != null && context.hasLogin()) {
			long uid = context.getUid();
			if (userOnlineService.isUpdateUserOnlineTime(uid)) {
				userOnlineService.setLastUserOnlineTime(uid);
			}
		}
		filterChain.doFilter(request, response);
	}

}

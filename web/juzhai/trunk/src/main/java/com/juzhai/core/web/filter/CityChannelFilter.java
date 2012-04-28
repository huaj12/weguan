package com.juzhai.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.juzhai.core.web.cookies.CookiesManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.service.IProfileService;

//@Component
public class CityChannelFilter implements Filter {

	public static final String SESSION_CHANNEL_NAME = "channelId";

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
		HttpServletRequest req = (HttpServletRequest) request;
		long channelId = -1L;
		if (channelId < 0) {
			UserContext context = (UserContext) req.getAttribute("context");
			String channel = CookiesManager.getCookie(req,
					CookiesManager.CHANNEL_NAME);
			if (StringUtils.isEmpty(channel)) {
				long uid = context.getUid();
				if (uid > 0) {
					// channelId = profileService.getUserCityFromCache(uid);
				} else {
					channelId = 0L;
				}
			} else {
				channelId = Long.valueOf(channel);
			}
		}
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			throw new ServletException(e.getMessage(), e);
		}
	}
}

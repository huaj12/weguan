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
import com.juzhai.core.web.cookies.CookiesManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.service.IProfileService;

@Component
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
		long channelId = HttpRequestUtil.getSessionAttributeAsLong(req,
				SESSION_CHANNEL_NAME, -1L);
		if (channelId < 0) {
			UserContext context = (UserContext) req.getAttribute("context");
			String channel = CookiesManager.getCookie(req,
					CookiesManager.CHANNEL_NAME);
			if (StringUtils.isEmpty(channel)) {
				long uid = context.getUid();
				if (uid > 0) {
					channelId = profileService.getUserCityFromCache(uid);
				}
			} else {
				channelId = Long.valueOf(channel);
			}
			HttpRequestUtil.setSessionAttribute(req, SESSION_CHANNEL_NAME,
					channelId);
		}
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			throw new ServletException(e.getMessage(), e);
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
			String returnLink = URLEncoder.encode(
					HttpRequestUtil.getRemoteUrl(request), "UTF-8");
			// TODO need test
			response.sendRedirect("/login?returnLink=" + returnLink);
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

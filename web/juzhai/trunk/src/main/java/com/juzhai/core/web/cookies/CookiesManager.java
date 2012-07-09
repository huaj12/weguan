package com.juzhai.core.web.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class CookiesManager {

	public static final String CHANNEL_NAME = "channel";
	public static final String STATE_NAME = "userState";

	/**
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 *            A positive value indicates that the cookie will expire after
	 *            that many seconds have passed. Note that the value is the
	 *            maximum age when the cookie will expire, not the cookie's
	 *            current age.
	 * 
	 *            A negative value means that the cookie is not stored
	 *            persistently and will be deleted when the Web browser exits. A
	 *            zero value causes the cookie to be deleted.
	 */
	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value, int maxAge) {
		String domain = parseDomainFromRequest(request);
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static String getCookie(HttpServletRequest request, String name) {
		if (request != null && request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (StringUtils.equals(cookie.getName(), name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static void delCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		setCookie(request, response, name, StringUtils.EMPTY, 0);
	}

	private static String parseDomainFromRequest(HttpServletRequest request) {
		String host = request.getHeader("host");
		int i = host.indexOf(":");
		if (i > 0) {
			host = host.substring(0, i);
		}
		return host;
	}
}

package com.juzhai.core.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpRequestUtil {

	public final static Log log = LogFactory.getLog(HttpRequestUtil.class);

	public static boolean getSessionAttributeAsBoolean(
			HttpServletRequest request, String name, boolean defaultValue) {
		if (_checkParamNull(request, name)) {
			return defaultValue;
		}
		HttpSession session = request.getSession();
		Object value = session.getAttribute(name);
		if (null == value) {
			return defaultValue;
		}
		return (Boolean) value;
	}

	public static Long getSessionAttributeAsLong(HttpServletRequest request,
			String name, long defaultValue) {
		if (_checkParamNull(request, name)) {
			return defaultValue;
		}
		HttpSession session = request.getSession();
		Object value = session.getAttribute(name);
		if (null == value) {
			return defaultValue;
		}
		return (Long) value;
	}

	public static void setSessionAttribute(HttpServletRequest request,
			String name, Object value) {
		if (_checkParamNull(request, name, value)) {
			return;
		}
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}

	public static String getSessionId(HttpServletRequest request) {
		if (_checkParamNull(request)) {
			return null;
		}
		HttpSession session = request.getSession();
		return session.getId();
	}

	public static String getRemoteAddress(HttpServletRequest request) {
		if (_checkParamNull(request)) {
			return null;
		}
		return request.getRemoteAddr();
	}

	public static void removeSessionAttribute(HttpServletRequest request,
			String uidAttributeName) {
		if (_checkParamNull(request)) {
			return;
		}
		HttpSession session = request.getSession();
		session.removeAttribute(uidAttributeName);
	}

	public static void setMaxInactiveInterval(HttpServletRequest request,
			int interval) {
		if (_checkParamNull(request, interval)) {
			return;
		}
		request.getSession().setMaxInactiveInterval(interval);
	}

	private static boolean _checkParamNull(Object... params) {
		for (Object param : params) {
			if (null == param) {
				log.error("Invalid Parameter.");
				return true;
			}
		}
		return false;
	}
}

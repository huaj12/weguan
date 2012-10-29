package com.juzhai.core.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.core.bean.DeviceName;
import com.juzhai.core.web.session.UserContext;

public class HttpRequestUtil {
	public final static Log log = LogFactory.getLog(HttpRequestUtil.class);

	private static final String NGINX_IP_HEADER = "X-Real-IP";
	private static final String NGINX_URL_HEADER = "X-Real-Url";

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

	/**
	 * 从request中抽取客户端ip(兼容nginx转发模式)
	 * 
	 * @param request
	 * @see #NGINX_IP_HEADER
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		if (_checkParamNull(request)) {
			return null;
		}
		String ip = request.getHeader(NGINX_IP_HEADER);
		if (StringUtils.isEmpty(ip))
			return request.getRemoteAddr();
		else
			return ip;
	}

	/**
	 * 从request中抽取当前url(兼容nginx转发模式)
	 * 
	 * @param request
	 * @see #NGINX_URL_HEADER
	 * @return
	 */
	public static String getRemoteUrl(HttpServletRequest request) {
		if (_checkParamNull(request)) {
			return null;
		}
		String url = request.getHeader(NGINX_URL_HEADER);
		if (StringUtils.isEmpty(url)) {
			return request.getRequestURL().toString();
		} else {
			if (log.isDebugEnabled()) {
				log.debug("NGINX_URL_HEADER:" + url);
			}
			return url;
		}
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

	public static DeviceName getClientName(UserContext context) {
		String userAgent = context.getUserAgentPermanentCode();
		String deviceName = null;
		if (userAgent.contains("iPhone OS") || userAgent.contains("iPad OS")) {
			deviceName = DeviceName.IPHONE.getName();
		} else if (userAgent.contains("Android")) {
			deviceName = DeviceName.ANDROID.getName();
		}
		return DeviceName.getDeviceName(deviceName);
	}
}

package com.juzhai.core.web.bean;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.juzhai.core.web.util.HttpRequestUtil;

public class RequestParameter {
	private Map<String, String[]> map;
	private String queryString;
	private String ip;

	public RequestParameter(HttpServletRequest request) {
		this.map = request.getParameterMap();
		this.queryString = request.getQueryString();
		this.ip = HttpRequestUtil.getRemoteIp(request);
	}

	public String get(String key) {
		String str[] = map.get(key);
		if (str == null || str.length == 0) {
			return null;
		}
		return str[0];
	}

	public String[] getArray(String key) {
		return map.get(key);
	}

	public String getQueryString() {
		return queryString;
	}

	public String getIp() {
		return ip;
	}
}

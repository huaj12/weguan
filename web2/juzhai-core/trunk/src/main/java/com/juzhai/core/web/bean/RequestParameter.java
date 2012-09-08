package com.juzhai.core.web.bean;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

import com.juzhai.core.web.util.HttpRequestUtil;

public class RequestParameter implements Serializable {

	private static final long serialVersionUID = 2301038686314247287L;
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
		if (ArrayUtils.isEmpty(str)) {
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

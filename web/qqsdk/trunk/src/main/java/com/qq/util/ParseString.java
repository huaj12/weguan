package com.qq.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ParseString {

	private static final Log log = LogFactory.getLog(ParseString.class);

	private static ParseString parsestring = new ParseString();

	private ParseString() {
	}

	public static ParseString getInstance() {
		return parsestring;
	}

	public static HashMap<String, String> parseTokenString(String request_token) {
		HashMap<String, String> tokens = new HashMap<String, String>();
		request_token += "&";
		while (request_token.length() > 0) {
			String key_value = request_token.substring(0,
					request_token.indexOf("&"));
			String key = key_value.substring(0, key_value.indexOf("="));
			String value = key_value.substring(key_value.indexOf("=") + 1,
					key_value.length());
			tokens.put(key, value);
			request_token = request_token.substring(
					request_token.indexOf("&") + 1, request_token.length());
		}
		if (tokens.size() == 0) {
			log.error("qq requestToken:" + request_token);
		}
		return tokens;
	}
}

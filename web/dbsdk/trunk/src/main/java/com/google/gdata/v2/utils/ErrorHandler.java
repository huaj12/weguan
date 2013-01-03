package com.google.gdata.v2.utils;

import com.google.api.client.http.HttpResponseException;
import com.google.gdata.v2.model.app.DoubanException;

/**
 * 
 * @author Zhibo Wei <uglytroll@dongxuexidu.com>
 */
public class ErrorHandler {

	public static final int HTTP_RESPONSE_ERROR_STATUS_CODE = 1015;
	public static final int ACCESS_TOKEN_NOT_SET = 727;
	public static final int MISSING_REQUIRED_PARAM = 728;

	public static DoubanException accessTokenNotSet() {
		return new DoubanException(ACCESS_TOKEN_NOT_SET,
				"This method needs access token to gain accessability");
	}

	public static DoubanException missingRequiredParam() {
		return new DoubanException(MISSING_REQUIRED_PARAM,
				"This method is missing required params");
	}

	public static DoubanException cannotGetAccessToken() {
		return new DoubanException(ACCESS_TOKEN_NOT_SET,
				"Cannot get access token, IO exception");
	}

	public static DoubanException getCustomDoubanException(int code, String msg) {
		return new DoubanException(code, msg);
	}

	public static DoubanException handleHttpResponseError(
			HttpResponseException ex) {
		return new DoubanException(HTTP_RESPONSE_ERROR_STATUS_CODE,
				"HttpResponseException : http status : " + ex.getStatusCode()
						+ " message : " + ex.getMessage());
	}

	public static DoubanException wrongJsonFormat(String rawString) {
		return new DoubanException(100, "Illegal JSON format : " + rawString);
	}

}

package com.juzhai.dpsdk.exception;

import com.juzhai.dpsdk.json.model.JSONException;
import com.juzhai.dpsdk.json.model.JSONObject;

public class DianPingException extends Exception {
	private static final long serialVersionUID = 8445860030785783015L;
	private int statusCode = -1;
	private int errorCode = -1;
	private String request;
	private String error;

	public DianPingException(String msg) {
		super(msg);
	}

	public DianPingException(Exception cause) {
		super(cause);
	}

	public DianPingException(String msg, int statusCode) throws JSONException {
		super(msg);
		this.statusCode = statusCode;
	}

	public DianPingException(String msg, JSONObject json, int statusCode)
			throws JSONException {
		super(msg + "\n error:" + json.getString("error") + " error_code:"
				+ json.getInt("error_code") + json.getString("request"));
		this.statusCode = statusCode;
		this.errorCode = json.getInt("error_code");
		this.error = json.getString("error");
		this.request = json.getString("request");

	}

	public DianPingException(String msg, Exception cause) {
		super(msg, cause);
	}

	public DianPingException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;

	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getRequest() {
		return request;
	}

	public String getError() {
		return error;
	}

}

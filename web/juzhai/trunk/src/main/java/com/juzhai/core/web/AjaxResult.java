package com.juzhai.core.web;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.context.MessageSource;

import com.juzhai.core.util.JackSonSerializer;

public class AjaxResult {

	private final Log log = LogFactory.getLog(getClass());

	public final static AjaxResult ERROR_RESULT = new AjaxResult(false);

	private boolean success = true;
	private String errorCode;
	private String errorInfo;
	private Object result;

	public AjaxResult() {
		super();
	}

	public AjaxResult(boolean success) {
		super();
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String toJson() {
		try {
			return JackSonSerializer.toString(this);
		} catch (JsonGenerationException e) {
			log.error("Serialize AjaxResult to json failed", e);
		}
		return "{\"result\":false;\"errorCode\":\"serialize.error\"}";
	}

	public void setError(String errorCode, MessageSource messageSource,
			Object... params) {
		this.setSuccess(false);
		this.setErrorCode(errorCode);
		this.setErrorInfo(messageSource.getMessage(errorCode, params,
				Locale.SIMPLIFIED_CHINESE));
	}
}

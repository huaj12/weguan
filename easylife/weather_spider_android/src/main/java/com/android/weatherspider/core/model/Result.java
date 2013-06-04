package com.android.weatherspider.core.model;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.android.weatherspider.model.UserConfig;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result<T> {
	private T result;
	private String errorCode;
	private Boolean success;
	private String errorInfo;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public static class StringResult extends Result<String> {
	}

	public static class IntegerResult extends Result<Integer> {
	}

	public static class SetResult extends Result<Set<Integer>> {
	}

	public static class UserResult extends Result<UserConfig> {
	}
}

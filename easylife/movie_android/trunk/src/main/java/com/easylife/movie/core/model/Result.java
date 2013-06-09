package com.easylife.movie.core.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.easylife.movie.video.model.Video;

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

	public static class VideoListResult extends Result<PageList<Video>> {
	}

}

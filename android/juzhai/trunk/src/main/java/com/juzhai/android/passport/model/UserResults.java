package com.juzhai.android.passport.model;


public class UserResults {
	private User result;
	private String errorCode;
	private Boolean success;
	private String errorInfo;

	public User getResult() {
		return result;
	}

	public void setResult(User result) {
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

}

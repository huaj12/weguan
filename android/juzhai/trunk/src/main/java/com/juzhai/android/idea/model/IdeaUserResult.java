package com.juzhai.android.idea.model;

public class IdeaUserResult {
	private IdeaUserAndPager result;
	private String errorCode;
	private Boolean success;
	private String errorInfo;

	public IdeaUserAndPager getResult() {
		return result;
	}

	public void setResult(IdeaUserAndPager result) {
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

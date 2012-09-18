package com.juzhai.android.idea.model;

public class IdeaResult {
	private IdeaListAndPager result;
	private String errorCode;
	private Boolean success;
	private String errorInfo;

	public IdeaListAndPager getResult() {
		return result;
	}

	public void setResult(IdeaListAndPager result) {
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

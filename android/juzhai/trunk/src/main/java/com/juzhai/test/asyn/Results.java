package com.juzhai.test.asyn;

import java.util.List;

public class Results {
	private List<Category> result;
	private String errorCode;
	private Boolean success;
	private String errorInfo;

	public List<Category> getResult() {
		return result;
	}

	public void setResult(List<Category> result) {
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

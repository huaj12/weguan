package com.qq.oauth2.bean;

public class ResultBean {
	/** 错误标识 */
	private boolean error = false;

	/** 错误编号 */
	private String errorCode;

	/** 错误信息 */
	private String errorMsg;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}

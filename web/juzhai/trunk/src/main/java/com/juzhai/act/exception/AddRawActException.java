package com.juzhai.act.exception;

public class AddRawActException extends Exception{

	private static final long serialVersionUID = -804936980371548778L;
	
	public AddRawActException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public AddRawActException(String errorCode) {
		super(errorCode);
	}

	public AddRawActException() {
		super();
	}

	public String getErrorCode() {
		return super.getMessage();
	}
}

package com.juzhai.act.exception;

public class AddRawActException extends Exception {

	private static final long serialVersionUID = -804936980371548778L;
	public static final String ADD_RAWACT_IS_ERROR = "60005";
	public static final String NAME_IS_NULL = "60006";
	public static final String PROVINCE_IS_NULL = "60007";
	public static final String CITY_IS_NULL = "60008";
	public static final String NAME_IS_TOO_LONG = "60009";
	public static final String ADDRESS_IS_TOO_LONG = "60010";
	public static final String DETAIL_IS_TOO_LONG = "60011";

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

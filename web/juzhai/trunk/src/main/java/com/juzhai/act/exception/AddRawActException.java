package com.juzhai.act.exception;

public class AddRawActException extends Exception{

	private static final long serialVersionUID = -804936980371548778L;
	public static final String ADD_RAWACT_IS_ERROR="add.rawact.is.error";
	public static final String NAME_IS_NULL="rawact.name.is.null";
	public static final String PROVINCE_IS_NULL="rawact.province.is.null";
	public static final String CITY_IS_NULL="rawact.city.is.null";
	public static final String NAME_IS_TOO_LONG="rawact.name.is.too.long";
	public static final String ADDRESS_IS_TOO_LONG="rawact.address.is.too.long";
	public static final String DETAIL_IS_TOO_LONG="rawact.detail.is.too.long";
	
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

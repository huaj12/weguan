package com.juzhai.core.exception;

public class UploadImageException extends JuzhaiException {
	private static final long serialVersionUID = -3616006775719455060L;

	public static final String UPLOAD_ERROR = "50001";
	public static final String UPLOAD_SIZE_ERROR = "50002";
	public static final String UPLOAD_TYPE_ERROR = "50003";
	public static final String UPLOAD_CUT_ERROR = "50004";
	public static final String UPLOAD_REDUCE_ERROR = "50005";

	public UploadImageException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public UploadImageException(String errorCode) {
		super(errorCode);
	}
}

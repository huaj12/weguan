package com.juzhai.core.exception;

public class UploadImageException extends JuzhaiException {
	private static final long serialVersionUID = -3616006775719455060L;
	
	public static final String UPLOAD_SIZE_ERROR  = "uoload.size.error";
	public static final String UPLOAD_TYPE_ERROR  = "upload.type.error";
	public static final String UPLOAD_FILE_ISNULL="upload.file.isnull";
	public static final String UPLOAD_ERROR  = "upload.error";
	public UploadImageException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public UploadImageException(String errorCode) {
		super(errorCode);
	}

	public UploadImageException() {
		super();
	}

	public String getErrorCode() {
		return super.getMessage();
	}
}

package com.juzhai.post.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputPostWindowException  extends JuzhaiException  {

	private static final long serialVersionUID = 6518141399436557560L;
	
	
	public InputPostWindowException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputPostWindowException(String errorCode) {
		super(errorCode);
	}
}

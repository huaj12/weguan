/* 
 * ConfigException.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.core;

/**
 * Config information exception, thrown when error occurs in reading and
 * parsing.
 * 
 * @author xiaolin
 * 
 *         2008-5-8 create
 */
public class ConfigException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigException() {
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

}

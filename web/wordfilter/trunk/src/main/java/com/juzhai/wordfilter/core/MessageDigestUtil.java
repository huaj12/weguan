/* 
 * MessageDigestUtil.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * message digest for user and ip information
 * 
 * @author xiaolin
 * 
 *         2008-3-26 create
 */
public class MessageDigestUtil {

	private static MessageDigest user_md;
	private static MessageDigest ip_md;

	public static void init() throws NoSuchAlgorithmException {
		user_md = MessageDigest.getInstance("MD5");
		ip_md = MessageDigest.getInstance("MD5");
	}

	public static byte[] updateAndDigestForUser(byte[] text, int offset, int len) {
		synchronized (user_md) {
			user_md.update(text, 0, len);
			return user_md.digest();
		}
	}

	public static byte[] updateAndDigestForIp(byte[] text, int offset, int len) {
		synchronized (ip_md) {
			ip_md.update(text, 0, len);
			return ip_md.digest();
		}
	}
}

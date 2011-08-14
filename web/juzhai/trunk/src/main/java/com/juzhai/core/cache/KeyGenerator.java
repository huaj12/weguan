/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.core.cache;

import org.apache.commons.lang.StringUtils;

public abstract class KeyGenerator {

	private static final String CACHE_KEY_SEPARATOR = ".";

	protected static String genKey(long uid, String... funcs) {
		StringBuilder sb = new StringBuilder();
		sb.append(uid);
		for (String func : funcs) {
			if (StringUtils.isNotEmpty(func)) {
				sb.append(CACHE_KEY_SEPARATOR).append(func);
			}
		}
		return sb.toString();
	}
}

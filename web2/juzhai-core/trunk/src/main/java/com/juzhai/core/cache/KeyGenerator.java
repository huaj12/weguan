/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.core.cache;

import org.apache.commons.lang.StringUtils;

public abstract class KeyGenerator {

	public static final String CACHE_KEY_SEPARATOR = ".";

	protected static String genKey(long primaryKey, String... funcs) {
		StringBuilder sb = new StringBuilder();
		sb.append(primaryKey);
		for (String func : funcs) {
			if (StringUtils.isNotEmpty(func)) {
				sb.append(CACHE_KEY_SEPARATOR).append(func);
			}
		}
		return sb.toString();
	}

	// protected static String genKey(long primaryKey1, long primaryKey2,
	// MsgType type, String... funcs) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(primaryKey1);
	// sb.append(CACHE_KEY_SEPARATOR);
	// sb.append(primaryKey2);
	// sb.append(CACHE_KEY_SEPARATOR);
	// sb.append(type.name());
	// for (String func : funcs) {
	// if (StringUtils.isNotEmpty(func)) {
	// sb.append(CACHE_KEY_SEPARATOR).append(func);
	// }
	// }
	// return sb.toString();
	// }

	// protected static String genKey(String tpIdeneity, long tpId,
	// String... funcs) {
	// StringBuilder sb = new StringBuilder();
	// Thirdparty tp = InitData.TP_MAP.get(tpId);
	// Assert.notNull(tp, "Can not find thirdparty by tpId[" + tpId + "].");
	// sb.append(tpIdeneity).append("@").append(tp.getName());
	// for (String func : funcs) {
	// if (StringUtils.isNotEmpty(func)) {
	// sb.append(CACHE_KEY_SEPARATOR).append(func);
	// }
	// }
	// return sb.toString();
	// }
}

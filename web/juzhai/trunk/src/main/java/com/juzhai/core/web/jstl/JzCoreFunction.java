package com.juzhai.core.web.jstl;

import com.juzhai.core.util.StaticUtil;
import com.juzhai.core.util.TextTruncateUtil;

public class JzCoreFunction {

	public static String u(String uri) {
		return StaticUtil.u(uri);
	}

	public static String truncate(String originalText, int targetLength,
			String suffix) {
		return TextTruncateUtil.truncate(originalText, targetLength, suffix);
	}
}

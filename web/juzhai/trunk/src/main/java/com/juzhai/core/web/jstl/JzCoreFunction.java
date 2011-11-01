package com.juzhai.core.web.jstl;

import java.util.Date;

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

	public static boolean dateAfter(Date date) {
		return date.before(new Date());
	}
}

package com.juzhai.core.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static int chineseLength(String text) {
		if (StringUtils.isEmpty(text)) {
			return 0;
		}
		int length = 0;
		char[] charArray = text.toCharArray();
		for (char c : charArray) {
			if (c < 0x80) {
				length += 1;
			} else {
				length += 2;
			}
		}
		return length;
	}

}

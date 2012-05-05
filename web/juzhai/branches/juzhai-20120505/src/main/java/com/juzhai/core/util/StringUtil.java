package com.juzhai.core.util;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;
import java.util.regex.Pattern;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static final String EMAIL_PATTERN_STRING = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$";
	public static final Pattern EMAIL_PATTERN = Pattern
			.compile(EMAIL_PATTERN_STRING);

	public static char separator = (char) 036;

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

	public static String encodeURI(String url, String env)
			throws UnsupportedEncodingException {
		BitSet urlsafe = new BitSet();
		// alpha characters
		for (int i = 'a'; i <= 'z'; i++) {
			urlsafe.set(i);
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			urlsafe.set(i);
		}
		// numeric characters
		for (int i = '0'; i <= '9'; i++) {
			urlsafe.set(i);
		}
		// special chars
		urlsafe.set('-');
		urlsafe.set('_');
		urlsafe.set('.');
		urlsafe.set('*');
		// blank to be replaced with +
		urlsafe.set(' ');
		urlsafe.set(':');
		urlsafe.set('/');
		urlsafe.set('=');
		urlsafe.set('#');
		urlsafe.set('?');
		urlsafe.set('&');
		urlsafe.set('%');

		return new String(URLCodec.encodeUrl(urlsafe, url.getBytes(env)));
	}

	public static boolean checkMailFormat(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		email = StringUtils.trim(email);
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			return false;
		}
		return true;
	}
}

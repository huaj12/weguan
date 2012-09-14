package com.juzhai.android.core.utils;

import java.util.regex.Pattern;

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

	public static String decodeUnicode(String theString) {
		if (StringUtils.isEmpty(theString)) {
			return StringUtils.EMPTY;
		}
		char aChar;
		int len = theString.length();
		StringBuilder outBuilder = new StringBuilder(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuilder.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuilder.append(aChar);
				}
			} else
				outBuilder.append(aChar);
		}
		return outBuilder.toString();

	}

}

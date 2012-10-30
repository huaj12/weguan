package com.juzhai.android.core.utils;


public class TextTruncateUtil {

	/**
	 * 根据字节数来截字（中文算两个字节）并加上自定义的后缀，后缀算在截字字数中
	 * 
	 * @param originalText
	 *            需要被截字的内容
	 * @param targetLength
	 *            截取的字节数
	 * @param suffix
	 *            后缀 例如“...”
	 * @return 截完之后的结果，带有后缀
	 */
	public static String truncate(String originalText, int targetLength,
			String suffix) {
		if (suffix == null) {
			suffix = "";
		}
		if ((originalText == null) || (originalText.length() == 0)) {
			return StringUtil.EMPTY;
		}
		if (targetLength <= 0) {
			return originalText.substring(0, 1) + suffix;
		}
		StringBuilder textBuilder = new StringBuilder(originalText);
		int originalTextLength = StringUtil.chineseLength(originalText);
		int truncateLength = targetLength - StringUtil.chineseLength(suffix);
		if (targetLength == 1 && originalTextLength == 2
				&& StringUtil.chineseLength(originalText.substring(0, 1)) == 2) {
			return originalText;
		}
		if (targetLength >= originalTextLength) {
			return originalText;
		}
		if (truncateLength < StringUtil.chineseLength(originalText.substring(0,
				1))) {
			return textBuilder.substring(0, 1) + suffix;
		}
		if (truncateLength >= originalTextLength) {
			return originalText;
		}

		int beginIndex = 0;
		int endIndex = textBuilder.length();
		int middleIndex = (beginIndex + endIndex) / 2;
		while (beginIndex < endIndex) {
			int tempLength = StringUtil.chineseLength(textBuilder.substring(0,
					middleIndex));
			if (tempLength > truncateLength) {
				endIndex = middleIndex;
			} else {
				if (tempLength == truncateLength)
					break;
				beginIndex = middleIndex;
			}

			middleIndex = (beginIndex + endIndex) / 2;

			if (beginIndex == middleIndex) {
				break;
			}
		}
		return textBuilder.substring(0, middleIndex) + suffix;
	}
}
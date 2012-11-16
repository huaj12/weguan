package com.juzhai.common.service;

public interface IEmojiRemoteService {

	/**
	 * 转换到ios4和ad的表情
	 * 
	 * @param string
	 * @return
	 */
	String transToIOS4AndAdEmoji(String string);

	/**
	 * 转换到ios5表情
	 * 
	 * @param string
	 * @return
	 */
	String transToIOS5Emoji(String string);
}

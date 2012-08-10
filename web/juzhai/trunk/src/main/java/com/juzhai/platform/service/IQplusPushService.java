package com.juzhai.platform.service;

public interface IQplusPushService {
	/**
	 * q+ push
	 * 
	 * @param openid
	 * @param text
	 * @param link
	 */
	void push(String openid, String text, String link);
}

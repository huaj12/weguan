package com.juzhai.verifycode.service;

import java.awt.image.BufferedImage;

import com.juzhai.verifycode.bean.VerifyLevel;

public interface IVerifyCodeService {
	/**
	 * 获取验证码(首先验证key是否有效)
	 * 
	 * @return
	 */
	BufferedImage createVerifyCode(String key, VerifyLevel level);

	/**
	 * 获取验证码key
	 * 
	 * @return
	 */
	String getVerifyCodeKey();

	/**
	 * 判断用户输入的验证码是否正确
	 * 
	 * @param key
	 * @param input
	 * @return
	 */
	boolean verify(String key, String input);
}

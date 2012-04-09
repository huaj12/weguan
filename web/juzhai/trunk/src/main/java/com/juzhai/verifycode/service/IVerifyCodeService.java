package com.juzhai.verifycode.service;

import com.juzhai.verifycode.bean.VerifyCode;
import com.juzhai.verifycode.bean.VerifyLevel;

public interface IVerifyCodeService {
	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	VerifyCode createVerifyCode(VerifyLevel level);
}

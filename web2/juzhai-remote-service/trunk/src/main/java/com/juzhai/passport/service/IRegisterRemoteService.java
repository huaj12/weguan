package com.juzhai.passport.service;

import com.juzhai.passport.bean.DeviceName;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ProfileInputException;

public interface IRegisterRemoteService {

	/**
	 * 注册
	 * 
	 * @param email
	 * @param nickname
	 * @param pwd
	 * @param confirmPwd
	 * @return
	 * @throws RegisterException
	 * @throws ProfileInputException
	 */
	public long register(String email, String nickname, String pwd,
			String confirmPwd, long inviterUid, DeviceName deviceName)
			throws PassportAccountException, ProfileInputException;

	/**
	 * 发送邮箱验证邮件
	 * 
	 * @param uid
	 * @throws PassportAccountException
	 */
	void sendAccountMail(long uid) throws PassportAccountException;

	/**
	 * 发送重置密码邮件
	 * 
	 * @param uid
	 */
	void sendResetPwdMail(String loginName);
}

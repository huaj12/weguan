/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;

public interface IRegisterService {

	/**
	 * 自动注册（第三方用户）
	 * 
	 * @param tp
	 * @param identity
	 * @param profile
	 * @return
	 */
	public long autoRegister(Thirdparty tp, String identity, AuthInfo authInfo,
			Profile profile, long inviterUid);

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
			String confirmPwd, long inviterUid)
			throws PassportAccountException, ProfileInputException;

	/**
	 * 设置账号
	 * 
	 * @param uid
	 * @param email
	 * @param pwd
	 * @param confirmPwd
	 * @throws RegisterException
	 */
	public void setAccount(long uid, String email, String pwd, String confirmPwd)
			throws PassportAccountException;

	/**
	 * 修改密码
	 * 
	 * @param uid
	 * @param oldPwd
	 * @param newPwd
	 * @param confirmPwd
	 * @throws RegisterException
	 */
	public void modifyPwd(long uid, String oldPwd, String newPwd,
			String confirmPwd) throws PassportAccountException;

	/**
	 * 充值密码
	 * 
	 * @param uid
	 * @param pwd
	 * @param confirmPwd
	 * @throws PassportAccountException
	 */
	public void resetPwd(long uid, String pwd, String confirmPwd, String code)
			throws PassportAccountException;
}

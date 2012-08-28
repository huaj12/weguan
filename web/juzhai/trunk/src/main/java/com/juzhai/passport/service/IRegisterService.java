/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.DeviceName;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Passport;
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
			Profile profile, long inviterUid, DeviceName deviceName);

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

	/**
	 * 是否有账号
	 * 
	 * @param uid
	 * @return
	 * @throws PassportAccountException
	 */
	public boolean hasAccount(long uid) throws PassportAccountException;

	/**
	 * 是否有账号
	 * 
	 * @param uid
	 * @return
	 * @throws PassportAccountException
	 */
	public boolean hasAccount(Passport passport);

	/**
	 * 账号是否存在
	 * 
	 * @param account
	 * @return
	 */
	public boolean existAccount(String account);

	/**
	 * 是否验证邮箱
	 * 
	 * @param uid
	 * @return
	 * @throws PassportAccountException
	 */
	public boolean hasActiveEmail(long uid) throws PassportAccountException;

	/**
	 * 是否验证邮箱
	 * 
	 * @param uid
	 * @return
	 * @throws PassportAccountException
	 */
	public boolean hasActiveEmail(Passport passport);

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

	/**
	 * 激活邮箱
	 * 
	 * @param code
	 * @return
	 */
	boolean activeAccount(String code);

	/**
	 * 注册第三方账户
	 * 
	 * @param tp
	 * @param identity
	 * @param passport
	 */
	public void registerTpUser(Thirdparty tp, String identity, Passport passport);

}

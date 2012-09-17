package com.juzhai.android.passport.service;

import android.content.Context;

import com.juzhai.android.passport.exception.PassportException;

public interface IPassportService {

	/**
	 * 判断当前是否自动登录成功
	 * 
	 * @param context
	 * @return
	 */
	boolean checkLogin(Context context);

	/**
	 * 登录
	 * 
	 * @param context
	 * @param account
	 * @param password
	 * @throws PassportException
	 */
	void login(Context context, String account, String password)
			throws PassportException;

	/**
	 * 注册
	 * 
	 * @param context
	 * @param account
	 * @param nickname
	 * @param pwd
	 * @param confirmPwd
	 */
	void register(Context context, String account, String nickname, String pwd,
			String confirmPwd) throws PassportException;

	/**
	 * 找回密码
	 * 
	 * @param context
	 * @param account
	 * @throws PassportException
	 */
	void getbackPwd(Context context, String account) throws PassportException;

	/**
	 * 第三方access
	 * 
	 * @param context
	 * @param tpId
	 * @param queryString
	 * @throws PassportException
	 */
	void tpLogin(Context context, long tpId, String queryString)
			throws PassportException;
}

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
}

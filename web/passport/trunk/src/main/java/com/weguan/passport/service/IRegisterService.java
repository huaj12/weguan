package com.weguan.passport.service;

import com.weguan.passport.exception.RegisterException;
import com.weguan.passport.form.ModifyForm;
import com.weguan.passport.form.RegisterForm;

public interface IRegisterService {

	/**
	 * 注册
	 * 
	 * @param registerForm
	 * @return
	 */
	public long register(RegisterForm registerForm) throws RegisterException;

	/**
	 * 修改注册信息
	 * 
	 * @param uid
	 * @param modifyForm
	 * @return
	 * @throws RegisterException
	 */
	public void modify(long uid, ModifyForm modifyForm)
			throws RegisterException;
}

package com.weguan.passport.service;

import com.weguan.passport.exception.RegisterException;
import com.weguan.passport.form.RegisterForm;

public interface IRegisterService {

	/**
	 * 注册
	 * 
	 * @param registerForm
	 * @return
	 */
	public long register(RegisterForm registerForm) throws RegisterException;
}

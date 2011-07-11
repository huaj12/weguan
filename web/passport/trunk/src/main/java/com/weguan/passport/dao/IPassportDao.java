package com.weguan.passport.dao;

import com.weguan.passport.model.Passport;

public interface IPassportDao {

	/**
	 * 根据用户名查询Passport
	 * 
	 * @param userName
	 * @return
	 */
	public Passport selectPassportByUserName(String userName);

	/**
	 * 根据用户名计数
	 * 
	 * @param userName
	 * @return
	 */
	public int countPassportByUserName(String userName);
}

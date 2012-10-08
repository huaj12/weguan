package com.juzhai.android.home.service;

import android.content.Context;

import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.home.exception.HomeException;

public interface IInterestUserService {
	/**
	 * 获取关注列表
	 * 
	 * @param page
	 * @return
	 */
	UserListResult interestList(Context context, int page) throws HomeException;

	/**
	 * 获取粉丝列表
	 * 
	 * @param page
	 * @return
	 */
	UserListResult interestMeList(Context context, int page)
			throws HomeException;
}

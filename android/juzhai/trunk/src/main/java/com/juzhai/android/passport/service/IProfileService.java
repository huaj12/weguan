package com.juzhai.android.passport.service;

import android.content.Context;

import com.juzhai.android.passport.exception.ProfileException;
import com.juzhai.android.passport.model.User;

public interface IProfileService {
	/**
	 * 更新用户资料
	 * 
	 * @param user
	 * @param context
	 */
	void updateUser(User user, Context context) throws ProfileException;
}

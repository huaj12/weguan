package com.juzhai.passport.service;

import com.juzhai.passport.model.UserGuide;

public interface IUserGuideService extends IUserGuideRemoteService {

	/**
	 * 创建userGuide数据
	 * 
	 * @param uid
	 */
	void craeteUserGuide(long uid);

	/**
	 * 进入下一个引导
	 * 
	 * @param uid
	 */
	void nextGuide(long uid);
}

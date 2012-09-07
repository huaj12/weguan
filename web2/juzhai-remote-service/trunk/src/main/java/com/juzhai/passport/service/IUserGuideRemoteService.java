package com.juzhai.passport.service;

import com.juzhai.passport.model.UserGuide;

public interface IUserGuideRemoteService {

	/**
	 * 是否完成了引导
	 * 
	 * @param uid
	 * @return
	 */
	boolean isCompleteGuide(long uid);

	/**
	 * 获取用户引导信息
	 * 
	 * @param uid
	 *            用户ID
	 * @return
	 */
	UserGuide getUserGuide(long uid);

	/**
	 * 创建和完成guide
	 * 
	 * @param uid
	 */
	void createAndCompleteGuide(long uid);

	/**
	 * 完成引导
	 * 
	 * @param uid
	 */
	void completeGuide(long uid);
}

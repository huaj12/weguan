package com.juzhai.passport.service;

import com.juzhai.passport.model.UserGuide;

public interface IUserGuideService {

	/**
	 * 创建userGuide数据
	 * 
	 * @param uid
	 */
	void craeteUserGuide(long uid);

	/**
	 * 获取用户引导信息
	 * 
	 * @param uid
	 *            用户ID
	 * @return
	 */
	UserGuide getUserGuide(long uid);

	/**
	 * 是否完成了引导
	 * 
	 * @param uid
	 * @return
	 */
	boolean isCompleteGuide(long uid);

	/**
	 * 进入下一个引导
	 * 
	 * @param uid
	 */
	void nextGuide(long uid);

	/**
	 * 完成引导
	 * 
	 * @param uid
	 */
	void completeGuide(long uid);
}

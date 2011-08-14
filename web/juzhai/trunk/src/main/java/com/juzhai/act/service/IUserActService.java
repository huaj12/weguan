/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

public interface IUserActService {

	/**
	 * 用户想去活动
	 * 
	 * @param uid
	 * @param actId
	 * @param friendId
	 */
	void wantToAct(long uid, long actId, long friendId);

	/**
	 * 用户看情况去活动
	 * 
	 * @param uid
	 * @param actId
	 * @param friendId
	 */
	void dependToAct(long uid, long actId, long friendId);

	/**
	 * 添加拒宅活动（用户可以自定义，但是不会立即对其他用户生效，通过验证后，会被其他人使用）
	 * 
	 * @param uid
	 *            用户ID
	 * @param actName
	 *            活动名称
	 */
	void addAct(long uid, String actName);

	/**
	 * 添加已经存在的拒宅活动
	 * 
	 * @param uid
	 *            用户ID
	 * @param actId
	 *            活动名称
	 */
	void addAct(long uid, long actId);

	/**
	 * 删除我的拒宅活动
	 * 
	 * @param uid
	 * @param actId
	 */
	void removeAct(long uid, long actId);
}

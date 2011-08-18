/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.exception.ActInputException;

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
	 * @throws ActInputException
	 */
	void addAct(long uid, String actName) throws ActInputException;

	/**
	 * 添加已经存在的拒宅活动
	 * 
	 * @param uid
	 *            用户ID
	 * @param actId
	 *            活动名称
	 * @throws ActInputException
	 */
	void addAct(long uid, long actId) throws ActInputException;

	/**
	 * 删除我的拒宅活动
	 * 
	 * @param uid
	 * @param actId
	 */
	void removeAct(long uid, long actId);

	/**
	 * 从缓存根据用户id获取Act(不带热度),根据热度排序
	 * 
	 * @param uid
	 * @return
	 */
	List<Long> getActByUidFromCache(long uid);
}

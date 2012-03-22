package com.juzhai.home.service;

import com.juzhai.post.controller.view.PostView;

public interface IGuessYouService {

	/**
	 * 更新你喜欢的人
	 * 
	 * @param userId
	 */
	void updateLikeUsers(long uid);

	/**
	 * 有没有喜欢的人
	 * 
	 * @param uid
	 * @return
	 */
	boolean existLikeUsers(long uid);

	/**
	 * 有没有解救小宅的数据
	 * 
	 * @param uid
	 * @return
	 */
	boolean existRescueUsers(long uid);

	/**
	 * 从解救用户列表里移除某一用户
	 * 
	 * @param uid
	 * @param removeUid
	 */
	void removeFromRescueUsers(long uid, long removeUid);

	/**
	 * 清空解救小宅数据
	 * 
	 * @param uid
	 */
	void clearRescueUsers(long uid);

	/**
	 * 随机一个解救用户
	 * 
	 * @param uid
	 * @return
	 */
	PostView randomRescue(long uid);
}

/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.juzhai.passport.bean.FriendsBean;
import com.juzhai.passport.bean.TpFriend;

public interface IFriendService {

	/**
	 * 获取所有好友（包括安装应用的好友和第三方所有好友）
	 * 
	 * @param uid
	 * @return
	 */
	FriendsBean getAllFriends(long uid);

	/**
	 * 获取安装了应用的好友，带亲密度
	 * 
	 * @param uid
	 * @return
	 */
	Map<Long, Integer> getAppFriendsWithIntimacy(long uid);

	/**
	 * 获取安装了应用的好友
	 * 
	 * @param uid
	 * @return
	 */
	Set<Long> getAppFriends(long uid);

	/**
	 * 获取未安装应用的好友
	 * 
	 * @param uid
	 * @return
	 */
	List<TpFriend> getTpFriends(long uid);

	/**
	 * 更新好友列表（从第三方获取，缓存时间为1天）
	 * 
	 * @param uid
	 * @param tpId
	 */
	void updateExpiredFriends(long uid, long tpId);

	/**
	 * 好友列表是否已经过期
	 * 
	 * @param uid
	 * @return
	 */
	boolean isExpired(long uid);

	/**
	 * 增加或减少亲密度
	 * 
	 * @param uid
	 * @param friendId
	 * @param intimacy
	 */
	void incrOrDecrIntimacy(long uid, long friendId, int intimacy);
}

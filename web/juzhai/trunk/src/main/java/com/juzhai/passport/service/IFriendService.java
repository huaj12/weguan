/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import com.juzhai.passport.bean.FriendsBean;

public interface IFriendService {

	/**
	 * 获取好友
	 * 
	 * @param uid
	 * @param tpId
	 * @return
	 */
	FriendsBean getFriends(long uid, long tpId);
}

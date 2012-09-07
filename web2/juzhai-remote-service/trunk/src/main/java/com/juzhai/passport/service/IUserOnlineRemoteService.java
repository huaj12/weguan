package com.juzhai.passport.service;

import java.util.Date;

public interface IUserOnlineRemoteService {

	/**
	 * 获取最后的在线时间
	 * 
	 * @param uid
	 * @return
	 */
	Date getLastUserOnlineTime(long uid);

	/**
	 * 设置用户最后在线时间
	 */
	void setLastUserOnlineTime(long uid);

	/**
	 * 更新用户最后在线时间缓存
	 * 
	 * @param uid
	 */
	void updateUserOnlineTimeCache(long uid, Date cDate);
}

package com.juzhai.passport.service;

import java.util.Date;

/**
 * 用户在线时间
 * 
 * @author kooks
 * 
 */
public interface IUserOnlineService {
	/**
	 * 设置用户最后在线时间
	 */
	void setLastUserOnlineTime(long uid);

	/**
	 * 是否能更新用户最后在线时间
	 * 
	 * @param uid
	 */
	boolean isUpdateUserOnlineTime(long uid);

	/**
	 * 更新用户最后在线时间缓存
	 * 
	 * @param uid
	 */
	void updateUserOnlineTimeCache(long uid, Date cDate);

}

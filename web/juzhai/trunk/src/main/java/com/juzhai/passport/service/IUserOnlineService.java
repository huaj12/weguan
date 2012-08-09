package com.juzhai.passport.service;

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

}

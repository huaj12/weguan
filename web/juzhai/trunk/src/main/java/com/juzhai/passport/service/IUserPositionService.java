package com.juzhai.passport.service;

public interface IUserPositionService {

	/**
	 * 更新用户的当前坐标
	 * 
	 * @param uid
	 * @param longitude
	 * @param latitude
	 */
	void updatePosition(long uid, double longitude, double latitude);
}

package com.juzhai.home.service;

import java.util.List;

import com.juzhai.platform.bean.UserStatus;

public interface IUserStatusService {
	/**
	 * 
	 * @param uid
	 *            登录者id
	 * @param tpId
	 *            登录者tpid
	 * @param fuid
	 *            被查看者id
	 * @return
	 */
	List<UserStatus> listUserStatus(long uid, long tpId, long fuid);

	/**
	 * 查看某用户最新微博
	 * 
	 * @param fuid
	 * @return
	 */
	List<UserStatus> listUserStatus(long fuid);

	/**
	 * 刷新用户最新微博
	 * 
	 * @param uid
	 * @param tpId
	 */
	void updateUserStatus(long uid, long tpId);

}

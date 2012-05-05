package com.juzhai.platform.service;

import java.util.List;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;

/**
 * 第三方关系获取
 * @author Administrator
 *
 */
public interface IRelationshipService {
	/**
	 * 获取好友列表
	 * 
	 * @param authInfo
	 * @return
	 */
	public List<TpFriend> getAllFriends(AuthInfo authInfo);

	/**
	 * 获取安装了应用的好友列表
	 * 
	 * @param authInfo
	 * @return
	 */
	public List<String> getAppFriends(AuthInfo authInfo);

	/**
	 * 获取安装的可能认识的
	 * 
	 * @param authInfo
	 * @return
	 */
	public List<String> getInstallFollows(AuthInfo authInfo);
}

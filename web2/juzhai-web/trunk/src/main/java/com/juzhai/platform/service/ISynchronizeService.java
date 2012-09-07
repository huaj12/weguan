package com.juzhai.platform.service;

import java.util.List;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.bean.UserStatus;

public interface ISynchronizeService {
	/**
	 * 获取用户最近的3条微博缓存24小时
	 * 
	 * @param uid
	 *            登录者id
	 * @param fuid
	 *            被查看者id
	 * @param tpId
	 *            登录者tpid
	 * @return
	 */
	List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size);

	/**
	 * 发送微博
	 * 
	 * @param authInfo
	 * @param text
	 * @param fuid
	 * @param image
	 */
	void sendMessage(AuthInfo authInfo, String title, String text, String link,
			byte[] image, String imageUrl);

	/**
	 * 发送邀请
	 * 
	 * @param authInfo
	 * @param text
	 * @param image
	 */
	void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids);

	/**
	 * 发送通知
	 * 
	 * @param authInfo
	 * @param fuids
	 * @param text
	 */
	void notifyMessage(AuthInfo authInfo, String[] fuids, String text);
}

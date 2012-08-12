package com.juzhai.plug.service;

import java.util.List;

public interface IInviteService {
	/**
	 * 发送邀请
	 * 
	 * @param content
	 * @param actId
	 * @param tpId
	 * @param uid
	 * @return
	 */
	boolean sendIvite(String content, long tpId, long uid, List<String> fuids);

	/**
	 * 显示邀请内容
	 * 
	 * @param uids
	 * @param names
	 * @param tpId
	 * @param uid
	 * @return
	 */
	String showInvite(String fuids, String names, long tpId, long uid);

	/**
	 * 邀请第三方用户
	 */
	void inviteSynchronize(long uid, long tpId, String content);
}

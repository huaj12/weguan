package com.juzhai.passport.service;

public interface IInviteRemoteService {

	/**
	 * 邀请第三方用户
	 */
	void inviteSynchronize(long uid, long tpId, String content);
}

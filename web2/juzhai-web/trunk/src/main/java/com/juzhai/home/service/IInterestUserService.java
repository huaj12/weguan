package com.juzhai.home.service;

import java.util.List;

import com.juzhai.home.exception.InterestUserException;

public interface IInterestUserService extends IInterestUserRemoteService {

	/**
	 * 感兴趣的人Id列表
	 * 
	 * @param uid
	 * @return
	 */
	List<Long> interestUids(long uid);

	/**
	 * 收藏感兴趣的人
	 * 
	 * @param uid
	 * @param targetUid
	 * @throws InterestUserException
	 */
	void interestUser(long uid, long targetUid, String content)
			throws InterestUserException;
}

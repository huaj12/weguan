package com.juzhai.home.service;

import java.util.List;

public interface IInterestUserService extends IInterestUserRemoteService {

	/**
	 * 感兴趣的人Id列表
	 * 
	 * @param uid
	 * @return
	 */
	List<Long> interestUids(long uid);
}

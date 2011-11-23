package com.juzhai.passport.service;

public interface IUserSetupService {

	/**
	 * 是否第三方通知
	 * 
	 * @param uid
	 * @param isAdvise
	 * @return
	 */
	boolean setupTpAdvise(long uid, boolean isAdvise);

	/**
	 * 获取用户是否发送第三方通知
	 * 
	 * @param uid
	 * @return
	 */
	boolean isTpAdvise(long uid);
}

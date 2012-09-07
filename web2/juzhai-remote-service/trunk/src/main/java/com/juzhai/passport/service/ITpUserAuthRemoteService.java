package com.juzhai.passport.service;

public interface ITpUserAuthRemoteService {

	/**
	 * token是否过期
	 * 
	 * @param uid
	 * @param tpId
	 * @return
	 */
	boolean isTokenExpired(long uid, long tpId);
}

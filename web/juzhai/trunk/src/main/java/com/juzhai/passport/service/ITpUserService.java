package com.juzhai.passport.service;

import com.juzhai.passport.model.TpUser;

public interface ITpUserService {

	TpUser getTpUserByTpIdAndIdentity(long tpId, String identity);

	/**
	 * 根据用户ID，查找第三方用户
	 * 
	 * @param uid
	 * @return
	 */
	TpUser getTpUserByUid(long uid);

	/**
	 * 替换新的第三方id
	 * 
	 * @param uid
	 * @param newTpIdentity
	 */
	void updateTpIdentity(long uid, String newTpIdentity);

	/**
	 * 判断第三方帐号是否存在
	 * 
	 * @param tpId
	 * @param identity
	 * @return
	 */
	boolean existTpUserByTpIdAndIdentity(long tpId, String identity);
}

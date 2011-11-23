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
}

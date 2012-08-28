package com.juzhai.passport.service;

import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Thirdparty;
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

	/**
	 * 注册第三方账户
	 * 
	 * @param tp
	 * @param identity
	 * @param passport
	 */
	// TODO (done)
	// 既然有多个地方要使用，变为公共方法，那就把方法移入TpUserService,方法的实现应该由TpUserService负责
	public void registerTpUser(Thirdparty tp, String identity, Passport passport);
}

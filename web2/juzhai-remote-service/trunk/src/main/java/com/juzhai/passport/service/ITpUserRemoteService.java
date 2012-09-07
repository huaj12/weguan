package com.juzhai.passport.service;

import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;

public interface ITpUserRemoteService {

	/**
	 * 根据用户ID，查找第三方用户
	 * 
	 * @param uid
	 * @return
	 */
	TpUser getTpUserByUid(long uid);

	/**
	 * 根据用户Id和JoinType获取对应的Tp（当一个帐号能绑定多个第三方，则此方法将删除）
	 * 
	 * @param uid
	 * @param joinType
	 * @return
	 */
	Thirdparty getTpByUidAndJoinType(long uid, JoinTypeEnum joinType);
}

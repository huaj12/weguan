/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.dao;

import com.juzhai.passport.model.TpUser;

public interface ITpUserDao {

	/**
	 * 根据第三方和在第三方的唯一标识符查询tpUser对象
	 * 
	 * @param tpName
	 * @param tpIdentity
	 * @return
	 */
	TpUser selectTpUserByTpNameAndTpIdentity(String tpName, String tpIdentity);
}

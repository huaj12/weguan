package com.juzhai.account.dao;

public interface IAccountDao {

	/**
	 * 更新积点
	 * 
	 * @param uid
	 *            用户ID
	 * @param point
	 *            更新的积点（正数为加，负数为减）
	 * @return 更新的条数，若为0，则表示失败（通常是不够减）
	 */
	int updatePoint(long uid, int point);
}

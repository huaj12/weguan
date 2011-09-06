package com.juzhai.account.service;

import com.juzhai.account.bean.ConsumeAction;
import com.juzhai.account.bean.ProfitAction;

public interface IAccountService {

	/**
	 * 创建用户账户
	 * 
	 * @param uid
	 */
	void createAccount(long uid);

	/**
	 * 检查用户积点
	 * 
	 * @param uid
	 * @return
	 */
	int queryPoint(long uid);

	/**
	 * 检查用户积点
	 * 
	 * @param uid
	 * @return
	 */
	boolean checkPoint(long uid, ConsumeAction consumeAction);

	/**
	 * 使用积点
	 * 
	 * @param uid
	 * @return
	 */
	boolean consumePoint(long uid, ConsumeAction consumeAction);

	/**
	 * 增加积点
	 * 
	 * @param uid
	 * @return
	 */
	void profitPoint(long uid, ProfitAction profitAction);
}

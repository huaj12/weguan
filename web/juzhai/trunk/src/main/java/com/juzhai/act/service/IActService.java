/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

public interface IActService {

	// /**
	// * 添加Act，用户添加，需要后台审核
	// *
	// * @param name
	// */
	// void addAct(String name);

	/**
	 * 审核用户自定义的Act
	 * 
	 * @param rawActId
	 * @param actCategoryIds
	 *            用逗号隔开的行为分类ID
	 */
	void verifyAct(long rawActId, String actCategoryIds);

}

/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

public interface IActService {

	/**
	 * 审核用户自定义的Act
	 * 
	 * @param rawActId
	 * @param actCategoryIds
	 *            用逗号隔开的行为分类ID
	 */
	void verifyAct(long rawActId, String actCategoryIds);

	/**
	 * 替换Act
	 * 
	 * @param srcActId
	 *            要被替换的ActId
	 * @param desActId
	 *            要替换成的ActId
	 */
	void replaceAct(long srcActId, long desActId);
}

/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

public interface IUserActService {

	/**
	 * 用户使用Act，即加入拒宅信息。用户可以自定义，但是不会立即对其他用户生效，通过验证后，会被其他人使用
	 * 
	 * @param uid
	 * @param actId
	 */
	void useAct(long uid, long actId);

	/**
	 * 根据行为名称来添加
	 * 
	 * @param uid
	 * @param actName
	 */
	void useAct(long uid, String actName);

	/**
	 * 删除Act
	 * 
	 * @param uid
	 * @param actId
	 */
	void removeAct(long uid, long actId);
}

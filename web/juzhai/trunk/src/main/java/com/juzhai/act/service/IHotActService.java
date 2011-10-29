package com.juzhai.act.service;

public interface IHotActService {

	/**
	 * 推荐兴趣
	 * 
	 * @param actId
	 */
	void activeHotAct(long actId);

	/**
	 * 取消推荐兴趣
	 * 
	 * @param actId
	 */
	void cancelHotAct(long actId);
}

package com.juzhai.home.service;

import java.util.List;

import com.juzhai.passport.exception.InputBlacklistException;

public interface IBlacklistService {
	/**
	 * 屏蔽某人
	 * 
	 * @param createUid
	 * @param shieldUid
	 */
	void shield(long createUid, long shieldUid) throws InputBlacklistException;

	/**
	 * 取消某人的
	 * 
	 * @param createUid
	 * @param shieldUid
	 */
	void cancel(long createUid, long shieldUid) throws InputBlacklistException;

	/**
	 * 获取黑名单列表
	 * 
	 * @param createUid
	 * @return
	 */
	List<Long> blacklist(long createUid, int firstResult, int maxResults);

	/**
	 * 统计屏蔽总人数
	 * 
	 * @param createUid
	 * @return
	 */
	int countBlacklist(long createUid);

	/**
	 * 判断某人是否被拉黑
	 * 
	 * @param createUid
	 * @param shieldUid
	 * @return
	 */
	boolean isShield(long createUid, long shieldUid);

}

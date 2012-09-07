package com.juzhai.index.service;

import java.util.List;

public interface IHighQualityService {

	/**
	 * 添加进优质
	 * 
	 * @param uid
	 */
	void addHighQuality(long uid);

	/**
	 * 移除优质用户
	 * 
	 * @param uid
	 */
	void removeHighQuality(long uid);

	/**
	 * 优质用户列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Long> highQualityUsers(int firstResult, int maxResults);

	/**
	 * 优质用户数量
	 * 
	 * @return
	 */
	int countHighQualityUsers();

	/**
	 * 是否在优质用户内
	 * 
	 * @param uid
	 * @return
	 */
	boolean isHighQuality(long uid);
}

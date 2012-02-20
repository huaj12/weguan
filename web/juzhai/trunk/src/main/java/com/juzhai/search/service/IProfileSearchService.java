package com.juzhai.search.service;

public interface IProfileSearchService {
	/**
	 * 建索引
	 * 
	 * @param act
	 */
	void createIndex(long uid);

	/**
	 * 更新索引
	 * 
	 * @param act
	 */
	void updateIndex(long uid);

	/**
	 * 删除索引
	 * 
	 * @param postId
	 */
	void deleteIndex(long uid);
}

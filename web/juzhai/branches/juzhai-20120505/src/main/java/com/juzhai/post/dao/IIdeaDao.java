package com.juzhai.post.dao;

public interface IIdeaDao {

	/**
	 * 增加或减少使用数
	 * 
	 * @param ideaId
	 * @param p
	 */
	void incrOrDecrUseCount(long ideaId, int p);

	/**
	 * 添加第一使用者
	 * 
	 * @param ideaId
	 * @param uid
	 */
	void addFirstUser(long ideaId, long uid);
}

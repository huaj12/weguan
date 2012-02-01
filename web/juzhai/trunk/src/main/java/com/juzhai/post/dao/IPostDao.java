package com.juzhai.post.dao;

public interface IPostDao {

	/**
	 * 增加或减少响应数
	 * 
	 * @param postId
	 * @param p
	 */
	void incrOrDecrResponseCnt(long postId, int p);
}

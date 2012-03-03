package com.juzhai.post.dao;

public interface IPostDao {

	/**
	 * 增加或减少响应数
	 * 
	 * @param postId
	 * @param p
	 */
	void incrOrDecrResponseCnt(long postId, int p);

	/**
	 * 增加或减少留言数
	 * 
	 * @param postId
	 * @param p
	 */
	void incrOrDecrCommentCnt(long postId, int p);

	/**
	 * 计算某一个用户获得响应数
	 * 
	 * @param uid
	 * @return 数量
	 */
	int sumResponseCntByCreateUid(long uid);
}

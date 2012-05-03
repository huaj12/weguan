package com.juzhai.search.service;

import com.juzhai.post.model.Post;
import com.juzhai.search.bean.LuceneResult;

public interface IPostSearchService {
	/**
	 * 搜索项目
	 * 
	 * @param queryString
	 *            （模糊搜索内容）
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	LuceneResult<Post> searchPosts(String queryString, Integer gender,
			long city, long uid, int firstResult, int maxResults);

	/**
	 * 建索引
	 * 
	 * @param act
	 */
	void createIndex(long postId);

	/**
	 * 更新索引
	 * 
	 * @param act
	 */
	void updateIndex(long postId);

	/**
	 * 删除索引
	 * 
	 * @param postId
	 */
	void deleteIndex(long postId);
}

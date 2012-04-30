package com.juzhai.search.service;

import java.util.List;

import com.juzhai.post.model.Post;

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
	List<Post> searchPosts(String queryString, Integer gender, int firstResult,
			int maxResults);

	/**
	 * 获取搜索出项目的总数
	 * 
	 * @param queryString
	 * @param gender
	 * @return
	 */
	int countSearchPosts(String queryString, Integer gender);

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

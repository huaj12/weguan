package com.juzhai.search.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.juzhai.post.model.Post;

public interface IPostSearchService {
	/**
	 * 搜索项目
	 * 
	 * @param queryString
	 *            （模糊搜索内容和地点）
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	Map<Integer, List<Post>> searchPosts(String queryString, int firstResult,
			int maxResults);

	/**
	 * 查询项目
	 * 
	 * @param content
	 * @param place
	 * @param data
	 * @param categoryId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Post> queryPosts(String content, String place, Date startDate,
			Date endDate, long categoryId, int firstResult, int maxResults);

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

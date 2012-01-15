package com.juzhai.search.service;

import java.util.List;

import com.juzhai.act.model.Act;

public interface IActSearchService {

	/**
	 * 索引查询
	 * 
	 * @param queryString
	 *            查询内容
	 * @param count
	 *            查询数量
	 * @return
	 */
	List<String> indexSearchName(String queryString, int count);

	/**
	 * 搜索项目
	 * 
	 * @param queryString
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Long> searchAct(String queryString, int firstResult, int maxResults);

	/**
	 * 建索引
	 * 
	 * @param act
	 */
	void createIndex(Act act);

	/**
	 * 更新索引
	 * 
	 * @param act
	 */
	void updateIndex(Act act);
}

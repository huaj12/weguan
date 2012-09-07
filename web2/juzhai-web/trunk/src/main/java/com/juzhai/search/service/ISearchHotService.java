package com.juzhai.search.service;

import java.util.List;

import com.juzhai.search.exception.InputSearchHotException;
import com.juzhai.search.model.SearchHot;

public interface ISearchHotService {
	/**
	 * 获取某个城市的搜索热词
	 * 
	 * @param city
	 * @return
	 */
	List<SearchHot> getSearchHotByCity(long city, int count);

	/**
	 * 获取某个城市的搜索热词
	 * 
	 * @param city
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<SearchHot> getSearchHotByCity(long city, int firstResult,
			int maxResults);

	/**
	 * 统计该城市下的热词
	 * 
	 * @param city
	 * @return
	 */
	int countSearchHotByCity(long city);

	/**
	 * 添加搜索热词
	 * 
	 * @param name
	 * @param city
	 */
	void add(String name, long city) throws InputSearchHotException;

	/**
	 * 删除搜索热词
	 * 
	 * @param id
	 */
	void delete(long id);

	/**
	 * 该热词在某城市下是否存在
	 * 
	 * @param name
	 * @param city
	 * @return
	 */
	boolean isExist(String name, long city);

	/**
	 * 更新搜索热词热度
	 */
	void updateWordHot();
}

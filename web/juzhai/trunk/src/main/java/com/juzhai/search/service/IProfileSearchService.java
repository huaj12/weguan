package com.juzhai.search.service;

import com.juzhai.search.bean.LuceneResult;
import com.juzhai.search.controller.form.SearchProfileForm;
import com.juzhai.search.controller.view.LuceneUserView;

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

	/**
	 * 搜索profile
	 * 
	 * @param city
	 * @param town
	 * @param gender
	 * @param minYear
	 * @param maxYear
	 * @param educations
	 * @param minMonthlyIncome
	 * @param maxMonthlyIncome
	 * @param isMoreIncome
	 * @param home
	 * @param constellationId
	 * @param house
	 * @param car
	 * @param minHeight
	 * @param maxHeight
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	LuceneResult<LuceneUserView> queryProfile(long uid, SearchProfileForm form,
			int firstResult, int maxResults);

}

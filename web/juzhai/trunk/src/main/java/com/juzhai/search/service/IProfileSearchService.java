package com.juzhai.search.service;

import java.util.List;

import com.juzhai.passport.model.Profile;

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
	List<Profile> queryProfile(long city, long town, Integer gender,
			int minYear, int maxYear, List<String> educations,
			int minMonthlyIncome, int maxMonthlyIncome, boolean isMoreIncome,
			String home, List<Long> constellationId, String house, String car,
			int minHeight, int maxHeight, int firstResult, int maxResults);

	/**
	 * 查询搜索profile总数
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
	 * @return
	 */
	int countQqueryProfile(long city, long town, Integer gender, int minYear,
			int maxYear, List<String> educations, int minMonthlyIncome,
			int maxMonthlyIncome, boolean isMoreIncome, String home,
			List<Long> constellationId, String house, String car,
			int minHeight, int maxHeight);
}

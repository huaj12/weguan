package com.juzhai.act.service;

import java.util.List;
import java.util.Map;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActCategory;

public interface IActCategoryService {

	/**
	 * 列出推荐的分类
	 * 
	 * @return
	 */
	List<ActCategory> listHotCategories();

	/**
	 * 列出推荐的分类
	 * 
	 * @return
	 */
	List<ActCategory> listHotCategories(int size);

	/**
	 * 列出推荐的Act
	 * 
	 * @param tpId
	 * @param actCategoryList
	 * @return
	 */
	Map<Long, List<Act>> getHotActListMap(long tpId,
			List<ActCategory> actCategoryList);

	/**
	 * 根据第三方ID和分类ID获取推荐Act
	 * 
	 * @param tpId
	 * @param categoryId
	 * @return
	 */
	List<Act> getHotActList(long tpId, long categoryId);
}

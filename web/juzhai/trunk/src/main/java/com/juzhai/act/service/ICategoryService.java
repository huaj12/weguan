package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;

public interface ICategoryService {

	/**
	 * 列出推荐的分类
	 * 
	 * @return
	 */
	List<Category> listCategories(int size);

	// /**
	// * 列出推荐的Act
	// *
	// * @param tpId
	// * @param actCategoryList
	// * @return
	// */
	// Map<Long, List<Act>> getHotActListMap(long tpId,
	// List<ActCategory> actCategoryList);

	/**
	 * 分类ID获取推荐Act,排除屏蔽的
	 * 
	 * @param categoryId
	 * @param size
	 * @return
	 */
	List<Act> geActsByCategoryId(long categoryId, int size);

	/**
	 * 更新分类项目
	 */
	void updateCategoryActList();
}

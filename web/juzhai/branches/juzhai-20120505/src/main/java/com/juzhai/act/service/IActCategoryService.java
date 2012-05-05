package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;

public interface IActCategoryService {

	/**
	 * 添加项目进分类
	 * 
	 * @param actId
	 * @param categoryIds
	 */
	void updateActCategory(long actId, List<Long> categoryIds);

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
	
	void deleteActCategory(long actId);
	/**
	 * 判断某分类下面是否有内容
	 * @param catId
	 * @return
	 */
	boolean isExistAct(long catId);
	
	List<Category> findAllCategory();
}

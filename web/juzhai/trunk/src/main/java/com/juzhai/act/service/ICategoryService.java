package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.model.Category;
import com.juzhai.cms.controller.form.CategoryLiatFrom;

public interface ICategoryService {

	/**
	 * 从db获取所有分类
	 * 
	 * @return
	 */
	List<Category> getAllCategory();

	void updateCategory(CategoryLiatFrom listFrom);

	boolean deleteCategory(Long id);

}

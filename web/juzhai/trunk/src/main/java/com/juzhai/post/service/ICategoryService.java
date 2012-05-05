package com.juzhai.post.service;

import java.util.List;

import com.juzhai.cms.controller.form.CategoryLiatFrom;
import com.juzhai.post.model.Category;

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

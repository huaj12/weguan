package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.model.Category;
import com.juzhai.cms.controller.form.CategoryForm;
import com.juzhai.cms.controller.form.CategoryLiatFrom;

public interface ICategoryService {

	/**
	 * 列出推荐的分类
	 * 
	 * @return
	 */
	List<Category> listCategories(int size);
	
	List<Category> getAllCategory();
	
	void updateCategor(CategoryLiatFrom listFrom);
	
	boolean deleteCategor(Long id);
	
}

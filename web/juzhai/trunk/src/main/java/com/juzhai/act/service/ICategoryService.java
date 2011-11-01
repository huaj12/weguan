package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.model.Category;

public interface ICategoryService {

	/**
	 * 列出推荐的分类
	 * 
	 * @return
	 */
	List<Category> listCategories(int size);
}

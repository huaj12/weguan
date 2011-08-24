package com.juzhai.act.service;

import java.util.List;

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
}

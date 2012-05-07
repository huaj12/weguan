package com.juzhai.post.service;

import java.util.List;

import com.juzhai.post.model.Idea;

public interface IRecommendIdeaService {
	/**
	 * 获取推荐内容列表
	 * 
	 * @return
	 */
	List<Idea> listRecommendIdea();

	/**
	 * 更新推荐列表
	 */
	void updateRecommendIdea();
}

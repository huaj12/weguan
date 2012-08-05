package com.juzhai.post.service;

import java.util.List;
import java.util.Set;

import com.juzhai.post.exception.InputRecommendException;
import com.juzhai.post.model.Idea;

public interface IRecommendIdeaService {
	/**
	 * 获取推荐内容列表
	 * 
	 * @return
	 */
	List<Idea> listRecommendIdea(int count);

	/**
	 * 更新推荐列表
	 */
	void updateRecommendIdea();

	/**
	 * 更新近期每个分类下最火的idea
	 * 
	 * @return
	 */
	@Deprecated
	void updateRecentTopIdeas();

	/**
	 * 获取近期每个分类下最火的idea
	 * 
	 * @return
	 */
	@Deprecated
	List<Idea> listRecentTopIdeas();

	/**
	 * 获取首页好主意
	 * 
	 * @return
	 */
	// TODO (review) 此方法有什么作用？太危险了
	Set<Idea> listIndexIdeas();

	/**
	 * 添加首页好主意
	 * 
	 * @param ideaId
	 */
	void addIndexIdea(long ideaId) throws InputRecommendException;

	/**
	 * 获取首页idea
	 * 
	 * @return
	 */
	Idea getIndexIdea();

	/**
	 * 删除首页好主意
	 * 
	 * @param ideaId
	 */
	void removeIndexIdea(long ideaId);

}

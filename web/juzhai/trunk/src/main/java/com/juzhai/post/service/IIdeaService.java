package com.juzhai.post.service;

import java.util.List;

import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.post.model.Idea;

public interface IIdeaService {

	/**
	 * 根据Id获取Idea
	 * 
	 * @param ideaId
	 * @return
	 */
	Idea getIdeaById(long ideaId);

	/**
	 * 添加第一使用者
	 * 
	 * @param ideaId
	 * @param uid
	 */
	void addFirstUser(long ideaId, long uid);

	/**
	 * 添加使用者
	 * 
	 * @param ideaId
	 * @param uid
	 */
	void addUser(long ideaId, long uid);

	/**
	 * 移除使用者
	 * 
	 * @param ideaId
	 * @param uid
	 */
	void removeUser(long ideaId, long uid);

	/**
	 * 是否使用了idea
	 * 
	 * @param uid
	 * @param ideaId
	 * @return
	 */
	boolean isUseIdea(long uid, long ideaId);

	/**
	 * 好主意列表
	 * 
	 * @param oderType
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Idea> listIdeaByCity(Long cityId, ShowIdeaOrder oderType,
			int firstResult, int maxResults);

	/**
	 * 好主意数量
	 * 
	 * @return
	 */
	int countIdeaByCity(Long cityId);
}

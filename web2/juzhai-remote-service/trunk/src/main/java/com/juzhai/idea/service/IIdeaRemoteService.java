package com.juzhai.idea.service;

import java.util.List;

import com.juzhai.idea.bean.ShowIdeaOrder;
import com.juzhai.idea.controller.view.IdeaUserView;
import com.juzhai.post.model.Idea;

public interface IIdeaRemoteService {

	/**
	 * 根据Id获取Idea
	 * 
	 * @param ideaId
	 * @return
	 */
	Idea getIdeaById(long ideaId);

	/**
	 * 好主意列表
	 * 
	 * @param oderType
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Idea> listIdeaByCityAndCategory(Long cityId, Long categoryId,
			ShowIdeaOrder oderType, int firstResult, int maxResults);

	/**
	 * 好主意数量
	 * 
	 * @return
	 */
	int countIdeaByCityAndCategory(Long cityId, Long categoryId);

	/**
	 * 获取好主意橱窗内容
	 * 
	 * @return
	 */
	List<Idea> listIdeaWindow(long city, long categoryId, int firstResult,
			int maxResults);

	/**
	 * 橱窗内容数量
	 * 
	 * @return
	 */
	int countIdeaWindow(long city, long categoryId);

	/**
	 * 是否使用了idea
	 * 
	 * @param uid
	 * @param ideaId
	 * @return
	 */
	boolean isUseIdea(long uid, long ideaId);

	/**
	 * 使用了好主意的用户列表
	 * 
	 * @param ideaId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<IdeaUserView> listIdeaUsers(long ideaId, Long cityId, Integer gender,
			int firstResult, int maxResults);

	/**
	 * 使用了好主意的用户数量
	 * 
	 * @param ideaId
	 * @return
	 */
	int countIdeaUsers(long ideaId, Long cityId, Integer gender);

	/**
	 * 分享idea到第三方平台
	 * 
	 * @param uid
	 * @param tpId
	 * @param ideaId
	 */
	void shareIdea(long uid, long tpId, String content, long ideaId);
}

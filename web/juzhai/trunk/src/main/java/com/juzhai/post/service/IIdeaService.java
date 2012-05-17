package com.juzhai.post.service;

import java.util.List;

import com.juzhai.cms.controller.form.IdeaForm;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.post.controller.view.IdeaUserView;
import com.juzhai.post.exception.InputIdeaException;
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
	 * 移除好主意
	 * 
	 * @param ideaId
	 */
	void removeIdea(long ideaId) throws InputIdeaException;

	/**
	 * 屏蔽好主意
	 * 
	 * @param ideaId
	 */
	void defunctIdea(long ideaId);

	/**
	 * 取消屏蔽
	 * 
	 * @param ideaId
	 */
	void cancelDefunctIdea(long ideaId);

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
	List<Idea> listIdeaByCityAndCategory(Long cityId, Long categoryId,
			ShowIdeaOrder oderType, int firstResult, int maxResults);

	/**
	 * 未使用的好主意
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Idea> listUnUsedIdea(long uid, Long cityId, int firstResult,
			int maxResults);

	/**
	 * 最近未使用的好主意
	 * 
	 * @param uid
	 * @param cityId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Idea> listRecentIdeas(long uid, Long cityId,
			List<Long> excludeIdeaIds, int firstResult, int maxResults);

	/**
	 * 好主意数量
	 * 
	 * @return
	 */
	int countIdeaByCityAndCategory(Long cityId, Long categoryId);

	/**
	 * 被屏蔽好主意数量
	 * 
	 * @return
	 */
	int countDefunctIdea();

	/**
	 * 被屏蔽的好主意列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Idea> listDefunctIdea(int firstResult, int maxResults);

	/**
	 * 添加好主意
	 * 
	 * @param idea
	 * @throws InputIdeaException
	 */
	void addIdea(IdeaForm ideaForm) throws InputIdeaException,
			UploadImageException;

	/**
	 * 更新好主意
	 * 
	 * @param ideaForm
	 * @throws InputIdeaException
	 * @throws UploadImageException
	 */
	void updateIdea(IdeaForm ideaForm) throws InputIdeaException,
			UploadImageException;

	/**
	 * 使用了好主意的所有用户列表
	 * 
	 * @param ideaId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<IdeaUserView> listIdeaAllUsers(long ideaId, int firstResult,
			int maxResults);

	/**
	 * 使用了好主意的所有用户数量
	 * 
	 * @param ideaId
	 * @return
	 */
	int countIdeaAllUsers(long ideaId);

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
	 * 城市随机
	 * 
	 * @param gender
	 * @return
	 */
	Idea getRandomIdea(long cityId);

	/**
	 * 标记是否加入到随即库
	 * 
	 * @param random
	 */
	void ideaRandom(long ideaId, boolean random);

	/**
	 * 标记是否加入到欢迎页
	 * 
	 * @param random
	 */
	void ideaWindow(long ideaId, boolean window);

	/**
	 * 好主意橱窗排序
	 */
	void ideaWindowSort();

	/**
	 * 获取好主意橱窗内容
	 * 
	 * @return
	 */
	List<Idea> listIdeaWindow();

	/**
	 * 删除过期的idea
	 */
	void defunctExpireIdea();

	/**
	 * 后台好主意数量（选择某城市不包括全国）
	 * 
	 * @return
	 */
	int countCmsIdeaByCityAndCategory(Long cityId, Long categoryId);

	/**
	 * 好主意列表（选择某城市不包括全国）
	 * 
	 * @param oderType
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Idea> listCmsIdeaByCityAndCategory(Long cityId, Long categoryId,
			ShowIdeaOrder oderType, int firstResult, int maxResults);

	/**
	 * 总的好主意数
	 * 
	 * @return
	 */
	int totalCount();

	/**
	 * 好主意标题是否重复
	 * 
	 * @param content
	 * @param id
	 * @return
	 * @throws InputIdeaException
	 */
	String checkContentDuplicate(String content, Long id)
			throws InputIdeaException;
}

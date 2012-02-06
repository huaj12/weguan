package com.juzhai.post.service;

import java.util.List;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.cms.controller.form.IdeaForm;
import com.juzhai.index.bean.ShowIdeaOrder;
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

	//TODO (review) 注释
	void removeIdea(long ideaId);

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

}

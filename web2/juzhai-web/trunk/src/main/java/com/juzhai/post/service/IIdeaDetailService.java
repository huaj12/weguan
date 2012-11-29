package com.juzhai.post.service;

import com.juzhai.post.model.IdeaDetail;

public interface IIdeaDetailService {

	/**
	 * 更新项目详情
	 * 
	 * @param actId
	 * @param detail
	 */
	void updateIdeaDetail(long ideaId, String detail);

	/**
	 * 获取项目详情
	 * 
	 * @param actId
	 * @return
	 */
	IdeaDetail getIdeaDetail(long ideaId);
}

package com.juzhai.idea.service;

import com.juzhai.post.model.IdeaDetail;

public interface IIdeaDetailRemoteService {

	/**
	 * 获取项目详情
	 * 
	 * @param actId
	 * @return
	 */
	IdeaDetail getIdeaDetail(long ideaId);
}

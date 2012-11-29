package com.juzhai.post.service;

import com.juzhai.idea.service.IIdeaDetailRemoteService;

public interface IIdeaDetailService extends IIdeaDetailRemoteService {

	/**
	 * 更新项目详情
	 * 
	 * @param actId
	 * @param detail
	 */
	void updateIdeaDetail(long ideaId, String detail);

}

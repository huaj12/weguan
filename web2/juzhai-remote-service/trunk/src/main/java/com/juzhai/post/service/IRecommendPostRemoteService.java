package com.juzhai.post.service;

import java.util.List;

import com.juzhai.post.model.Post;

public interface IRecommendPostRemoteService {

	/**
	 * 获取推荐内容列表
	 * 
	 * @return
	 */
	List<Post> listRecommendPost(int count);
}

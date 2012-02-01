package com.juzhai.post.service;

import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.model.Post;

public interface IPostService {

	/**
	 * 发布一个拒宅
	 * 
	 * @param uid
	 * @param postForm
	 * @throws InputPostException
	 */
	void createPost(long uid, PostForm postForm) throws InputPostException;

	/**
	 * 移除拒宅
	 * 
	 * @param uid
	 * @param postId
	 * @throws InputPostException
	 */
	void deletePost(long uid, long postId) throws InputPostException;

	/**
	 * 是否发布过此idea
	 * 
	 * @param uid
	 * @param ideaId
	 * @return
	 */
	boolean hasPostIdea(long uid, long ideaId);

	/**
	 * 修改拒宅
	 * 
	 * @param uid
	 * @param postForm
	 * @throws InputPostException
	 */
	void modifyPost(long uid, PostForm postForm) throws InputPostException;

	/**
	 * 响应拒宅
	 * 
	 * @param uid
	 * @param postId
	 * @throws InputPostException
	 */
	void responsePost(long uid, long postId) throws InputPostException;

	/**
	 * 获取用户最新一条post
	 * 
	 * @param uid
	 * @return 如果返回null，表示没有
	 */
	Post getUserLatestPost(long uid);
}

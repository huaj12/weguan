package com.juzhai.post.service;

import java.util.List;

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

	/**
	 * 用户响应的PostId列表
	 * 
	 * @param uid
	 * @return
	 */
	List<Long> responsePostIds(long uid);

	/**
	 * 首页post列表
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	List<Post> listNewestPost(long uid, Long cityId, Integer gender,
			int firstResult, int maxResults);

	/**
	 * 首页post数量
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	int countNewestPost(long uid, Long cityId, Integer gender);

	/**
	 * 列表我响应的post
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	List<Post> listResponsePost(long uid, Long cityId, Integer gender,
			int firstResult, int maxResults);

	/**
	 * 我响应的数量
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	int countResponsePost(long uid, Long cityId, Integer gender);

	/**
	 * 我收藏的人的post
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	List<Post> listInterestUserPost(long uid, Long cityId, Integer gender,
			int firstResult, int maxResults);

	/**
	 * 收藏的人的post的数量
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	int countInterestUserPost(long uid, Long cityId, Integer gender);

	/**
	 * 是否响应了post
	 * 
	 * @param uid
	 * @param postId
	 * @return
	 */
	boolean isResponsePost(long uid, long postId);

	/**
	 * 用户post列表
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Post> listUserPost(long uid, int firstResult, int maxResults);

	/**
	 * 用户post数量
	 * 
	 * @param uid
	 * @return
	 */
	int countUserPost(long uid);
}

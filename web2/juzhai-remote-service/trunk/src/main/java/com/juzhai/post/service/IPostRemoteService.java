package com.juzhai.post.service;

import java.util.List;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.post.bean.PostResult;
import com.juzhai.post.bean.ShowPostOrder;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.model.Post;

public interface IPostRemoteService {

	/**
	 * 发布一个拒宅
	 * 
	 * @param uid
	 * @param postForm
	 * @throws InputPostException
	 * @throws UploadImageException
	 */
	long createPost(long uid, PostForm postForm) throws InputPostException,
			UploadImageException;

	/**
	 * 同步微博
	 * 
	 * @param content
	 * @param place
	 * @param date
	 * @param link
	 * @param uid
	 * @param tpId
	 */
	void synchronizePlatform(long uid, long tpId, long postId);

	/**
	 * 首页post列表
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	PostResult listNewOrOnlinePosts(Long cityId, Long townId, Integer gender,
			ShowPostOrder order, long excludeUid, int firstResult,
			int maxResults);

	/**
	 * 首页post数量
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	int countNewOrOnlinePosts(Long cityId, Long townId, Integer gender,
			long excludeUid);

	/**
	 * 响应拒宅
	 * 
	 * @param uid
	 * @param postId
	 * @throws InputPostException
	 */
	void responsePost(long uid, long postId, String content)
			throws InputPostException;

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
	List<Post> listUserPost(long uid, List<Long> excludePostIds,
			int firstResult, int maxResults);

	/**
	 * 用户post数量
	 * 
	 * @param uid
	 * @return
	 */
	int countUserPost(long uid);
}

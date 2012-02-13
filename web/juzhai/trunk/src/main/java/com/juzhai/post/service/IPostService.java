package com.juzhai.post.service;

import java.util.List;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.platform.exception.AdminException;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostResponse;

public interface IPostService {

	/**
	 * 发布一个拒宅
	 * 
	 * @param uid
	 * @param postForm
	 * @throws InputPostException
	 */
	long createPost(long uid, PostForm postForm) throws InputPostException;

	/**
	 * 移除拒宅
	 * 
	 * @param uid
	 * @param postId
	 * @throws InputPostException
	 */
	void deletePost(long uid, long postId) throws InputPostException;

	/**
	 * 修改拒宅
	 * 
	 * @param uid
	 * @param postForm
	 * @throws InputPostException
	 */
	long modifyPost(long uid, PostForm postForm) throws InputPostException;

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

	/**
	 * 后台删除拒宅
	 * 
	 * @param postId
	 * @throws InputPostException
	 */
	void deletePost(long postId) throws InputPostException;

	/**
	 * 屏蔽拒宅
	 * 
	 * @param postId
	 */
	void shieldPost(long postId) throws InputPostException;

	/**
	 * 取消屏蔽拒宅
	 * 
	 * @param postId
	 * @throws InputPostException
	 */
	void unShieldPost(long postId) throws InputPostException;

	/**
	 * 标记为已处理拒宅
	 * 
	 * @param postIds
	 */
	void handlePost(List<Long> postIds) throws InputPostException;

	/**
	 * 标记为好主意
	 * 
	 * @param postId
	 * @throws InputPostException
	 */
	void markIdea(long postId, long ideaId) throws InputPostException;

	/**
	 * 是否发布过此idea
	 * 
	 * @param uid
	 * @param ideaId
	 * @return
	 */
	boolean hasPostIdea(long uid, long ideaId);

	/**
	 * 后台未处理的拒宅列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Post> listUnhandlePost(int firstResult, int maxResults);

	/**
	 * 后台屏蔽拒宅列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Post> listShieldPost(int firstResult, int maxResults);

	/**
	 * 后台处理的拒宅列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Post> listHandlePost(int firstResult, int maxResults);

	/**
	 * 未处理拒宅数量
	 * 
	 * @return
	 */
	int countUnhandlePost();

	/**
	 * 屏蔽的拒宅数量
	 * 
	 * @return
	 */
	int countShieldPost();

	/**
	 * 合格的拒宅数量
	 * 
	 * @return
	 */
	int countHandlePost();

	/**
	 * 根据id查询post
	 * 
	 * @param postId
	 * @return
	 */
	Post getPostById(long postId);

	/**
	 * 拒宅的响应者列表
	 * 
	 * @param postId
	 * @param firestResult
	 * @param maxResults
	 * @return
	 */
	List<ProfileCache> listResponseUser(long postId, int firstResult,
			int maxResults);

	/**
	 * 拒宅的响应列表
	 * 
	 * @param postId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<PostResponse> listPostResponse(long postId, int firstResult,
			int maxResults);

	/**
	 * 拒宅的响应者数量
	 * 
	 * @param postId
	 * @return
	 */
	int countResponseUser(long postId);

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
	void synchronizeWeibo(long uid, long tpId, long postId);

}

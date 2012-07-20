package com.juzhai.post.service;

import java.util.List;
import java.util.Map;

import com.juzhai.cms.controller.view.CmsPostView;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.home.bean.ShowPostOrder;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.bean.PostResult;
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
	 * @throws UploadImageException
	 */
	long createPost(long uid, PostForm postForm) throws InputPostException,
			UploadImageException;

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
	void responsePost(long uid, long postId, String content)
			throws InputPostException;

	/**
	 * 获取用户最新一条post
	 * 
	 * @param uid
	 * @return 如果返回null，表示没有
	 */
	Long getUserLatestPost(long uid);

	/**
	 * 用户响应的PostId列表
	 * 
	 * @param uid
	 * @return
	 */
	List<Long> responsePostIds(long uid);

	/**
	 * 获取多个拒宅
	 * 
	 * @param postIdList
	 * @return
	 */
	Map<Long, Post> getMultiUserLatestPosts(List<Long> uidList);

	/**
	 * 首页post列表
	 * 
	 * @param uid
	 * @param cityId
	 * @param gender
	 * @return
	 */
	// TODO (done) 如今这个方法名字不太合适了
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
	List<Post> listUserPost(long uid, List<Long> excludePostIds,
			int firstResult, int maxResults);

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
	List<Post> listUnhandlePost(long city, Boolean isIdea, int firstResult,
			int maxResults);

	/**
	 * 后台屏蔽拒宅列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Post> listShieldPost(long city, int firstResult, int maxResults);

	/**
	 * 后台处理的拒宅列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Post> listHandlePost(long city, Integer gender, Boolean isIdea,
			int firstResult, int maxResults);

	/**
	 * 未处理拒宅数量
	 * 
	 * @return
	 */
	int countUnhandlePost(long city, Boolean isIdea);

	/**
	 * 屏蔽的拒宅数量
	 * 
	 * @return
	 */
	int countShieldPost(long city);

	/**
	 * 合格的拒宅数量
	 * 
	 * @return
	 */
	int countHandlePost(long city, Integer gender, Boolean isIdea);

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

	/**
	 * 获取总的响应数，半小时缓存
	 * 
	 * @param uid
	 * @return
	 */
	int getAllResponseCnt(long uid);

	/**
	 * 拒宅总数
	 * 
	 * @return
	 */
	int totalCount();

	/**
	 * 响应总数
	 * 
	 * @return
	 */
	int responseTotalCount();

	/**
	 * 获取拒宅
	 * 
	 * 
	 * @param type
	 *            查询类型
	 * @param id
	 * @return
	 */
	List<CmsPostView> getpost(int type, long id);

	/**
	 * 获取某用户已通过拒宅
	 * 
	 * @param uid
	 * @return
	 */
	List<Post> getUserQualifiedPost(long uid, int firstResult, int maxResults);

	/**
	 * 找出待审核状态的拒宅且是通过好主意发送的
	 * 
	 * @param uid
	 * @return
	 */
	// TODO (done) 注释和方法名都没体现出实现的功能
	List<Post> listRawPostIdea(long uid);

	/**
	 * 是否打开等待用户弹出框
	 * 
	 * @param uid
	 * @return
	 */
	boolean isOpenWaitRescueUserDialog(long uid);

	/**
	 * 获取热门拒宅
	 * 
	 * @param city
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Post> listUserHotPost(Long city, int firstResult, int maxResults);

}

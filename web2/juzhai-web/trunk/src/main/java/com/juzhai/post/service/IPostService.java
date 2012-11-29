package com.juzhai.post.service;

import java.util.List;
import java.util.Map;

import com.juzhai.cms.controller.view.CmsPostView;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostResponse;

public interface IPostService extends IPostRemoteService {

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
	void handlePost(Long postId) throws InputPostException;

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

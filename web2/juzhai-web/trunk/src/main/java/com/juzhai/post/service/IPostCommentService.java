package com.juzhai.post.service;

import java.util.List;

import com.juzhai.core.web.session.UserContext;
import com.juzhai.post.controller.form.PostCommentForm;
import com.juzhai.post.exception.InputPostCommentException;
import com.juzhai.post.model.PostComment;

public interface IPostCommentService {

	/**
	 * 评论，留言
	 * 
	 * @param UserContext
	 * @param form
	 * @return
	 * @throws InputPostCommentException
	 */
	PostComment comment(UserContext context, PostCommentForm form)
			throws InputPostCommentException;

	/**
	 * 评论，留言
	 * 
	 * @param uid
	 * @param form
	 * @return
	 * @throws InputPostCommentException
	 */
	PostComment comment(long uid, PostCommentForm form)
			throws InputPostCommentException;

	/**
	 * 评论设置屏蔽（post删除）
	 * 
	 * @param postId
	 */
	void defunctComment(long postId);

	/**
	 * 删除留言
	 * 
	 * @param postCommentId
	 * @throws InputPostCommentException
	 */
	void deleteComment(long uid, long postCommentId)
			throws InputPostCommentException;

	/**
	 * 评论收件箱列表
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<PostComment> listInbox(long uid, int firstResult, int maxResults);

	/**
	 * 评论收件箱里的数量
	 * 
	 * @param uid
	 * @return
	 */
	int countInbox(long uid);

	/**
	 * 评论发件箱列表
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<PostComment> listOutbox(long uid, int firstResult, int maxResults);

	/**
	 * 评论发件箱里的数量
	 * 
	 * @param uid
	 * @return
	 */
	int countOutbox(long uid);

	/**
	 * 拒宅的评论列表
	 * 
	 * @param postId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<PostComment> listPostComment(long postId, int firstResult,
			int maxResults);

	/**
	 * 拒宅的评论数量
	 * 
	 * @param postId
	 * @return
	 */
	int countPostComment(long postId);

	/**
	 * 留言总数
	 * 
	 * @return
	 */
	int totalCount();
}

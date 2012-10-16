package com.juzhai.android.post.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.juzhai.android.core.model.Result.PostListResult;
import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.home.bean.ZhaobanOrder;
import com.juzhai.android.post.exception.PostException;
import com.juzhai.android.post.model.Post;

public interface IUserPostService {
	/**
	 * 找伴列表
	 * 
	 * @param gender
	 * @param orderType
	 * @param page
	 * @return
	 */
	UserListResult list(Context context, Integer gender, ZhaobanOrder order,
			int page) throws PostException;

	/**
	 * 找出某用户所有的拒宅
	 * 
	 * @param uid
	 * @param page
	 * @return
	 * @throws PostException
	 */
	PostListResult listPosts(Context context, long uid, int page)
			throws PostException;

	/**
	 * 发送拒宅
	 * 
	 * @param image
	 * @param post
	 * @param context
	 * @throws PostException
	 */
	void sendPost(Bitmap image, Post post, Context context)
			throws PostException;
}

package com.juzhai.android.post.service;

import com.juzhai.android.core.model.Result.PostListResult;
import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.home.bean.ZhaobanOrder;
import com.juzhai.android.post.exception.PostException;

public interface IUserPostService {
	/**
	 * 找伴列表
	 * 
	 * @param gender
	 * @param orderType
	 * @param page
	 * @return
	 */
	UserListResult list(Integer gender, ZhaobanOrder order, int page)
			throws PostException;

	/**
	 * 找出某用户所有的拒宅
	 * 
	 * @param uid
	 * @param page
	 * @return
	 * @throws PostException
	 */
	PostListResult listPosts(long uid, int page) throws PostException;
}

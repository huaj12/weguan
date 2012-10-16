package com.juzhai.android.post.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.PostListResult;
import com.juzhai.android.core.model.Result.StringResult;
import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.home.bean.ZhaobanOrder;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.post.exception.PostException;
import com.juzhai.android.post.model.Post;
import com.juzhai.android.post.service.IUserPostService;

public class UserPostService implements IUserPostService {
	private String userPostUri = "post/showposts";
	private String postsUri = "home";
	private String sendPostUri = "post/sendPost";

	@Override
	public UserListResult list(Context context, Integer gender,
			ZhaobanOrder order, int page) throws PostException {
		Map<String, Object> values = new HashMap<String, Object>();
		if (gender != null) {
			values.put("gender", gender.intValue());
		}
		values.put("orderType", order.getName());
		values.put("page", page);
		String uri = HttpUtils.createHttpParam(userPostUri, values);
		ResponseEntity<UserListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, uri, UserListResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"post get UserListResult is  error", e);
			}
			throw new PostException(context, R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();
	}

	@Override
	public PostListResult listPosts(Context context, long uid, int page)
			throws PostException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("uid", uid);
		values.put("page", page);
		String uri = HttpUtils.createHttpParam(postsUri, values);
		ResponseEntity<PostListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, uri, PostListResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"post get UserPostListResult is  error", e);
			}
			throw new PostException(context, R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();
	}

	@Override
	public void sendPost(Bitmap image, Post post, Context context)
			throws PostException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("content", post.getContent());
		values.put("categoryId", post.getCategoryId());
		if (StringUtils.isNotEmpty(post.getPlace())) {
			values.put("place", post.getPlace());
		}
		if (StringUtils.isNotEmpty(post.getDate())) {
			values.put("dateString", post.getDate());
		}
		ResponseEntity<StringResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.uploadFile(context, sendPostUri, values,
					UserCache.getUserStatus(), "postImg", image,
					StringResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(), "post  sendPost is  error", e);
			}
			throw new PostException(context, R.string.system_internet_erorr, e);
		}
		StringResult result = responseEntity.getBody();
		if (!result.getSuccess()) {
			throw new PostException(context, result.getErrorInfo());
		}
	}
}

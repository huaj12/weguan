package com.juzhai.android.post.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.PostListResult;
import com.juzhai.android.core.model.Result.StringResult;
import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.home.bean.ZhaobanOrder;
import com.juzhai.android.passport.exception.NeedLoginException;
import com.juzhai.android.post.exception.PostException;
import com.juzhai.android.post.model.Post;
import com.juzhai.android.post.service.IUserPostService;
import com.umeng.analytics.MobclickAgent;

public class UserPostService implements IUserPostService {
	private String userPostUri = "post/userPosts";
	private String postsUri = "home";
	private String sendPostUri = "post/sendPost";
	private String respUsersUri = "post/respUsers";

	@Override
	public UserListResult list(Context context, Integer gender,
			ZhaobanOrder order, int page) throws PostException {
		Map<String, Object> values = new HashMap<String, Object>();
		if (gender != null) {
			values.put("gender", gender.intValue());
		}
		values.put("orderType", order.getName());
		values.put("page", page);
		ResponseEntity<UserListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, userPostUri, values,
					UserListResult.class);
		} catch (NeedLoginException e) {
			UserListResult userListResult = new UserListResult();
			userListResult.setSuccess(false);
			userListResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return userListResult;
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
		ResponseEntity<PostListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, postsUri, values,
					PostListResult.class);
		} catch (NeedLoginException e) {
			PostListResult postListResult = new PostListResult();
			postListResult.setSuccess(false);
			postListResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return postListResult;
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
	public void sendPost(Context context, Post post, Bitmap image)
			throws PostException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("content", post.getContent());
		values.put("categoryId", post.getCategoryId());
		if (StringUtils.hasText(post.getPlace())) {
			values.put("place", post.getPlace());
		}
		if (StringUtils.hasText(post.getDate())) {
			values.put("dateString", post.getDate());
		}
		ResponseEntity<StringResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.uploadFile(context, sendPostUri, values,
					null, "postImg", image, StringResult.class);
		} catch (NeedLoginException e) {
			throw new PostException(context, R.string.login_status_error, e);
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
		MobclickAgent.onEvent(context, UmengEvent.SEND_POST);
	}

	@Override
	public UserListResult respUsers(Context context, long postId, int page)
			throws PostException {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("postId", postId);
		values.put("page", page);
		ResponseEntity<UserListResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.get(context, respUsersUri, values,
					UserListResult.class);
		} catch (NeedLoginException e) {
			UserListResult userListResult = new UserListResult();
			userListResult.setSuccess(false);
			userListResult.setErrorInfo(context
					.getString(R.string.login_status_error));
			return userListResult;
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d(getClass().getSimpleName(),
						"respUsers get UserListResult is  error", e);
			}
			throw new PostException(context, R.string.system_internet_erorr, e);
		}
		return responseEntity.getBody();
	}
}

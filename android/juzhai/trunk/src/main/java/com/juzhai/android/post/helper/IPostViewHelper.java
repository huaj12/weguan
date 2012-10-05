package com.juzhai.android.post.helper;

import android.content.Context;
import android.widget.ImageView;

import com.juzhai.android.passport.model.Post;

public interface IPostViewHelper {
	/**
	 * 显示拒宅小图片
	 * 
	 * @param context
	 * @param post
	 * @param imageView
	 */
	void showSmallPostImage(Context context, Post post, ImageView imageView);

	/**
	 * 显示拒宅大图片
	 * 
	 * @param context
	 * @param post
	 * @param imageView
	 */
	void showBigPostImage(Context context, Post post, ImageView imageView);
}

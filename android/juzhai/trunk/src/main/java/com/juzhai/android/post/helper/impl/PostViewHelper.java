package com.juzhai.android.post.helper.impl;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.passport.model.Post;
import com.juzhai.android.post.helper.IPostViewHelper;

public class PostViewHelper implements IPostViewHelper {

	@Override
	public void showSmallPostImage(final Context context, Post post,
			final ImageView imageView) {
		if (StringUtils.isNotEmpty(post.getPic())) {
			imageView.setVisibility(View.VISIBLE);
			ImageViewLoader nid = ImageViewLoader.getInstance(context);
			nid.fetchImage(JzUtils.getImageUrl(post.getPic()),
					R.drawable.load_pic_s, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(context, 110),
										UIUtil.dip2px(context, 80));
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		} else {
			imageView.setVisibility(View.GONE);
		}

	}

	@Override
	public void showBigPostImage(final Context context, Post post,
			final ImageView imageView) {
		if (StringUtils.isNotEmpty(post.getBigPic())) {
			imageView.setVisibility(View.VISIBLE);
			ImageViewLoader nid = ImageViewLoader.getInstance(context);
			nid.fetchImage(JzUtils.getImageUrl(post.getBigPic()),
					R.drawable.load_pic_b, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(context, 230),
										UIUtil.dip2px(context, 150));
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		} else {
			imageView.setVisibility(View.GONE);
		}

	}

}

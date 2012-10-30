package com.juzhai.android.post.helper.impl;

import org.springframework.util.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.post.helper.IPostViewHelper;
import com.juzhai.android.post.model.Post;

public class PostViewHelper implements IPostViewHelper {

	@Override
	public void showSmallPostImage(final Context context, Post post,
			final ImageView imageView) {
		if (StringUtils.hasText(post.getPic())) {
			imageView.setVisibility(View.VISIBLE);
			ImageViewLoader nid = ImageViewLoader.getInstance(context);
			nid.fetchImage(JzUtils.getImageUrl(post.getPic()),
					R.drawable.load_pic_s, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, 110, 80, context);
								LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView
										.getLayoutParams();
								params.width = UIUtil.dip2px(context, 110);
								params.height = zoomBitmap.getHeight();
								imageView.setLayoutParams(params);
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 5,
												context));
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
		if (StringUtils.hasText(post.getBigPic())) {
			imageView.setVisibility(View.VISIBLE);
			ImageViewLoader nid = ImageViewLoader.getInstance(context);
			nid.fetchImage(JzUtils.getImageUrl(post.getBigPic()),
					R.drawable.load_pic_b, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils
										.ZoomBitmapNotCut(bitmap, 230, 150,
												context);
								LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView
										.getLayoutParams();
								params.width = UIUtil.dip2px(context, 230);
								params.height = zoomBitmap.getHeight();
								imageView.setLayoutParams(params);
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 5,
												context));
							}
						}
					});
		} else {
			imageView.setVisibility(View.GONE);
		}

	}

}

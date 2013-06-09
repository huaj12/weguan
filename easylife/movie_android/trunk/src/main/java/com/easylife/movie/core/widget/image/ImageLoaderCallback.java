package com.easylife.movie.core.widget.image;

import android.graphics.Bitmap;

public interface ImageLoaderCallback {

	/**
	 * 下载图片结束回调
	 * 
	 * @param bitmap
	 *            下载下来的位图
	 */
	void imageLoaderFinish(Bitmap bitmap);
}

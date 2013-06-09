package com.easylife.movie.common.service;

import android.content.Context;

import com.easylife.movie.video.model.Video;

public interface IShareService {

	/**
	 * 打开分享框
	 * 
	 * @param context
	 */
	void openSharePop(String text, Context context, Video video);

}

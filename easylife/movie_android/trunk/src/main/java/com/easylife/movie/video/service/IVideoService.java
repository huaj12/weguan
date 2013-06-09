package com.easylife.movie.video.service;

import android.content.Context;

import com.easylife.movie.core.model.Result.VideoListResult;
import com.easylife.movie.video.model.Video;

public interface IVideoService {
	/**
	 * 获取movie列表
	 * 
	 * @param context
	 * @param categoryId
	 * @param page
	 * @return
	 */
	VideoListResult getVideoListResult(Context context, long categoryId,
			int page);

	/**
	 * 添加到历史记录里
	 * 
	 * @param context
	 * @param video
	 */
	void addHistory(Context context, Video video);

	/**
	 * 添加到收藏列表里
	 * 
	 * @param context
	 * @param video
	 */
	void addInterest(Context context, Video video);

	/**
	 * 是否感兴趣
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	boolean hasInterest(Context context, String id);

	/**
	 * 是否观看过
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	boolean hasHistory(Context context, String id);

	/**
	 * 取消收藏
	 * 
	 * @param context
	 * @param id
	 */
	void removeInterest(Context context, String id);

	/**
	 * 获取用户感兴趣的列表
	 * 
	 * @param context
	 * @param page
	 * @return
	 */
	VideoListResult getVideoInterestListResult(Context context, int page);

	/**
	 * 获取用户历史记录列表
	 * 
	 * @param context
	 * @param page
	 * @return
	 */
	VideoListResult getVideoHistoryListResult(Context context, int page);

	/**
	 * 获取历史列表数目
	 */
	int getVideoHistoryCount(Context context);

}

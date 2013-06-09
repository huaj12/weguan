package com.easylife.movie.setting.task;

import android.content.Context;

import com.easylife.movie.core.model.Result.VideoListResult;
import com.easylife.movie.core.widget.list.GetDataTask;
import com.easylife.movie.core.widget.list.MovieRefreshListView;
import com.easylife.movie.video.model.Video;
import com.easylife.movie.video.service.IVideoService;
import com.easylife.movie.video.service.impl.VideoService;

public class InterestVideoListGetDataTask extends
		GetDataTask<VideoListResult, Video> {

	public InterestVideoListGetDataTask(Context context,
			MovieRefreshListView refreshListView) {
		super(context, refreshListView);
	}

	@Override
	protected VideoListResult doInBackground(Object... params) {
		VideoListResult result = null;
		try {
			int page = (Integer) params[0];
			IVideoService videoService = new VideoService();
			result = videoService.getVideoInterestListResult(context, page);
		} catch (Exception e) {

		}
		return result;
	}
}
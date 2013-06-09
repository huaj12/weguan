package com.easylife.movie.main.task;

import android.content.Context;

import com.easylife.movie.core.model.Result.VideoListResult;
import com.easylife.movie.core.widget.list.GetDataTask;
import com.easylife.movie.core.widget.list.MovieRefreshListView;
import com.easylife.movie.video.model.Video;
import com.easylife.movie.video.service.IVideoService;
import com.easylife.movie.video.service.impl.VideoService;

public class VideoListGetDataTask extends GetDataTask<VideoListResult, Video> {

	public VideoListGetDataTask(Context context,
			MovieRefreshListView refreshListView) {
		super(context, refreshListView);
	}

	@Override
	protected VideoListResult doInBackground(Object... params) {
		VideoListResult result = null;
		try {
			long categoryId = (Long) params[0];
			int page = (Integer) params[1];
			IVideoService videoService = new VideoService();
			result = videoService.getVideoListResult(context, categoryId, page);
		} catch (Exception e) {

		}
		return result;
	}
}

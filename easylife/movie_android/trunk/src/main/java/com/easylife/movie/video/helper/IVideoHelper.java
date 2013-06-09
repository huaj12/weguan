package com.easylife.movie.video.helper;

import android.content.Context;

import com.easylife.movie.core.widget.list.MovieRefreshListView;

public interface IVideoHelper {
	void showMovieRefreshListView(MovieRefreshListView listView,
			long categoryId, Context context);

}

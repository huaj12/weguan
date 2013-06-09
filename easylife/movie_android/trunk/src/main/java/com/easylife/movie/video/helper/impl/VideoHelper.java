package com.easylife.movie.video.helper.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.easylife.movie.core.widget.list.MovieRefreshListView;
import com.easylife.movie.core.widget.list.pullrefresh.PullToRefreshBase;
import com.easylife.movie.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.easylife.movie.main.task.VideoListGetDataTask;
import com.easylife.movie.video.activity.VideoActivity;
import com.easylife.movie.video.adapter.VideoListAdapter;
import com.easylife.movie.video.helper.IVideoHelper;
import com.easylife.movie.video.model.Video;

public class VideoHelper implements IVideoHelper {

	@Override
	public void showMovieRefreshListView(final MovieRefreshListView listView,
			final long categoryId, final Context context) {

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new VideoListGetDataTask(context, listView).execute(categoryId,
						1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new VideoListGetDataTask(context, listView)
						.execute(categoryId, listView.getPageAdapter()
								.getPager().getCurrentPage() + 1);
			}
		});
		listView.setAdapter(new VideoListAdapter(context));
		listView.manualRefresh();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int position = (int) arg3;
				Video video = (Video) listView.getPageAdapter().getItem(
						position);
				Intent intent = new Intent(context, VideoActivity.class);
				intent.putExtra("video", video);
				((Activity) context).startActivity(intent);
			}
		});
	}

}

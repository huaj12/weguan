package com.easylife.movie.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.easylife.movie.R;
import com.easylife.movie.core.activity.BaseActivity;
import com.easylife.movie.core.widget.list.MovieRefreshListView;
import com.easylife.movie.core.widget.list.pullrefresh.PullToRefreshBase;
import com.easylife.movie.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.easylife.movie.setting.task.InterestVideoListGetDataTask;
import com.easylife.movie.video.activity.VideoActivity;
import com.easylife.movie.video.adapter.VideoListAdapter;
import com.easylife.movie.video.model.Video;

public class InterestActivity extends BaseActivity {
	private MovieRefreshListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_interest);
		listView = (MovieRefreshListView) findViewById(R.id.movie_list_view);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new InterestVideoListGetDataTask(InterestActivity.this,
						listView).execute(1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new InterestVideoListGetDataTask(InterestActivity.this,
						listView).execute(listView.getPageAdapter().getPager()
						.getCurrentPage() + 1);
			}
		});
		listView.setAdapter(new VideoListAdapter(InterestActivity.this));
		listView.manualRefresh();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int position = (int) arg3;
				Video video = (Video) listView.getPageAdapter().getItem(
						position);
				Intent intent = new Intent(InterestActivity.this,
						VideoActivity.class);
				intent.putExtra("video", video);
				startActivity(intent);
			}
		});
		Button backBtn = (Button) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}
}
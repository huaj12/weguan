package com.juzhai.android.home.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.button.SegmentedButton;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.post.activity.PostDetailActivity;

public class ZhaobanActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 内容视图
		setNavContentView(R.layout.user_post_list);
		// 导航左边按钮
		Button sendJzButton = (Button) getLayoutInflater().inflate(
				R.layout.send_jz_button, null);
		sendJzButton.setOnClickListener(null);
		getNavigationBar().setLeftView(sendJzButton);
		// 导航右边按钮
		SegmentedButton segmentedButton = new SegmentedButton(this,
				new String[] { "最新发布", "活跃小宅" }, 80, 32);
		segmentedButton
				.setOnClickListener(new SegmentedButton.OnClickListener() {
					@Override
					public void onClick(Button button, int which) {
						Log.d("juzhai", "button : " + which);
					}
				});
		getNavigationBar().setBarTitleView(segmentedButton);

		ListView listView = (ListView) findViewById(R.id.user_post_list);

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 20; i++) {
			Map<String, String> item = new HashMap<String, String>();
			item.put("userName", "name" + i);
			item.put("postContent", "content" + i);
			data.add(item);
		}

		SimpleAdapter userPostListAdapter = new SimpleAdapter(this, data,
				R.layout.user_post_list_item, new String[] { "userName",
						"postContent" }, new int[] { R.id.user_name,
						R.id.post_content });
		// 添加并且显示
		listView.setAdapter(userPostListAdapter);
		final Context context = this;

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, PostDetailActivity.class);
				pushIntent(intent);
			}
		});
	}
}

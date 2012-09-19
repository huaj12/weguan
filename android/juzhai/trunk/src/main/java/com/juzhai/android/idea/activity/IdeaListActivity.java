package com.juzhai.android.idea.activity;

import org.springframework.http.ResponseEntity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.widget.button.SegmentedButton;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.adapter.IdeaListAdapter;
import com.juzhai.android.idea.model.IdeaListAndPager;
import com.juzhai.android.idea.model.IdeaResult;
import com.juzhai.android.passport.data.UserCache;

public class IdeaListActivity extends NavigationActivity {
	private static final String[] categorys = { "拒宅灵感", "休闲娱乐", "结伴游玩", "聚会活动",
			"看场电影", "好吃好喝", "演出展览" };
	private String uri = "idea/list";
	private ProgressBar bar;
	private String categoryId = "0";
	private String orderType = "pop";
	private ListView ideaListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 内容视图
		setNavContentView(R.layout.idea_list);
		// 导航左边按钮
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categorys);
		Spinner spinner = new Spinner(IdeaListActivity.this);
		spinner.setLayoutParams(new LayoutParams(100, 32));
		spinner.setBackgroundResource(R.drawable.category_selector_button);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		getNavigationBar().setLeftView(spinner);
		// 导航右边按钮
		SegmentedButton segmentedButton = new SegmentedButton(this,
				new String[] { "最新", "最热" }, 40, 32);
		segmentedButton
				.setOnClickListener(new SegmentedButton.OnClickListener() {
					@Override
					public void onClick(Button button, int which) {
						Log.d("juzhai", "button : " + which);
					}
				});
		getNavigationBar().setRightView(segmentedButton);

		bar = (ProgressBar) findViewById(R.id.pro_bar);
		ideaListView = (ListView) findViewById(R.id.idea_listview);
		new AsyncTask<String, Integer, IdeaListAndPager>() {

			@Override
			protected IdeaListAndPager doInBackground(String... params) {
				String url = params[0] + "?categoryId=" + params[1]
						+ "&orderType=" + params[2] + "&page=" + params[3];
				ResponseEntity<IdeaResult> responseEntity = null;
				try {
					responseEntity = HttpUtils.get(url,
							UserCache.getUserStatus(), IdeaResult.class);
				} catch (Exception e) {
					Log.e("error", e.getMessage());
				}
				return responseEntity.getBody().getResult();
			}

			@Override
			protected void onPostExecute(IdeaListAndPager result) {
				bar.setProgress(100);
				bar.setVisibility(View.GONE);
				ideaListView.setVisibility(View.VISIBLE);
				ideaListView.setAdapter(new IdeaListAdapter(result,
						IdeaListActivity.this, LAYOUT_INFLATER_SERVICE));
			}

			@Override
			protected void onPreExecute() {
				bar.setProgress(0);
			}

		}.execute(uri, categoryId, orderType, "1");

		// getNavigationBar().setBarTitle("出去玩");
		// setNavContentView(R.layout.idea_list);
		//
		// final ImageView imageView = (ImageView) findViewById(R.id.test_img);
		//
		// ImageViewLoader nid = ImageViewLoader.getInstance(this);
		// nid.fetchImage(
		// "http://static.51juzhai.com/upload/idea/0/0/1001/450/2ef86e9e-769e-448f-8a57-cd11694ed98c.jpg",
		// 0, imageView, new ImageLoaderCallback() {
		// @Override
		// public void imageLoaderFinish(Bitmap bitmap) {
		// imageView.setImageBitmap(bitmap);
		// }
		// });

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView) view;
				tv.setTextColor(getResources().getColor(R.color.white)); // 设置颜色
				tv.setTextSize(15.0f); // 设置大小
				tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL); // 设置居中
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
}

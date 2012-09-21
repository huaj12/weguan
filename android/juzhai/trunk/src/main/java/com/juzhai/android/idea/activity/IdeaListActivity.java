package com.juzhai.android.idea.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.common.model.Category;
import com.juzhai.android.common.service.CommonData;
import com.juzhai.android.core.widget.button.SegmentedButton;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.core.widget.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.idea.adapter.IdeaListAdapter;
import com.juzhai.android.idea.task.IdeaListGetDataTask;

public class IdeaListActivity extends NavigationActivity {
	private int categoryId = 0;
	private String orderType = "time";
	private List<Category> categorys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 内容视图
		setNavContentView(R.layout.page_idea_list);
		final JuzhaiRefreshListView ideaListView = (JuzhaiRefreshListView) findViewById(R.id.idea_list_view);
		// 分类
		categorys = CommonData.getCategorys(IdeaListActivity.this);
		String[] categoryNames = new String[categorys.size()];
		for (int i = 0; i < categorys.size(); i++) {
			categoryNames[i] = categorys.get(i).getName();
		}
		// 导航左边按钮
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categoryNames) {
			@Override
			public long getItemId(int position) {
				return categorys.get(position).getCategoryId();
			}

		};
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner) getLayoutInflater().inflate(
				R.layout.button_category, null);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView) view;
				tv.setTextColor(getResources().getColor(R.color.white)); // 设置颜色
				tv.setTextSize(13.0f); // 设置大小
				tv.setGravity(android.view.Gravity.LEFT);
				tv.setPadding(25, 0, 0, 0);
				categoryId = (int) id;
				// TODO (review) bug
				// ideaListView.manualRefresh();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		spinner.setAdapter(adapter);
		getNavigationBar().setLeftView(spinner);

		// 导航右边按钮
		SegmentedButton segmentedButton = new SegmentedButton(this,
				new String[] { "最新", "最热" }, 60, 32);
		segmentedButton
				.setOnClickListener(new SegmentedButton.OnClickListener() {
					@Override
					public void onClick(Button button, int which) {
						switch (which) {
						case 0:
							orderType = "time";
							break;
						case 1:
							orderType = "pop";
							break;
						}
						ideaListView.manualRefresh();
					}
				});
		getNavigationBar().setRightView(segmentedButton);

		// list
		ideaListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				new IdeaListGetDataTask(ideaListView).execute(categoryId,
						orderType, 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				new IdeaListGetDataTask(ideaListView).execute(categoryId,
						orderType, ideaListView.getAdapter().getPager()
								.getCurrentPage() + 1);
			}
		});
		ideaListView.setAdapter(new IdeaListAdapter(IdeaListActivity.this));
		ideaListView.manualRefresh();
	}
}

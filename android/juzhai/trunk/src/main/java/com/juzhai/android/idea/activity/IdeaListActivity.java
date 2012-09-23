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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 内容视图
		setNavContentView(R.layout.page_idea_list);
		final JuzhaiRefreshListView ideaListView = (JuzhaiRefreshListView) findViewById(R.id.idea_list_view);
		// 分类
		final List<Category> categorys = CommonData
				.getCategorys(IdeaListActivity.this);
		String[] categoryNames = CommonData
				.getCategoryNames(IdeaListActivity.this);
		// 导航左边按钮
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.button_category_spinner, categoryNames) {
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
			// TODO (done) 为什么是onItemSelected，而不是onclick？ onItemSelected
			// 如果选择当前选中的item不会触发。少请求一次。
			// TODO (done) 少了所有好主意
			// TODO (done) bug 初始化的时候会执行一次onItemSelected所以manualRefresh放在里面
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO (done) 为什么是事件内部来设置样式？
				categoryId = (int) id;

				ideaListView.manualRefresh();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		spinner.setAdapter(adapter);
		getNavigationBar().setLeftView(spinner);

		// 导航右边按钮
		// TODO (done) 文字配制化（最新，推荐）
		SegmentedButton segmentedButton = new SegmentedButton(this,
				new String[] { getResources().getString(R.string.idea_time),
						getResources().getString(R.string.idea_hot) }, 60, 32);
		segmentedButton
				.setOnClickListener(new SegmentedButton.OnClickListener() {
					@Override
					public void onClick(Button button, int which) {
						switch (which) {
						case 0:
							orderType = "time";
							break;
						case 1:
							// TODO (review)
							// 目前好主意列表支持推荐列表，orderType传入“recommend”（事实上只要OrderType枚举获取不到）就会请求windowIdeas
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
				super.onPullDownToRefresh(refreshView);
				new IdeaListGetDataTask(ideaListView).execute(categoryId,
						orderType, 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new IdeaListGetDataTask(ideaListView).execute(categoryId,
						orderType, ideaListView.getPageAdapter().getPager()
								.getCurrentPage() + 1);
			}
		});
		ideaListView.setAdapter(new IdeaListAdapter(IdeaListActivity.this));

		// ideaListView.manualRefresh();
	}
}

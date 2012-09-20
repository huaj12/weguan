package com.juzhai.android.idea.activity;

import java.util.List;

import org.springframework.util.CollectionUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.CommonData;
import com.juzhai.android.core.model.Category;
import com.juzhai.android.core.pager.Pager;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.button.SegmentedButton;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.adapter.IdeaListAdapter;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.IdeaListAndPager;
import com.juzhai.android.idea.model.IdeaResult;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.idea.service.impl.IdeaService;

public class IdeaListActivity extends NavigationActivity {
	private ProgressBar bar;
	private int categoryId = 0;
	private int page = 1;
	private String orderType = "time";
	private ListView ideaListView;
	private List<Category> categorys;
	private IdeaListAdapter adapter;
	private View view;
	private TextView moreTextView;
	private LinearLayout loadProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 内容视图
		setNavContentView(R.layout.page_idea_list);
		ideaListView = (ListView) findViewById(R.id.idea_listview);
		addPage();
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
						refreshList();
					}
				});
		getNavigationBar().setRightView(segmentedButton);

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
				refreshList();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		bar = (ProgressBar) findViewById(R.id.pro_bar);
	}

	private void refreshList() {
		new AsyncTask<String, Integer, IdeaListAndPager>() {

			@Override
			protected IdeaListAndPager doInBackground(String... params) {
				IIdeaService ideaService = new IdeaService();
				IdeaResult ideaResult = null;
				try {
					page = 1;
					ideaResult = ideaService.list(categoryId, orderType, page);
				} catch (IdeaException e) {
					DialogUtils.showToastText(IdeaListActivity.this,
							e.getMessageId());
					return null;
				}
				if (ideaResult != null && !ideaResult.getSuccess()) {
					DialogUtils.showToastText(IdeaListActivity.this,
							ideaResult.getErrorInfo());
					return null;
				}
				return ideaResult.getResult();
			}

			@Override
			protected void onPostExecute(IdeaListAndPager result) {
				bar.setVisibility(View.GONE);
				if (result != null) {
					ideaListView.setVisibility(View.VISIBLE);
					adapter = new IdeaListAdapter(result.getList(),
							IdeaListActivity.this, LAYOUT_INFLATER_SERVICE);
					ideaListView.setAdapter(adapter);
				}
				showPage(result.getPager());
			}

			@Override
			protected void onPreExecute() {
				bar.setVisibility(View.VISIBLE);
				ideaListView.setVisibility(View.GONE);
			}

		}.execute();
	}

	private void addPage() {
		if (view == null) {
			view = getLayoutInflater().inflate(R.layout.fragment_page, null);
			moreTextView = (TextView) view.findViewById(R.id.more_id);
			loadProgressBar = (LinearLayout) view.findViewById(R.id.load_id);
			moreTextView.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					new AsyncTask<String, Integer, IdeaListAndPager>() {

						@Override
						protected IdeaListAndPager doInBackground(
								String... params) {
							IIdeaService ideaService = new IdeaService();
							IdeaResult ideaResult = null;
							page = page + 1;
							try {
								ideaResult = ideaService.list(categoryId,
										orderType, page);
							} catch (IdeaException e) {
								DialogUtils.showToastText(
										IdeaListActivity.this, e.getMessageId());
								return null;
							}
							if (ideaResult != null && !ideaResult.getSuccess()) {
								DialogUtils.showToastText(
										IdeaListActivity.this,
										ideaResult.getErrorInfo());
								return null;
							}
							return ideaResult.getResult();
						}

						@Override
						protected void onPostExecute(IdeaListAndPager result) {
							if (result != null
									&& !CollectionUtils.isEmpty(result
											.getList())) {
								adapter.push(result.getList());
								adapter.notifyDataSetChanged();
							}
							showPage(result.getPager());

						}

						@Override
						protected void onPreExecute() {
							moreTextView.setVisibility(View.GONE);
							loadProgressBar.setVisibility(View.VISIBLE);
						}

					}.execute();
				}
			});
			ideaListView.addFooterView(view);
		}
	}

	private void showPage(Pager pager) {
		if (pager != null && pager.getHasNext()) {
			moreTextView.setVisibility(View.VISIBLE);
			loadProgressBar.setVisibility(View.GONE);
		} else {
			moreTextView.setVisibility(View.GONE);
			loadProgressBar.setVisibility(View.GONE);
		}
	}
}

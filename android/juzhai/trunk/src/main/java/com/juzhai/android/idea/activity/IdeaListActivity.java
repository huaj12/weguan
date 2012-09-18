package com.juzhai.android.idea.activity;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.adapter.IdeaListAdapter;
import com.juzhai.android.idea.model.IdeaResult;
import com.juzhai.android.idea.model.IdeaListAndPager;
import com.juzhai.android.passport.data.UserCache;

public class IdeaListActivity extends NavigationActivity {
	private String uri = "idea/list";
	private ProgressBar bar;
	private String categoryId = "0";
	private String orderType = "pop";
	private ListView ideaListView;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.idea_list);
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
				ideaListView.setAdapter(new IdeaListAdapter(result, mContext,
						LAYOUT_INFLATER_SERVICE));
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
	}
}

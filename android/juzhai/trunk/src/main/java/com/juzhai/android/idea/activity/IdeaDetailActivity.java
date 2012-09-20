/**
 * 
 */
package com.juzhai.android.idea.activity;

import org.apache.commons.lang.StringUtils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.adapter.IdeaInfoAdapter;
import com.juzhai.android.idea.model.Idea;

/**
 * @author kooks
 * 
 */
public class IdeaDetailActivity extends NavigationActivity {
	private ListView ideaInfoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.idea_detail_title));
		setNavContentView(R.layout.page_idea_detail);
		Idea idea = (Idea) getIntent().getSerializableExtra("idea");
		if (idea == null) {
			popIntent();
		}
		final ImageView imageView = (ImageView) findViewById(R.id.idea_image);
		TextView useCountText = (TextView) findViewById(R.id.idea_use_count_txet);
		TextView contentText = (TextView) findViewById(R.id.idea_content);
		ideaInfoList = (ListView) findViewById(R.id.idea_info_list);
		contentText.setText(idea.getContent());
		useCountText.setText(getResources().getString(R.string.use_count_begin)
				+ idea.getUseCount()
				+ getResources().getString(R.string.use_count_end));
		ImageViewLoader nid = ImageViewLoader
				.getInstance(IdeaDetailActivity.this);
		if (StringUtils.isNotEmpty(idea.getBigPic())) {
			nid.fetchImage(idea.getBigPic().replaceAll("test.", ""), 0,
					imageView, new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(
												IdeaDetailActivity.this, 262),
										UIUtil.dip2px(IdeaDetailActivity.this,
												180));
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		}
		ideaInfoList.setAdapter(new IdeaInfoAdapter(idea, getLayoutInflater()));
	}
}

/**
 * 
 */
package com.juzhai.android.idea.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.model.Idea;
import com.umeng.analytics.MobclickAgent;

/**
 * @author kooks
 * 
 */
public class IdeaDetailActivity extends NavigationActivity {
	private Idea idea;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.idea_detail_title));
		setNavContentView(R.layout.page_idea_detail);
		idea = (Idea) getIntent().getSerializableExtra("idea");
		if (idea == null) {
			popIntent();
		}
		final ImageView imageView = (ImageView) findViewById(R.id.idea_image);
		TextView useCountText = (TextView) findViewById(R.id.idea_use_count_txet);
		TextView contentText = (TextView) findViewById(R.id.idea_content);
		final Button wantBtn = (Button) findViewById(R.id.idea_want_btn);
		final Button shareBtn = (Button) findViewById(R.id.idea_share_btn);
		contentText.setText(idea.getContent());
		if (idea.getUseCount() != null && idea.getUseCount() > 0) {
			useCountText.setText(getResources().getString(
					R.string.use_count_begin)
					+ idea.getUseCount()
					+ getResources().getString(R.string.use_count_end));
			useCountText.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(IdeaDetailActivity.this,
							IdeaUsersActivity.class);
					intent.putExtra("idea", idea);
					pushIntent(intent);
				}
			});
		}
		if (StringUtils.isNotEmpty(idea.getBigPic())) {
			ImageViewLoader nid = ImageViewLoader
					.getInstance(IdeaDetailActivity.this);
			nid.fetchImage(JzUtils.getImageUrl(idea.getBigPic()),
					R.drawable.good_idea_list_pic_none_icon, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(
												IdeaDetailActivity.this, 262),
										UIUtil.dip2px(IdeaDetailActivity.this,
												180));
								imageView.setImageBitmap(zoomBitmap);
							}
						}
					});
		}
		if (idea.isHasUsed()) {
			wantBtn.setText(R.string.want_done);
			wantBtn.setEnabled(false);
		} else {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("ideaId", idea.getIdeaId());
			wantBtn.setOnClickListener(new SimpleClickListener("post/sendPost",
					IdeaDetailActivity.this, values, new TaskCallback() {
						@Override
						public void successCallback() {
							wantBtn.setText(R.string.want_done);
							wantBtn.setEnabled(false);
							idea.setHasUsed(true);
							Intent intent = getIntent();
							intent.putExtra("idea", idea);
							setResult(
									ActivityCode.ResultCode.IDEA_LIST_RESULT_CODE,
									intent);
							MobclickAgent.onEvent(IdeaDetailActivity.this,
									UmengEvent.SEND_IDEA);
						}

						@Override
						public String doInBackground() {
							return null;
						}
					}));
		}
		shareBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "title");
				intent.putExtra(Intent.EXTRA_TEXT, "text");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, "分享"));
			}
		});
		initIdeaInfo();

	}

	private void initIdeaInfo() {
		// 设置地点
		LinearLayout placeLayout = (LinearLayout) findViewById(R.id.idea_place_layout);
		TextView place = (TextView) findViewById(R.id.idea_place_text);
		if (StringUtils.isNotEmpty(idea.getCityName())
				|| StringUtils.isNotEmpty(idea.getTownName())
				|| StringUtils.isNotEmpty(idea.getPlace())) {
			StringBuffer sbStr = new StringBuffer();
			if (StringUtils.isNotEmpty(idea.getCityName())) {
				sbStr.append(idea.getCityName());
				sbStr.append(" ");
			}
			if (StringUtils.isNotEmpty(idea.getTownName())) {
				sbStr.append(idea.getTownName());
				sbStr.append(" ");
			}
			sbStr.append(idea.getPlace());
			place.setText(sbStr.toString());
		} else {
			placeLayout.setVisibility(View.GONE);
		}
		// 设置时间
		LinearLayout timeLayout = (LinearLayout) findViewById(R.id.idea_time_layout);
		TextView time = (TextView) findViewById(R.id.idea_time_text);
		if (StringUtils.isNotEmpty(idea.getStartTime())
				|| StringUtils.isNotEmpty(idea.getEndTime())) {
			String startTime = idea.getStartTime() == null ? "" : idea
					.getStartTime();
			String endTime = idea.getEndTime() == null ? "" : idea.getEndTime();
			time.setText(startTime + " " + endTime);
		} else {
			timeLayout.setVisibility(View.GONE);
		}
		// 设置价格
		LinearLayout chargeLayout = (LinearLayout) findViewById(R.id.idea_charge_layout);
		TextView charge = (TextView) findViewById(R.id.idea_charge_text);
		if (idea.getCharge() != null) {
			charge.setText(idea.getCharge()
					+ getResources().getString(R.string.yuan));
		} else {
			chargeLayout.setVisibility(View.GONE);
		}
	}
}

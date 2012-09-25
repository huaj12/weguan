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
import com.juzhai.android.core.listener.ListenerSuccessCallBack;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.model.Idea;

/**
 * @author kooks
 * 
 */
public class IdeaDetailActivity extends NavigationActivity {
	public final static int IDEA_LIST_RESULT_CODE = 4;
	private Idea idea;
	private ImageView imageView;
	private Button wantBtn;

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
		imageView = (ImageView) findViewById(R.id.idea_image);
		TextView useCountText = (TextView) findViewById(R.id.idea_use_count_txet);
		TextView contentText = (TextView) findViewById(R.id.idea_content);
		wantBtn = (Button) findViewById(R.id.idea_want_btn);
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
					pushIntentForResult(intent, CLEAR_REQUEST_CODE);
				}
			});
		}
		ImageViewLoader nid = ImageViewLoader
				.getInstance(IdeaDetailActivity.this);
		if (StringUtils.isNotEmpty(idea.getBigPic())) {
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
			wantBtn.setBackgroundResource(R.drawable.good_idea_detial_btn_wtg_done);
		} else {
			Map<String, String> values = new HashMap<String, String>();
			values.put("ideaId", String.valueOf(idea.getIdeaId()));
			wantBtn.setOnClickListener(new SimpleClickListener("post/sendPost",
					IdeaDetailActivity.this, values,
					new ListenerSuccessCallBack() {
						@Override
						public void callback() {
							wantBtn.setText(R.string.want_done);
							wantBtn.setBackgroundResource(R.drawable.good_idea_detial_btn_wtg_done);
							wantBtn.setOnClickListener(null);
							idea.setHasUsed(true);
							Intent intent = new Intent(IdeaDetailActivity.this,
									IdeaListActivity.class);
							intent.putExtra("idea", idea);
							intent.putExtra("position", getIntent()
									.getIntExtra("position", 0));
							setResult(IDEA_LIST_RESULT_CODE, intent);
						}
					}));
		}
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
			time.setText(idea.getStartTime() == null ? "" : idea.getStartTime()
					+ " " + idea.getEndTime() == null ? "" : idea.getEndTime());
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

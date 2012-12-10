/**
 * 
 */
package com.juzhai.android.idea.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.common.service.IShareService;
import com.juzhai.android.common.service.impl.ShareService;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.model.Result.IdeaResult;
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.idea.service.impl.IdeaService;
import com.umeng.analytics.MobclickAgent;

/**
 * @author kooks
 * 
 */
public class IdeaDetailActivity extends NavigationActivity {
	private Idea idea;
	private Bitmap ideaBitmap;
	private Uri ideaUri;
	long ideaId = 0;
	private IShareService shareService = new ShareService();
	private IIdeaService ideaService = new IdeaService();
	private ProgressDialog progressDialog;

	class LoadIdeaTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			IdeaResult result = null;
			try {
				result = ideaService.showIdea(IdeaDetailActivity.this, ideaId);
			} catch (IdeaException e) {
				DialogUtils.showErrorDialog(IdeaDetailActivity.this,
						e.getMessage());
				return null;
			}
			if (result.getSuccess()) {
				idea = result.getResult();
			} else {
				DialogUtils.showErrorDialog(IdeaDetailActivity.this,
						result.getErrorInfo());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			initIdeaDetail();
		}

		@Override
		protected void onPreExecute() {
			if (progressDialog != null) {
				progressDialog.show();
			} else {
				progressDialog = ProgressDialog.show(IdeaDetailActivity.this,
						// TODO (done) sending和loading什么区别？“正在请求”&“正在加载”
						getResources().getString(R.string.loding),
						IdeaDetailActivity.this.getResources().getString(
								R.string.please_wait), true, false);
			}
			super.onPreExecute();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.idea_detail_title));
		setNavContentView(R.layout.page_idea_detail);
		idea = (Idea) getIntent().getSerializableExtra("idea");
		ideaId = getIntent().getLongExtra("ideaId", 0);
		if (idea == null) {
			LoadIdeaTask task = new LoadIdeaTask();
			task.execute();
		} else {
			initIdeaDetail();
		}

	}

	private void initIdeaDetail() {
		final ImageView imageView = (ImageView) findViewById(R.id.idea_image);
		TextView useCountText = (TextView) findViewById(R.id.idea_use_count_txet);
		TextView contentText = (TextView) findViewById(R.id.idea_content);
		final Button wantBtn = (Button) findViewById(R.id.idea_want_btn);
		final Button shareBtn = (Button) findViewById(R.id.idea_share_btn);
		final RelativeLayout imageLayout = (RelativeLayout) findViewById(R.id.idea_image_bg_layout);
		// TODO (done) 为什么会提取出来了？默认是GONE的，满足if就VISIABLE不行吗？
		RelativeLayout useCountlayout = (RelativeLayout) findViewById(R.id.idea_use_count_layout);
		// TODO (done) 为什么不用TextView
		TextView catImageTextView = (TextView) findViewById(R.id.idea_cat_btn);
		catImageTextView.setBackgroundResource(JzUtils.getCategoryBackground(
				idea.getCategoryId(), IdeaDetailActivity.this));
		catImageTextView.setEnabled(false);
		catImageTextView.setText(idea.getCategoryName());
		contentText.setText(idea.getContent());
		if (idea.getUseCount() != null && idea.getUseCount() > 0) {
			useCountText.setText(idea.getUseCount() + "");
			useCountlayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(IdeaDetailActivity.this,
							IdeaUsersActivity.class);
					intent.putExtra("idea", idea);
					pushIntent(intent);
				}
			});
			useCountlayout.setVisibility(View.VISIBLE);
		}
		if (StringUtils.hasText(idea.getBigPic())) {
			final ImageViewLoader nid = ImageViewLoader
					.getInstance(IdeaDetailActivity.this);
			nid.fetchImage(JzUtils.getImageUrl(idea.getBigPic()),
					R.drawable.good_idea_list_pic_none_icon, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								ideaBitmap = bitmap;
								Bitmap zoomBitmap = ImageUtils
										.ZoomBitmapNotCut(bitmap, 262, 180,
												IdeaDetailActivity.this);
								imageLayout.getLayoutParams().height = zoomBitmap
										.getHeight();
								imageLayout.getLayoutParams().width = zoomBitmap
										.getWidth();
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
					IdeaDetailActivity.this, values, false, new TaskCallback() {
						@Override
						public void successCallback() {
							DialogUtils.showSuccessDialog(
									IdeaDetailActivity.this,
									R.string.i_want_success_text);
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
				if (null == ideaUri && ideaBitmap != null) {
					FileOutputStream fos = null;
					String fileName = new String(Hex.encodeHex(DigestUtils
							.md5(idea.getBigPic()))) + ".jpg";
					try {
						fos = IdeaDetailActivity.this.openFileOutput(fileName,
								Context.MODE_WORLD_READABLE);
						ideaBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
								fos);
					} catch (FileNotFoundException e) {
					} finally {
						try {
							fos.close();
						} catch (IOException e) {
						}
					}
					File file = IdeaDetailActivity.this
							.getFileStreamPath(fileName);
					ideaUri = Uri.fromFile(file);
				}
				shareService.openIdeaSharePop(IdeaDetailActivity.this, idea,
						ideaUri);
			}
		});
		initIdeaInfo();
	}

	private void initIdeaInfo() {
		boolean flag = false;
		// 设置地点
		RelativeLayout placeLayout = (RelativeLayout) findViewById(R.id.idea_place_layout);
		TextView place = (TextView) findViewById(R.id.idea_place_text);
		// TODO (done) 有city，有town，没place的状态下，显示地址那条信息？
		if (StringUtils.hasText(idea.getPlace())) {
			StringBuffer sbStr = new StringBuffer();
			if (StringUtils.hasText(idea.getCityName())) {
				sbStr.append(idea.getCityName());
				sbStr.append(" ");
			}
			if (StringUtils.hasText(idea.getTownName())) {
				sbStr.append(idea.getTownName());
				sbStr.append(" ");
			}
			sbStr.append(idea.getPlace());
			place.setText(sbStr.toString());
			flag = true;
		} else {
			placeLayout.setVisibility(View.GONE);
		}
		// 设置时间
		RelativeLayout timeLayout = (RelativeLayout) findViewById(R.id.idea_time_layout);
		TextView time = (TextView) findViewById(R.id.idea_time_text);
		if (StringUtils.hasText(idea.getStartTime())
				|| StringUtils.hasText(idea.getEndTime())) {
			String startTime = idea.getStartTime() == null ? "" : idea
					.getStartTime();
			String endTime = idea.getEndTime() == null ? "" : idea.getEndTime();
			// TODO (done) 个人资料可以用空格隔开，时间段怎么能用空格呢？
			String split = " ";
			if (StringUtils.hasText(startTime) && StringUtils.hasText(endTime)) {
				split = "-";
			}
			time.setText(startTime + split + endTime);
			flag = true;
		} else {
			timeLayout.setVisibility(View.GONE);
		}
		// 设置价格
		RelativeLayout chargeLayout = (RelativeLayout) findViewById(R.id.idea_charge_layout);
		TextView charge = (TextView) findViewById(R.id.idea_charge_text);
		if (idea.getCharge() != null) {
			charge.setText(idea.getCharge()
					+ getResources().getString(R.string.yuan));
			flag = true;
		} else {
			chargeLayout.setVisibility(View.GONE);
		}
		TextView tipView = (TextView) findViewById(R.id.idea_detail_list_info_tip_view);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.idea_info_list_relativeLayout);
		// TODO (review) flag这个局部变量其实可以不用，你想想看有什么办法
		if (!flag) {
			tipView.setVisibility(View.GONE);
			layout.setVisibility(View.GONE);
		}
		TextView detailView = (TextView) findViewById(R.id.idea_detail_text_view);
		RelativeLayout detailRelativeLayoutView = (RelativeLayout) findViewById(R.id.idea_detail_text_layout);
		TextView detailTipView = (TextView) findViewById(R.id.idea_detail_tip_view);
		if (StringUtils.hasText(idea.getDetail())) {
			detailView.setText(idea.getDetail());
		} else {
			detailTipView.setVisibility(View.GONE);
			detailRelativeLayoutView.setVisibility(View.GONE);
		}
	}
}

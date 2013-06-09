package com.easylife.movie.video.activity;

import java.util.Random;

import org.springframework.util.StringUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdManager.ErrorCode;
import cn.domob.android.ads.DomobAdView;

import com.easylife.movie.R;
import com.easylife.movie.common.ad.DomobActivity;
import com.easylife.movie.common.service.CommonData;
import com.easylife.movie.common.service.IShareService;
import com.easylife.movie.common.service.impl.ShareService;
import com.easylife.movie.core.Constants;
import com.easylife.movie.core.activity.BaseActivity;
import com.easylife.movie.core.stat.UmengEvent;
import com.easylife.movie.core.utils.DialogUtils;
import com.easylife.movie.core.utils.ImageUtils;
import com.easylife.movie.core.widget.image.ImageLoaderCallback;
import com.easylife.movie.core.widget.image.ImageViewLoader;
import com.easylife.movie.video.model.Category;
import com.easylife.movie.video.model.Video;
import com.easylife.movie.video.service.IVideoService;
import com.easylife.movie.video.service.impl.VideoService;
import com.life.DianJinPlatform;
import com.life.DianJinPlatform.OfferWallStyle;
import com.life.DianJinPlatform.Oriention;
import com.life.listener.AppActivatedListener;
import com.life.listener.OfferWallStateListener;
import com.life.webservice.WebServiceListener;
import com.umeng.analytics.MobclickAgent;

public class VideoActivity extends BaseActivity {
	private Video video;
	private IVideoService videoService = new VideoService();
	RelativeLayout mAdContainer;
	DomobAdView mAdview320x50;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 点金
		DianJinPlatform.setAppActivatedListener(new AppActivatedListener() {
			@Override
			public void onAppActivatedResponse(int responseCode, Float money) {
				switch (responseCode) {
				case DianJinPlatform.APP_ACTIVATED_SUCESS:
					Toast.makeText(
							VideoActivity.this,
							VideoActivity.this.getResources().getString(
									R.string.get_reward_sucess,
									String.valueOf(money)), Toast.LENGTH_SHORT)
							.show();
					MobclickAgent.onEvent(VideoActivity.this,
							UmengEvent.ACTIVITY_OFFER);
					break;
				default:
					break;
				}
			}
		});
		setContentView(R.layout.page_video);
		video = (Video) getIntent().getSerializableExtra("video");
		if (video == null) {
			finish();
			return;
		}
		Button playBtn = (Button) findViewById(R.id.play_btn);
		final ImageView videoImage = (ImageView) findViewById(R.id.video_image);
		TextView videoTitle = (TextView) findViewById(R.id.video_title);
		TextView catName = (TextView) findViewById(R.id.video_cat_name);
		TextView videoTime = (TextView) findViewById(R.id.video_time);
		Button backBtn = (Button) findViewById(R.id.back_view);
		final Button interestBtn = (Button) findViewById(R.id.interest_view);
		final Button unInterestBtn = (Button) findViewById(R.id.un_interest_view);
		Button forwardBtn = (Button) findViewById(R.id.forwards_view);
		if (StringUtils.hasText(video.getPosterImg())) {
			ImageViewLoader nid = ImageViewLoader
					.getInstance(VideoActivity.this);
			nid.fetchImage(video.getPosterImg(), 0, videoImage,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils
										.zoomWidthCutMiddle(bitmap, 280, 200,
												VideoActivity.this);
								videoImage.setImageBitmap(zoomBitmap);
							}
						}
					});
		}
		videoTitle.setText(video.getTitle());
		Category category = CommonData.getCategory(video.getCategoryId(),
				VideoActivity.this);
		if (category != null) {
			catName.setText(category.getName());
		}
		videoTime.setText(video.getPlayTime());
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		playBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (video.getCategoryId() != Constants.CHARGE_CATEGORY
						|| videoService.hasHistory(VideoActivity.this,
								video.getId())) {
					play();
				} else {
					DianJinPlatform.consume(VideoActivity.this,
							Constants.AMOUNT,
							new WebServiceListener<Integer>() {
								@Override
								public void onResponse(int responseCode,
										Integer t) {
									switch (responseCode) {
									case DianJinPlatform.DIANJIN_SUCCESS:
										// 扣过积分的直接播放不经过广告
										Intent intent = new Intent(
												Intent.ACTION_VIEW);
										intent.setDataAndType(
												Uri.parse(video.getVideoSrc()),
												"video/*");
										startActivity(intent);
										new AsyncTask<Void, Void, Void>() {

											@Override
											protected Void doInBackground(
													Void... params) {
												videoService.addHistory(
														VideoActivity.this,
														video);
												return null;
											}

										}.execute();
										break;
									default:
										new AlertDialog.Builder(
												VideoActivity.this)
												.setMessage(
														VideoActivity.this
																.getResources()
																.getString(
																		R.string.point_confirm,
																		String.valueOf(Constants.AMOUNT)))
												.setTitle(R.string.confirm)
												.setPositiveButton(
														R.string.point_ok,
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																DianJinPlatform
																		.showOfferWall(
																				VideoActivity.this,
																				Oriention.SENSOR,
																				OfferWallStyle.BROWN);
															}
														})
												.setNegativeButton(

														R.string.cancel,
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																dialog.cancel();
															}
														}).show();
										break;
									}
								}
							});
				}

			}
		});
		if (videoService.hasInterest(VideoActivity.this, video.getId())) {
			unInterestBtn.setVisibility(View.VISIBLE);
			interestBtn.setVisibility(View.GONE);
		} else {
			unInterestBtn.setVisibility(View.GONE);
			interestBtn.setVisibility(View.VISIBLE);
		}
		interestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				videoService.addInterest(VideoActivity.this, video);
				DialogUtils.showSuccessDialog(VideoActivity.this,
						R.string.interest_success);
				unInterestBtn.setVisibility(View.VISIBLE);
				interestBtn.setVisibility(View.GONE);
			}
		});
		unInterestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(VideoActivity.this)
						.setMessage(R.string.un_interest_confirm)
						.setTitle(R.string.confirm)
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										videoService.removeInterest(
												VideoActivity.this,
												video.getId());
										DialogUtils.showSuccessDialog(
												VideoActivity.this,
												R.string.un_interest);
										unInterestBtn.setVisibility(View.GONE);
										interestBtn.setVisibility(View.VISIBLE);
									}
								}).setNegativeButton(

						R.string.cancel, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}).show();

			}
		});
		forwardBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IShareService shareService = new ShareService();
				shareService.openSharePop(
						video.getTitle() + video.getVideoSrc(),
						VideoActivity.this, video);
			}
		});
		mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
		// Create ad view
		mAdview320x50 = new DomobAdView(this, Constants.PUBLISHER_ID,
				Constants.InlinePPID, DomobAdView.INLINE_SIZE_320X50);
		mAdview320x50.setAdEventListener(new DomobAdEventListener() {

			@Override
			public void onDomobAdReturned(DomobAdView adView) {
				Log.i("DomobSDKDemo", "onDomobAdReturned");
			}

			@Override
			public void onDomobAdOverlayPresented(DomobAdView adView) {
				Log.i("DomobSDKDemo", "overlayPresented");
			}

			@Override
			public void onDomobAdOverlayDismissed(DomobAdView adView) {
				Log.i("DomobSDKDemo", "Overrided be dismissed");
			}

			@Override
			public void onDomobAdClicked(DomobAdView arg0) {
				MobclickAgent.onEvent(VideoActivity.this,
						UmengEvent.BANNER_CLICK);
				Log.i("DomobSDKDemo", "onDomobAdClicked");
			}

			@Override
			public void onDomobAdFailed(DomobAdView arg0, ErrorCode arg1) {
				Log.i("DomobSDKDemo", "onDomobAdFailed");
			}

			@Override
			public void onDomobLeaveApplication(DomobAdView arg0) {
				Log.i("DomobSDKDemo", "onDomobLeaveApplication");
			}

			@Override
			public Context onDomobAdRequiresCurrentContext() {
				return VideoActivity.this;
			}
		});

		mAdContainer.addView(mAdview320x50);
		/* * 监听推广墙状态接口 */

		DianJinPlatform.setOfferWallStateListener(new OfferWallStateListener() {
			@Override
			public void onOfferWallState(int code) {
				switch (code) {
				case DianJinPlatform.DIANJIN_OFFERWALL_START:
					MobclickAgent.onEvent(VideoActivity.this,
							UmengEvent.OPEN_OFFER);
					break;
				default:
					break;
				}
			}
		});
	}

	private void play() {
		if (video != null) {
			Random random = new Random();
			if (random.nextInt(100) + 1 > Constants.AD_PROBABILITY) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(video.getVideoSrc()), "video/*");
				startActivity(intent);
			} else {
				Intent intent = new Intent(VideoActivity.this,
						DomobActivity.class);
				intent.putExtra("video_url", video.getVideoSrc());
				// intent.putExtra("title", video.getTitle());
				startActivity(intent);
			}
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					videoService.addHistory(VideoActivity.this, video);
					return null;
				}

			}.execute();

		}
	}

}

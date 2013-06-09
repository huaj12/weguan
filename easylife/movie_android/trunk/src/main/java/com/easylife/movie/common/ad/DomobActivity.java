package com.easylife.movie.common.ad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import cn.domob.android.ads.DomobRTSplashAd;
import cn.domob.android.ads.DomobSplashAd;
import cn.domob.android.ads.DomobSplashAd.DomobSplashMode;
import cn.domob.android.ads.DomobSplashAdListener;

import com.easylife.movie.R;
import com.easylife.movie.core.Constants;
import com.easylife.movie.core.activity.BaseActivity;
import com.easylife.movie.core.stat.UmengEvent;
import com.umeng.analytics.MobclickAgent;

public class DomobActivity extends BaseActivity {
	DomobSplashAd splashAd;
	DomobRTSplashAd rtSplashAd;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_domob);
		url = getIntent().getStringExtra("video_url");
		/**
		 * 
		 * DomobSplashMode.DomobSplashModeFullScreen 请求开屏广告的尺寸为全屏
		 * DomobSplashMode.DomobSplashModeSmallEmbed
		 * 请求开屏广告的尺寸不是全屏，根据设备分辨率计算出合适的小屏尺寸
		 * DomobSplashMode.DomobSplashModeBigEmbed
		 * 请求开屏广告的尺寸不是全屏，更具设备分辨率计算出合适的相对SmallMode的尺寸
		 * 
		 */

		// 缓存开屏广告
		splashAd = new DomobSplashAd(this, Constants.PUBLISHER_ID,
				Constants.SplashPPID, DomobSplashMode.DomobSplashModeFullScreen);
		// setSplashTopMargin is available when you choose non-full-screen
		// splash mode.
		// splashAd.setSplashTopMargin(200);
		splashAd.setSplashAdListener(new DomobSplashAdListener() {
			@Override
			public void onSplashPresent() {
				Log.i("DomobSDKDemo", "onSplashStart");
			}

			@Override
			public void onSplashDismiss() {
				Log.i("DomobSDKDemo", "onSplashClosed");
				MobclickAgent.onEvent(DomobActivity.this,
						UmengEvent.OPEN_FULLAD);
				jump();
			}

			@Override
			public void onSplashLoadFailed() {
				Log.i("DomobSDKDemo", "onSplashLoadFailed");
			}
		});

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (splashAd.isSplashAdReady()) {
					splashAd.splash(DomobActivity.this,
							DomobActivity.this.findViewById(R.id.splash_holder));
				} else {
					jump();
				}
			}
		}, 1);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("DomobSDKDemo", "Splash onDestroy");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Back key disabled
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void jump() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse(url), "video/*");
		startActivity(intent);
		finish();
	}
}

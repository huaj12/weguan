package com.easylife.movie.main.activity;

import android.content.Intent;
import android.os.Bundle;
import cn.sharesdk.framework.AbstractWeibo;

import com.easylife.movie.R;
import com.easylife.movie.core.Constants;
import com.easylife.movie.core.activity.BaseActivity;
import com.easylife.movie.core.utils.MovieUtils;
import com.life.DianJinPlatform;

public class LaunchActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_launch);
		AbstractWeibo.initSDK(this);
		DianJinPlatform.initialize(this, Constants.DIANJIN_ID,
				Constants.DIANJIN_SECRET_KEY);
		DianJinPlatform.hideDianJinFloatView(LaunchActivity.this);
		startActivity(new Intent(LaunchActivity.this, MainActivity.class));
		finish();
		MovieUtils.setRepeating(this);
	}
}
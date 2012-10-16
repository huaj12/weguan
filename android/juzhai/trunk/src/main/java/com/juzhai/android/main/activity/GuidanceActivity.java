package com.juzhai.android.main.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.pager.CirclePageIndicator;
import com.juzhai.android.core.widget.pager.PageIndicator;
import com.juzhai.android.main.adapter.GuidancePagerAdapter;
import com.umeng.analytics.MobclickAgent;

public class GuidanceActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.page_guidance);

		GuidancePagerAdapter mAdapter = new GuidancePagerAdapter(
				getSupportFragmentManager());

		ViewPager mPager = (ViewPager) findViewById(R.id.guidance_pager);
		mPager.setAdapter(mAdapter);

		PageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.guidance_indicator);
		mIndicator.setViewPager(mPager);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}

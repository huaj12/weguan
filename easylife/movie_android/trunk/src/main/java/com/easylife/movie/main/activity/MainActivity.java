package com.easylife.movie.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.easylife.movie.R;
import com.easylife.movie.core.Constants;
import com.easylife.movie.core.widget.list.MovieRefreshListView;
import com.easylife.movie.core.widget.menu.SlidingMenu;
import com.easylife.movie.core.widget.menu.app.SlidingFragmentActivity;
import com.easylife.movie.main.fragment.LeftFragment;
import com.easylife.movie.main.fragment.RightFragment;
import com.easylife.movie.video.helper.IVideoHelper;
import com.easylife.movie.video.helper.impl.VideoHelper;
import com.life.DianJinPlatform;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends SlidingFragmentActivity {
	private SlidingMenu slidingMenu;
	private LeftFragment leftFragment;
	private RightFragment rightFragment;
	private MovieRefreshListView listView;
	private long categoryId = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		DianJinPlatform.initialize(this, Constants.DIANJIN_ID,
				Constants.DIANJIN_SECRET_KEY);
		setContentView(R.layout.page_center);
		Button leftBtn = (Button) findViewById(R.id.showLeft);
		Button rightBtn = (Button) findViewById(R.id.showRight);
		rightBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingMenu.showSecondaryMenu();
			}
		});
		leftBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingMenu.showMenu();
			}
		});
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setShadowWidth(getWindowManager().getDefaultDisplay()
				.getWidth() / 40);
		slidingMenu.setShadowDrawable(R.drawable.menu_shadow);
		slidingMenu.setSecondaryShadowDrawable(R.drawable.menu_shadow_r);
		slidingMenu.setBehindOffset(getWindowManager().getDefaultDisplay()
				.getWidth() / 6);
		slidingMenu.setFadeEnabled(true);
		slidingMenu.setFadeDegree(0.4f);
		slidingMenu.setBehindScrollScale(0);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setBehindContentView(R.layout.fragment_left);
		slidingMenu.setSecondaryMenu(R.layout.fragment_right);
		FragmentTransaction t = MainActivity.this.getSupportFragmentManager()
				.beginTransaction();
		leftFragment = new LeftFragment();
		leftFragment.setContext(MainActivity.this);
		t.replace(R.id.left_frame, leftFragment);

		rightFragment = new RightFragment();
		rightFragment.setContext(MainActivity.this);
		slidingMenu.setSecondaryMenu(R.layout.fragment_right);
		t.replace(R.id.right_frame, rightFragment);
		t.commitAllowingStateLoss();
		init();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		categoryId = intent.getLongExtra("categoryId", 0);
		init();
		showContent();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void init() {
		listView = (MovieRefreshListView) findViewById(R.id.movie_list_view);
		IVideoHelper helper = new VideoHelper();
		helper.showMovieRefreshListView(listView, categoryId, MainActivity.this);
	}

	@Override
	protected void onDestroy() {
		DianJinPlatform.destroy();
		super.onDestroy();
	}
}

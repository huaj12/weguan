/**
 * 
 */
package com.juzhai.android.main.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.juzhai.android.R;
import com.juzhai.android.common.service.CommonData;
import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.main.service.impl.GuidanceService;
import com.juzhai.android.passport.activity.LoginActivity;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

/**
 * @author kooks
 * 
 */
public class LaunchActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.onError(this);
		setContentView(R.layout.page_launch);
		new AsyncTask<Void, Void, Boolean>() {
			protected void onPostExecute(Boolean result) {
				// 判断是否走过引导
				if (!new GuidanceService().hasGuide(LaunchActivity.this)) {
					// 跳往引导
					clearStackAndStartActivity(new Intent(LaunchActivity.this,
							GuidanceActivity.class));
					return;
				}
				if (result) {
					if (UserCache.getUserInfo().isHasGuided()) {
						clearStackAndStartActivity(new Intent(
								LaunchActivity.this, MainTabActivity.class));
					} else {
						clearStackAndStartActivity(new Intent(
								LaunchActivity.this, UserGuideActivity.class));
					}
				} else {
					clearStackAndStartActivity(new Intent(LaunchActivity.this,
							LoginActivity.class));
				}
			};

			@Override
			protected Boolean doInBackground(Void... params) {
				IPassportService passportService = new PassportService();
				return passportService.checkLogin(LaunchActivity.this);
			}
		}.execute();
		// 初始化数据
		CommonData.initDate(LaunchActivity.this);
	}
}

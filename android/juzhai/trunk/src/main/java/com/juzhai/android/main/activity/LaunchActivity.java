/**
 * 
 */
package com.juzhai.android.main.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.core.task.InitDataTask;
import com.juzhai.android.passport.activity.LoginActivity;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

/**
 * @author kooks
 * 
 */
public class LaunchActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// // Android OS 3.0之后，需要以下代码，才能在主线程进行webService访问
		// StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		// .detectDiskReads().detectDiskWrites().detectNetwork()
		// .penaltyLog().build());
		// StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		// .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
		// .build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_launch);

		// 初始化ProgressBar
		// ProgressBar bar = (ProgressBar) findViewById(R.id.pro_bar);
		// bar.setProgress(0);

		new AsyncTask<Void, Void, Boolean>() {
			protected void onPostExecute(Boolean result) {
				if (result) {
					clearStackAndStartActivity(new Intent(LaunchActivity.this,
							MainTabActivity.class));
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
		new InitDataTask(LaunchActivity.this).execute();
	}
}

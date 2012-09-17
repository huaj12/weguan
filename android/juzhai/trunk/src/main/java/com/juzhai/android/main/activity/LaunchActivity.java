/**
 * 
 */
package com.juzhai.android.main.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ProgressBar;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.activity.LoginActivity;
import com.juzhai.android.passport.bean.UserCacheManager;
import com.juzhai.android.passport.model.UserResults;

/**
 * @author kooks
 * 
 */
public class LaunchActivity extends Activity {
	private ProgressBar bar = null;
	private Intent intent;
	private String loginUri = "passport/login";
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		bar = (ProgressBar) findViewById(R.id.pro_bar);
		bar.setProgress(0);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				SharedPreferences sharedPreferences = getSharedPreferences(
						"juzhai-android", MODE_PRIVATE);
				String p_token = null;
				if (sharedPreferences.contains("p_token")) {
					p_token = sharedPreferences.getString("p_token", null);
				}
				if (StringUtils.isEmpty(p_token)) {
					// 没有记住登录状态跳转到登录页面
					intent = new Intent(mContext, LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					// 有记录登录状态直接登录
					Map<String, String> cookies = new HashMap<String, String>();
					cookies.put("p_token", p_token);
					ResponseEntity<UserResults> responseEntity = null;
					try {
						responseEntity = HttpUtils.post(loginUri, null,
								cookies, UserResults.class);
					} catch (Exception e) {
						DialogUtils.showToastText(mContext,
								R.string.system_internet_erorr);
						// 登录失败跳转到登录页面
						intent = new Intent(mContext, LoginActivity.class);
						startActivity(intent);
						finish();
					}
					UserCacheManager.initUserCacheManager(responseEntity,
							mContext);
					intent = new Intent(mContext, MainTabActivity.class);
					startActivity(intent);
					finish();
				}
			}
		};
		// 2秒后执行一次
		new Timer().schedule(task, 2000);

	}
}

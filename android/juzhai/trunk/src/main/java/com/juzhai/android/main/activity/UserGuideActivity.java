/**
 * 
 */
package com.juzhai.android.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.juzhai.android.R;
import com.juzhai.android.core.task.ProgressTask;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.home.activity.SetUserInfoActivity;
import com.juzhai.android.passport.exception.ProfileException;
import com.juzhai.android.passport.service.IProfileService;
import com.juzhai.android.passport.service.impl.ProfileService;

/**
 * @author kooks
 * 
 */
public class UserGuideActivity extends SetUserInfoActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setmContext(UserGuideActivity.this);
		setGuide(true);
		setNavContentView(R.layout.page_user_guide);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.user_guide_title));
		// TODO (review) finish按钮设置可以封装起来
		finish = (Button) getLayoutInflater().inflate(R.layout.button_finish,
				null);
		finish.setEnabled(false);
		getNavigationBar().setRightView(finish);
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ProgressTask(UserGuideActivity.this, new TaskCallback() {

					@Override
					public void successCallback() {
						DialogUtils.showToastText(UserGuideActivity.this,
								R.string.save_success);
						// TODO (review) 为什么不用clearStackAndStartActivity方法？
						pushIntent(new Intent(UserGuideActivity.this,
								MainTabActivity.class));
						UserGuideActivity.this.finish();
					}

					@Override
					public String doInBackground() {
						IProfileService profileService = new ProfileService();
						try {
							profileService.guide(user, UserGuideActivity.this);
						} catch (ProfileException e) {
							return e.getMessage();
						}
						return null;
					}
				}, false).execute();

			}
		});
		init();
	}

}

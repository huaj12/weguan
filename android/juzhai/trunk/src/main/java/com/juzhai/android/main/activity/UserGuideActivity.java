/**
 * 
 */
package com.juzhai.android.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

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
		setGuide(true);
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.user_guide_title));
		finish = setRightFinishButton();
		finish.setEnabled(false);
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validation()) {
					new ProgressTask(UserGuideActivity.this,
							new TaskCallback() {

								@Override
								public void successCallback() {
									DialogUtils.showSuccessDialog(
											UserGuideActivity.this,
											R.string.save_success);
									// DialogUtils.showToastText(
									// UserGuideActivity.this,
									// R.string.save_success);
									clearStackAndStartActivity(new Intent(
											UserGuideActivity.this,
											MainTabActivity.class));
								}

								@Override
								public String doInBackground() {
									IProfileService profileService = new ProfileService();
									try {
										profileService.guide(user,
												UserGuideActivity.this);
									} catch (ProfileException e) {
										return e.getMessage();
									}
									return null;
								}
							}, false).execute();
				}
			}
		});
	}

	@Override
	protected int getNavContentViewLayout() {
		return R.layout.page_user_guide;
	}

}

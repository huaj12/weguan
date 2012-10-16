/**
 * 
 */
package com.juzhai.android.main.activity;

import org.apache.commons.lang.StringUtils;

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
		super.onCreate(savedInstanceState);
		setGuide(true);
		setNavContentView(R.layout.page_user_guide);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.user_guide_title));
		// TODO (done) finish按钮设置可以封装起来
		finish = setRightFinishButton();
		finish.setEnabled(false);
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO (done) 这里的逻辑是怎么样的？我觉得有问题啊
				if (StringUtils.isEmpty(user.getNickname())) {
					DialogUtils.showToastText(UserGuideActivity.this,
							R.string.nickname_is_null);
					return;
				}
				if (user.getBirthYear() <= 0) {
					DialogUtils.showToastText(UserGuideActivity.this,
							R.string.user_birth_day_is_null);
					return;
				}
				if (user.getCityId() <= 0) {
					DialogUtils.showToastText(UserGuideActivity.this,
							R.string.user_address_is_null);
					return;
				}
				if (user.getProfessionId() <= 0
						&& StringUtils.isNotEmpty(user.getProfession())) {
					DialogUtils.showToastText(UserGuideActivity.this,
							R.string.profession_name_is_null);
					return;
				}
				new ProgressTask(UserGuideActivity.this, new TaskCallback() {

					@Override
					public void successCallback() {
						DialogUtils.showToastText(UserGuideActivity.this,
								R.string.save_success);
						// TODO (done) 为什么不用clearStackAndStartActivity方法？
						clearStackAndStartActivity(new Intent(
								UserGuideActivity.this, MainTabActivity.class));
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

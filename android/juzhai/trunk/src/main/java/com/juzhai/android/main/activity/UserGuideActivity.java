/**
 * 
 */
package com.juzhai.android.main.activity;

import org.apache.commons.lang.StringUtils;

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
		init();
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.user_guide_title));
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
							if (e.getMessageId() > 0) {
								return UserGuideActivity.this.getResources()
										.getString(e.getMessageId());
							} else if (StringUtils.isNotEmpty(e.getMessage())) {
								return e.getMessage();
							}
						}
						return null;
					}
				}, false).execute();

			}
		});

	}

}

/**
 * 
 */
package com.juzhai.android.home.activity;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.juzhai.android.R;
import com.juzhai.android.core.task.ProgressTask;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.passport.exception.ProfileException;
import com.juzhai.android.passport.service.IProfileService;
import com.juzhai.android.passport.service.impl.ProfileService;

public class HomeSettingActivity extends SetUserInfoActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setmContext(HomeSettingActivity.this);
		setNavContentView(R.layout.page_home_setting);
		init();
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.home_setting_title));
		finish = (Button) getLayoutInflater().inflate(R.layout.button_finish,
				null);
		finish.setEnabled(false);
		getNavigationBar().setRightView(finish);

		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new ProgressTask(HomeSettingActivity.this, new TaskCallback() {

					@Override
					public void successCallback() {
						DialogUtils.showToastText(HomeSettingActivity.this,
								R.string.save_success);
						HomeSettingActivity.this.finish();
					}

					@Override
					public String doInBackground() {
						IProfileService profileService = new ProfileService();
						try {
							profileService.updateUser(user,
									HomeSettingActivity.this);
						} catch (ProfileException e) {
							if (e.getMessageId() > 0) {
								return HomeSettingActivity.this.getResources()
										.getString(e.getMessageId());
							} else if (StringUtils.isEmpty(e.getMessage())) {
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

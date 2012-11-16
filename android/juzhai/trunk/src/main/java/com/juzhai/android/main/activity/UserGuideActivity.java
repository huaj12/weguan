/**
 * 
 */
package com.juzhai.android.main.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.juzhai.android.passport.service.impl.PassportService;
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
				clickfinish();
			}
		});
		Button beginBtn = (Button) findViewById(R.id.begin_jz_btn);
		beginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clickfinish();
			}
		});

		Button logout = (Button) getLayoutInflater().inflate(
				R.layout.button_finish, null);
		logout.setText(R.string.logout);
		getNavigationBar().setLeftView(logout);
		logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(UserGuideActivity.this)
						.setMessage(R.string.tip_logout_confirm)
						.setNegativeButton(R.string.close,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.cancel();
									}
								})
						.setPositiveButton(R.string.logout,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.cancel();
										new AsyncTask<Void, Integer, Void>() {
											ProgressDialog progressDialog = null;

											@Override
											protected Void doInBackground(
													Void... params) {
												PassportService passportService = new PassportService();
												passportService
														.logout(UserGuideActivity.this);
												return null;
											}

											protected void onPostExecute(
													Void result) {
												if (null != progressDialog) {
													progressDialog.dismiss();
												}
												Intent intent = new Intent(
														UserGuideActivity.this,
														LoginAndRegisterActivity.class);
												clearStackAndStartActivity(intent);
											};

											protected void onPreExecute() {
												progressDialog = ProgressDialog
														.show(UserGuideActivity.this,
																getResources()
																		.getString(
																				R.string.tip_logouting),
																getResources()
																		.getString(
																				R.string.please_wait),
																true, false);
											};
										}.execute();
									}
								}).show();
			}
		});
	}

	@Override
	protected int getNavContentViewLayout() {
		return R.layout.page_user_guide;
	}

	public void clickfinish() {
		if (validation()) {
			new ProgressTask(UserGuideActivity.this, new TaskCallback() {
				@Override
				public void successCallback() {
					DialogUtils.showSuccessDialog(UserGuideActivity.this,
							R.string.save_success, 0);
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
	}

}

package com.juzhai.android.setting.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.table.widget.UITableView;
import com.juzhai.android.core.widget.list.table.widget.UITableView.ClickListener;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.activity.LoginActivity;
import com.juzhai.android.passport.service.impl.PassportService;

public class SettingListActivity extends NavigationActivity {

	private UITableView accountTableView;
	private UITableView appTableView;
	private UITableView cacheTableView;
	private UITableView logoutTableView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.setting_title));
		setNavContentView(R.layout.page_setting);
		accountTableView = (UITableView) findViewById(R.id.setting_account_table_view);
		createAccountList();
		accountTableView.commit();

		appTableView = (UITableView) findViewById(R.id.setting_app_table_view);
		createAppList();
		appTableView.commit();

		cacheTableView = (UITableView) findViewById(R.id.setting_cache_table_view);
		cacheAppList();
		cacheTableView.commit();

		logoutTableView = (UITableView) findViewById(R.id.setting_logout_table_view);
		logoutAppList();
		logoutTableView.commit();
	}

	private void createAccountList() {
		accountTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
			}
		});
		accountTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_profile));
		accountTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_authority));
	}

	private void createAppList() {
		appTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
			}
		});
		appTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_protocal));
		appTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_feedback));
		appTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_version));
	}

	private void cacheAppList() {
		cacheTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				new AlertDialog.Builder(SettingListActivity.this)
						.setMessage(R.string.tip_clear_cache_confirm)
						.setNegativeButton(R.string.close,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.cancel();
									}
								})
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.cancel();
										new AsyncTask<Void, Integer, Void>() {
											ProgressDialog progressDialog = null;

											@Override
											protected Void doInBackground(
													Void... params) {
												ImageViewLoader
														.getInstance(
																SettingListActivity.this)
														.clearCache();
												return null;
											}

											protected void onPostExecute(
													Void result) {
												if (progressDialog != null) {
													progressDialog.dismiss();
												}
											};

											protected void onPreExecute() {
												if (progressDialog != null) {
													progressDialog.show();
												} else {
													progressDialog = ProgressDialog
															.show(SettingListActivity.this,
																	getResources()
																			.getString(
																					R.string.tip_cache_clearing),
																	getResources()
																			.getString(
																					R.string.please_wait),
																	true, false);
												}
											};
										}.execute();
									}
								}).show();
			}
		});
		cacheTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_clear_cache));
	}

	private void logoutAppList() {
		logoutTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				new AlertDialog.Builder(SettingListActivity.this)
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
														.logout(SettingListActivity.this);
												return null;
											}

											protected void onPostExecute(
													Void result) {
												if (null != progressDialog) {
													progressDialog.dismiss();
												}
												Intent intent = new Intent(
														SettingListActivity.this,
														LoginActivity.class);
												clearStackAndStartActivity(intent);
											};

											protected void onPreExecute() {
												progressDialog = ProgressDialog
														.show(SettingListActivity.this,
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
		logoutTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_logout));
	}
}

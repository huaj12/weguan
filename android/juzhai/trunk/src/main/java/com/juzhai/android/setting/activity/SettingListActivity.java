package com.juzhai.android.setting.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.juzhai.android.R;
import com.juzhai.android.core.ApplicationContext;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.table.model.BasicItem.ItemType;
import com.juzhai.android.core.widget.list.table.widget.UITableView;
import com.juzhai.android.core.widget.list.table.widget.UITableView.ClickListener;
import com.juzhai.android.home.activity.HomeSettingActivity;
import com.juzhai.android.main.activity.LoginAndRegisterActivity;
import com.juzhai.android.main.activity.TabItemActivity;
import com.juzhai.android.passport.activity.AuthorizeBindActivity;
import com.juzhai.android.passport.activity.AuthorizeExpiredActivity;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.passport.service.impl.PassportService;
import com.umeng.update.UmengUpdateAgent;

public class SettingListActivity extends TabItemActivity {

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
		appTableView = (UITableView) findViewById(R.id.setting_app_table_view);
		cacheTableView = (UITableView) findViewById(R.id.setting_cache_table_view);
		logoutTableView = (UITableView) findViewById(R.id.setting_logout_table_view);

		createAppList();
		appTableView.commit();

		cacheAppList();
		cacheTableView.commit();

		logoutAppList();
		logoutTableView.commit();
	}

	@Override
	protected void onResume() {
		accountTableView.clear();
		createAccountList();
		accountTableView.commit();
		super.onResume();
	}

	private void createAccountList() {
		accountTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				if (index == 0) {
					Intent intent = new Intent(SettingListActivity.this,
							HomeSettingActivity.class);
					pushIntent(intent);
				} else if (index == 1) {
					User user = UserCacheManager.getUserCache(
							SettingListActivity.this).getUserInfo();
					if (user.hasTpExpired()) {
						pushIntent(new Intent(SettingListActivity.this,
								AuthorizeExpiredActivity.class));
					} else if (!user.hasTp()) {
						pushIntent(new Intent(SettingListActivity.this,
								AuthorizeBindActivity.class));
					}
				}
			}
		});
		accountTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_profile));

		String authorizeSubTitle = null;
		User user = UserCacheManager.getUserCache(SettingListActivity.this)
				.getUserInfo();
		if (user.hasTpExpired()) {
			authorizeSubTitle = getResources().getString(
					R.string.setting_authorize_expired);
		} else if (user.hasTp()) {
			String tpTitle = null;
			if (user.getTpId() == 6L) {
				tpTitle = getResources().getString(R.string.sina_title);
			} else if (user.getTpId() == 7L) {
				tpTitle = getResources().getString(R.string.db_title);
			} else if (user.getTpId() == 8L) {
				tpTitle = getResources().getString(R.string.qq_title);
			}
			authorizeSubTitle = getResources().getString(
					R.string.setting_authorize_has_bind, tpTitle);
		} else {
			authorizeSubTitle = getResources().getString(
					R.string.setting_authorize_unbind);
		}
		accountTableView.addBasicItem(
				getResources().getString(R.string.setting_cell_authorize),
				authorizeSubTitle, ItemType.HORIZONTAL);
	}

	private void createAppList() {
		appTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				if (index == 0) {
					Intent intent = new Intent(SettingListActivity.this,
							SettingProtocalActivity.class);
					pushIntent(intent);
				} else if (index == 1) {
					Intent intent = new Intent(SettingListActivity.this,
							SettingFeedbackActivity.class);
					pushIntent(intent);
				} else if (index == 2) {
					UmengUpdateAgent.update(SettingListActivity.this
							.getParent());
				}
			}
		});
		appTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_protocal));
		appTableView.addBasicItem(getResources().getString(
				R.string.setting_cell_feedback));
		appTableView.addBasicItem(
				getResources().getString(R.string.setting_cell_version),
				"v"
						+ ApplicationContext
								.getVersionName(SettingListActivity.this),
				ItemType.HORIZONTAL);
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
														LoginAndRegisterActivity.class);
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

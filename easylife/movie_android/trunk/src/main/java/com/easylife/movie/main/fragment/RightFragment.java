package com.easylife.movie.main.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.easylife.movie.R;
import com.easylife.movie.common.service.IShareService;
import com.easylife.movie.common.service.impl.ShareService;
import com.easylife.movie.core.ApplicationContext;
import com.easylife.movie.core.data.SharedPreferencesManager;
import com.easylife.movie.core.widget.button.SwitchButton;
import com.easylife.movie.core.widget.image.ImageViewLoader;
import com.easylife.movie.setting.activity.HistoryActivity;
import com.easylife.movie.setting.activity.InterestActivity;
import com.easylife.movie.setting.adapter.SettingAdapter;

public class RightFragment extends Fragment {
	private Context context;
	private ListView settingListView;

	public RightFragment() {
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_right, null);
		settingListView = (ListView) view.findViewById(R.id.setting_list_view);
		TextView verView = (TextView) view.findViewById(R.id.ver);
		ApplicationContext applicationContext = (ApplicationContext) context
				.getApplicationContext();
		verView.setText("v" + applicationContext.getVersionName(context));
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		settingListView.setAdapter(new SettingAdapter(context));
		settingListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int location, long arg3) {
				switch (location) {
				case 0:
					startActivity(new Intent(context, HistoryActivity.class));
					break;
				case 1:
					startActivity(new Intent(context, InterestActivity.class));
					break;
				case 2:
					new AlertDialog.Builder(context)
							.setMessage(R.string.tip_clear_cache_confirm)
							.setNegativeButton(R.string.close,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											dialog.cancel();
										}
									})
							.setPositiveButton(R.string.ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											dialog.cancel();
											new AsyncTask<Void, Integer, Void>() {
												ProgressDialog progressDialog = null;

												@Override
												protected Void doInBackground(
														Void... params) {
													ImageViewLoader
															.getInstance(
																	context)
															.clearCache();
													return null;
												}

												protected void onPostExecute(
														Void result) {
													if (progressDialog != null) {
														progressDialog
																.dismiss();
													}
												};

												protected void onPreExecute() {
													if (progressDialog != null) {
														progressDialog.show();
													} else {
														progressDialog = ProgressDialog
																.show(context,
																		getResources()
																				.getString(
																						R.string.tip_cache_clearing),
																		getResources()
																				.getString(
																						R.string.please_wait),
																		true,
																		false);
													}
												};
											}.execute();
										}
									}).show();
					break;
				case 3:
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("message/rfc822");
					i.putExtra(Intent.EXTRA_EMAIL, new String[] { context
							.getResources().getString(R.string.official_mail) });
					i.putExtra(Intent.EXTRA_SUBJECT, context.getResources()
							.getString(R.string.setting_proposal_title));
					startActivity(Intent.createChooser(i, context
							.getResources().getString(R.string.send_email)));
					break;
				case 4:
					IShareService shareService = new ShareService();
					shareService.openSharePop(
							context.getResources().getString(
									R.string.share_friend_text), context, null);
					break;
				case 5:
					final SharedPreferencesManager manager = new SharedPreferencesManager(
							context);
					SwitchButton switchBtn = (SwitchButton) view
							.findViewById(R.id.switchButton);
					// 默认是提醒
					switchBtn.setChecked(!manager.getBoolean(
							SharedPreferencesManager.HAS_NOTIFICATION, true));
					manager.commit(SharedPreferencesManager.HAS_NOTIFICATION,
							switchBtn.isChecked());
					break;
				default:
					break;
				}
			}
		});
	}
}

package com.easylife.weather.main.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.easylife.weather.R;
import com.easylife.weather.common.service.IShareService;
import com.easylife.weather.common.service.impl.ShareService;
import com.easylife.weather.core.activity.ActivityCode;
import com.easylife.weather.core.utils.UIUtil;
import com.easylife.weather.main.activity.CityActivity;
import com.easylife.weather.main.activity.PeelsActivity;
import com.easylife.weather.main.activity.RemindTimeActivity;
import com.easylife.weather.main.adapter.RemindListAdapter;
import com.easylife.weather.main.adapter.SettingListAdapter;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class RightFragment extends Fragment {
	private Context context;
	private ListView settingListView = null;
	private ListView remindListView = null;
	private LayoutInflater inflater;
	private UserConfig user;
	public BaseAdapter settingAdapter;

	public RightFragment() {
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_right, null);
		settingListView = (ListView) view.findViewById(R.id.setting_list_view);
		remindListView = (ListView) view.findViewById(R.id.remind_list_view);
		this.inflater = inflater;
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		settingAdapter = new SettingListAdapter(context);
		settingListView.setAdapter(settingAdapter);
		UIUtil.setListViewHeightBasedOnChildren(settingListView);
		settingListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				switch (index) {
				case 0:
					pushIntentForResult(
							new Intent(context, CityActivity.class),
							ActivityCode.RequestCode.CLEAR_REQUEST_CODE);
					break;
				case 1:
					user = UserConfigManager.getUserConfig(context);
					Intent intent = new Intent(context,
							RemindTimeActivity.class);
					intent.putExtra("hour", user.getHourStr());
					pushIntentForResult(intent,
							ActivityCode.RequestCode.HOUR_REQUEST_CODE);
					break;
				case 2:
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("message/rfc822");
					i.putExtra(Intent.EXTRA_EMAIL, new String[] { context
							.getResources().getString(R.string.official_mail) });
					i.putExtra(Intent.EXTRA_SUBJECT, context.getResources()
							.getString(R.string.proposal));
					startActivity(Intent.createChooser(i, context
							.getResources().getString(R.string.send_email)));
					break;
				case 3:
					IShareService shareService = new ShareService();
					shareService.openSharePop(
							context.getResources().getString(
									R.string.share_friend_text), context);
					break;
				case 4:
					pushIntentForResult(
							new Intent(context, PeelsActivity.class),
							ActivityCode.RequestCode.CLEAR_REQUEST_CODE);
					break;
				case 5:
					UmengUpdateAgent.update(context);
					UmengUpdateAgent.setUpdateAutoPopup(false);
					UmengUpdateAgent
							.setUpdateListener(new UmengUpdateListener() {
								@Override
								public void onUpdateReturned(int updateStatus,
										UpdateResponse updateInfo) {
									switch (updateStatus) {
									case 0: // has update
										UmengUpdateAgent.showUpdateDialog(
												context, updateInfo);
										break;
									case 1: // has no update
										Toast.makeText(context,
												R.string.no_update_ver,
												Toast.LENGTH_SHORT).show();
										break;
									case 2: // none wifi
										Toast.makeText(context,
												R.string.no_wifi,
												Toast.LENGTH_SHORT).show();
										break;
									case 3: // time out
										Toast.makeText(context,
												R.string.no_network,
												Toast.LENGTH_SHORT).show();
										break;
									}
								}
							});
					break;
				default:
					break;
				}
			}
		});
		remindListView.setAdapter(new RemindListAdapter(context));
		UIUtil.setListViewHeightBasedOnChildren(remindListView);
	}

	public void pushIntentForResult(Intent intent, int requestCode) {
		startActivityForResult(intent, requestCode);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		Activity activity = (Activity) context;
		activity.startActivityForResult(intent, requestCode);
	}

}

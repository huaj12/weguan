package com.easylife.movie.setting.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylife.movie.R;
import com.easylife.movie.core.Constants;
import com.easylife.movie.core.data.SharedPreferencesManager;
import com.easylife.movie.core.widget.button.SwitchButton;

public class SettingAdapter extends BaseAdapter {
	protected Context context;
	protected LayoutInflater inflater;

	public SettingAdapter(Context mContext) {
		this.context = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.item_setting, null);
		TextView textView = (TextView) convertView.findViewById(R.id.item_text);
		ImageView iconView = (ImageView) convertView
				.findViewById(R.id.item_icon);
		switch (position) {
		case 0:
			textView.setText(R.string.history);
			iconView.setBackgroundResource(R.drawable.history);
			break;
		case 1:
			textView.setText(R.string.interest);
			iconView.setBackgroundResource(R.drawable.fav);
			break;
		case 2:
			textView.setText(R.string.clean_cache);
			iconView.setBackgroundResource(R.drawable.cache);
			break;
		case 3:
			textView.setText(R.string.feedback);
			iconView.setBackgroundResource(R.drawable.feedback);
			break;
		case 4:
			textView.setText(R.string.share_title);
			iconView.setBackgroundResource(R.drawable.share);
			break;
		case 5:
			final SharedPreferencesManager manager = new SharedPreferencesManager(
					context);
			iconView.setBackgroundResource(R.drawable.notification);
			textView.setText(R.string.notification_update);
			SwitchButton switchBtn = (SwitchButton) convertView
					.findViewById(R.id.switchButton);
			TextView strIcon = (TextView) convertView
					.findViewById(R.id.str_icon);
			switchBtn.setVisibility(View.VISIBLE);
			strIcon.setVisibility(View.GONE);
			// 默认是提醒
			switchBtn.setChecked(manager.getBoolean(
					SharedPreferencesManager.HAS_NOTIFICATION, true));
			switchBtn
					.setOnChangedListener(new SwitchButton.OnChangedListener() {

						@Override
						public void OnChanged(boolean checkState) {
							AlarmManager am = (AlarmManager) context
									.getSystemService(Context.ALARM_SERVICE);
							PendingIntent sender = PendingIntent.getBroadcast(
									context, 0, new Intent(
											Constants.ALARM_INTENT),
									PendingIntent.FLAG_CANCEL_CURRENT);
							if (checkState) {
								am.setRepeating(
										AlarmManager.RTC_WAKEUP,
										System.currentTimeMillis()
												+ Constants.NO_LOGIN_NOTICE_PERIOD,
										Constants.NOTICE_PERIOD, sender);
							} else {
								am.cancel(sender);
							}
							manager.commit(
									SharedPreferencesManager.HAS_NOTIFICATION,
									checkState);
						}
					});
			break;
		}
		return convertView;
	}

}

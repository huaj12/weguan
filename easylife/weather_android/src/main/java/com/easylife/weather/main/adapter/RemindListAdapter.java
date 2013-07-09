package com.easylife.weather.main.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easylife.weather.R;
import com.easylife.weather.core.Constants;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.core.widget.button.SwitchButton;
import com.easylife.weather.core.widget.button.SwitchButton.OnChangedListener;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;
import com.easylife.weather.passport.service.IPassportService;
import com.easylife.weather.passport.service.impl.PassPortService;

public class RemindListAdapter extends BaseAdapter {
	protected Context context;
	protected LayoutInflater inflater;
	private IPassportService passportService = new PassPortService();

	public RemindListAdapter(Context mContext) {
		this.context = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return 4;
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
		final UserConfig user = UserConfigManager.getUserConfig(context);
		convertView = inflater.inflate(R.layout.item_remind, null);
		TextView textView = (TextView) convertView.findViewById(R.id.item_text);
		SwitchButton button = (SwitchButton) convertView
				.findViewById(R.id.switchButton);
		switch (position) {
		case 0:
			textView.setText(context.getResources().getString(
					R.string.remind_rain));
			button.setChecked(user.isRemindRain());
			button.setOnChangedListener(new OnChangedListener() {

				@Override
				public void OnChanged(boolean checkState) {
					user.setRemindRain(checkState);
					updateUser(user);
				}
			});
			break;
		case 1:
			textView.setText(context.getResources().getString(
					R.string.remind_hot));
			button.setChecked(user.isRemindHot());
			button.setOnChangedListener(new OnChangedListener() {

				@Override
				public void OnChanged(boolean checkState) {
					user.setRemindHot(checkState);
					updateUser(user);
				}
			});
			break;
		case 2:
			textView.setText(context.getResources().getString(
					R.string.remind_cooling));
			button.setChecked(user.isRemindCooling());
			button.setOnChangedListener(new OnChangedListener() {

				@Override
				public void OnChanged(boolean checkState) {
					user.setRemindCooling(checkState);
					updateUser(user);
				}
			});
			break;
		case 3:
			textView.setText(context.getResources().getString(
					R.string.remind_wind));
			button.setChecked(user.isRemindWind());
			button.setOnChangedListener(new OnChangedListener() {

				@Override
				public void OnChanged(boolean checkState) {
					user.setRemindWind(checkState);
					updateUser(user);
				}
			});
			break;
		default:
			break;
		}
		return convertView;
	}

	private void updateUser(final UserConfig user) {
		try {
			passportService.updateUserConfig(user, context);
		} catch (WeatherException e) {
		}
		if (!user.isRemindCooling() && !user.isRemindRain()
				&& !user.isRemindWind() && !user.isRemindHot()) {
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			PendingIntent sender = PendingIntent.getBroadcast(context, 0,
					new Intent(Constants.ALARM_INTENT),
					PendingIntent.FLAG_CANCEL_CURRENT);
			am.cancel(sender);
		} else {
			WeatherUtils.setRepeating(context);
		}
	}

}

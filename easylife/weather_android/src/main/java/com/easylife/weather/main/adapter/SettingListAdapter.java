package com.easylife.weather.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylife.weather.R;
import com.easylife.weather.core.ApplicationContext;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;

public class SettingListAdapter extends BaseAdapter {
	protected Context context;
	protected LayoutInflater inflater;

	public SettingListAdapter(Context mContext) {
		this.context = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return 7;
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
		UserConfig user = UserConfigManager.getUserConfig(context);
		convertView = inflater.inflate(R.layout.item_setting, null);
		TextView textView = (TextView) convertView.findViewById(R.id.item_text);
		TextView valueView = (TextView) convertView
				.findViewById(R.id.item_value);
		TextView iconView = (TextView) convertView.findViewById(R.id.item_icon);
		ImageView imgView = (ImageView) convertView.findViewById(R.id.item_img);
		imgView.setVisibility(View.GONE);
		iconView.setVisibility(View.VISIBLE);
		valueView.setVisibility(View.VISIBLE);
		switch (position) {
		case 0:
			getSelectCityView(textView, valueView);
			break;
		case 1:
			textView.setText(context.getResources().getString(
					R.string.setting_remind_title));
			valueView.setText(user.getTimeStr());
			break;
		case 2:
			textView.setText(context.getResources().getString(
					R.string.setting_proposal_title));
			break;
		case 3:
			textView.setText(context.getResources().getString(
					R.string.setting_share_title));
			break;
		case 4:
			textView.setText(context.getResources().getString(
					R.string.setting_peels_title));
			break;
		case 5:
			textView.setText(context.getResources().getString(
					R.string.setting_version_title));
			ApplicationContext applicationContext = (ApplicationContext) context
					.getApplicationContext();
			valueView.setText("v" + applicationContext.getVersionName(context));
			break;
		case 6:
			textView.setText(context.getResources().getString(
					R.string.offer_wall));
			imgView.setBackgroundResource(R.drawable.new_pic);
			imgView.setVisibility(View.VISIBLE);
			iconView.setVisibility(View.GONE);
			valueView.setVisibility(View.GONE);
			break;
		default:
			break;
		}
		return convertView;
	}

	private void getSelectCityView(TextView textView, TextView valueView) {
		textView.setText(context.getResources().getString(
				R.string.setting_city_title));
		valueView.setText(UserConfigManager.getCityName(context));

	}

}

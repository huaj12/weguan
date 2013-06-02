package com.easylife.weather.main.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.easylife.weather.R;
import com.easylife.weather.core.activity.BaseActivity;
import com.easylife.weather.main.activity.MainActivity;
import com.easylife.weather.main.data.WeatherDataManager;

public class PeelsAdapter extends BaseAdapter {
	protected Context mContext;
	protected LayoutInflater inflater;
	private List<String> colors;
	private ImageView selectedView;

	public PeelsAdapter(List<String> colors, Context mContext) {
		this.mContext = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.colors = colors;
	}

	@Override
	public int getCount() {
		return colors.size();
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_peels, null);
			holder = new ViewHolder();
			holder.iconView = (ImageView) convertView
					.findViewById(R.id.item_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String color = colors.get(position);
		ImageView iconView = holder.iconView;
		final int colorInt = Color.argb(255,
				Integer.parseInt(color.substring(0, 2), 16),
				Integer.parseInt(color.substring(2, 4), 16),
				Integer.parseInt(color.substring(4, 6), 16));
		convertView.setBackgroundColor(colorInt);
		if (WeatherDataManager.getBackgroundColor(mContext) == colorInt) {
			iconView.setVisibility(View.VISIBLE);
			selectedView = iconView;
		} else {
			iconView.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ImageView clickIconView = (ImageView) v
						.findViewById(R.id.item_icon);
				clickIconView.setVisibility(View.VISIBLE);
				if (selectedView != null) {
					selectedView.setVisibility(View.GONE);
				}
				WeatherDataManager.setBackgroundColor(mContext, colorInt);
				BaseActivity activity = (BaseActivity) mContext;
				activity.clearStackAndStartActivity(new Intent(mContext,
						MainActivity.class));
			}
		});
		return convertView;
	}

	private class ViewHolder {
		public ImageView iconView;
	}
}

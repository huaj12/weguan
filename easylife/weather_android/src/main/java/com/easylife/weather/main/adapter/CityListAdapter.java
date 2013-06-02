package com.easylife.weather.main.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easylife.weather.R;

public class CityListAdapter extends BaseAdapter {
	protected Context mContext;
	protected LayoutInflater inflater;
	private List<String> citys;

	public CityListAdapter(List<String> citys, Context mContext) {
		this.mContext = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Collections.reverse(citys);
		this.citys = citys;
	}

	@Override
	public int getCount() {
		return citys.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return citys.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_city, null);
			holder = new ViewHolder();
			holder.cityNameView = (TextView) convertView
					.findViewById(R.id.city_name_view);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TextView cityNameView = holder.cityNameView;
		String cityName = citys.get(position);
		cityNameView.setText(cityName);
		return convertView;
	}

	private class ViewHolder {
		public TextView cityNameView;
	}
}

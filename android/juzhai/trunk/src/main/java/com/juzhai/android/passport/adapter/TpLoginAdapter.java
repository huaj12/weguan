package com.juzhai.android.passport.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;

public class TpLoginAdapter extends BaseAdapter {

	private LayoutInflater inflater;

	public TpLoginAdapter(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.tp_login_list_item, null);
			ImageView logo = (ImageView) convertView
					.findViewById(R.id.tp_image_logo);
			TextView title = (TextView) convertView
					.findViewById(R.id.tp_item_title);
			if (position == 0) {
				convertView.setTag(6);
				logo.setImageResource(R.drawable.sina_login_icon);
				title.setText(R.string.sina_login_title);
			} else if (position == 1) {
				convertView.setTag(8);
				logo.setImageResource(R.drawable.qq_login_icon);
				title.setText(R.string.qq_login_title);
			} else if (position == 2) {
				convertView.setTag(7);
				logo.setImageResource(R.drawable.db_login_icon);
				title.setText(R.string.db_login_title);
			}
		}
		return convertView;
	}
}

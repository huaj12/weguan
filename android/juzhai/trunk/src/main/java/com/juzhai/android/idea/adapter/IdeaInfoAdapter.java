package com.juzhai.android.idea.adapter;

import org.apache.commons.lang.StringUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.idea.model.Idea;

public class IdeaInfoAdapter extends BaseAdapter {
	private Idea idea;
	private LayoutInflater inflater;

	public IdeaInfoAdapter(Idea idea, LayoutInflater inflater) {
		this.idea = idea;
		this.inflater = inflater;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_idea_info, null);
		}
		ImageView logo = (ImageView) convertView
				.findViewById(R.id.idea_info_image);
		TextView text = (TextView) convertView
				.findViewById(R.id.idea_info_text);
		if (position == 0) {
			if (StringUtils.isNotEmpty(idea.getCityName())
					&& StringUtils.isNotEmpty(idea.getTownName())
					&& StringUtils.isNotEmpty(idea.getPlace())) {
				logo.setImageResource(R.drawable.position_icon_link);
				StringBuffer sbStr = new StringBuffer();
				if (StringUtils.isNotEmpty(idea.getCityName())) {
					sbStr.append(idea.getCityName());
					sbStr.append(" ");
				}
				if (StringUtils.isNotEmpty(idea.getTownName())) {
					sbStr.append(idea.getTownName());
					sbStr.append(" ");
				}
				sbStr.append(idea.getPlace());
				text.setText(sbStr.toString());
			}
		} else if (position == 1) {
			if (StringUtils.isNotEmpty(idea.getStartTime())
					&& StringUtils.isNotEmpty(idea.getEndTime())) {
				logo.setImageResource(R.drawable.time_icon_link);
				text.setText(idea.getStartTime() + " " + idea.getEndTime());
			}
		} else if (position == 2) {
			if (idea.getCharge() != null) {
				logo.setImageResource(R.drawable.icon_price);
				text.setText(idea.getCharge() + "元");
			}
		}
		return convertView;
	}

}

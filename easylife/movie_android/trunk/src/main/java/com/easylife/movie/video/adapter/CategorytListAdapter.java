package com.easylife.movie.video.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylife.movie.R;
import com.easylife.movie.common.service.CommonData;
import com.easylife.movie.video.model.Category;

public class CategorytListAdapter extends BaseAdapter {
	protected Context context;
	protected LayoutInflater inflater;
	private List<Category> datas;

	public CategorytListAdapter(Context mContext) {
		this.context = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		datas = CommonData.getCategorys(context);
	}

	@Override
	public int getCount() {
		return datas.size();
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
		convertView = inflater.inflate(R.layout.item_category, null);
		Category category =datas.get(position);
		TextView textView = (TextView) convertView.findViewById(R.id.item_text);
		ImageView iconView = (ImageView) convertView
				.findViewById(R.id.item_icon);
		textView.setText(category.getName());
		int iconId = context.getResources().getIdentifier(
				"tag_icon" + category.getCategoryId(), "drawable",
				context.getPackageName());
		iconView.setBackgroundResource(iconId);
		return convertView;
	}

}

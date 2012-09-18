package com.juzhai.android.idea.adapter;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.model.IdeaListAndPager;

public class IdeaListAdapter extends BaseAdapter {
	private IdeaListAndPager ideaResult;
	private Context mContext = null;
	private String name = null;
	private LayoutInflater inflater = null;

	public IdeaListAdapter(IdeaListAndPager ideaResult, Context mContext,
			String name) {
		this.ideaResult = ideaResult;
		this.mContext = mContext;
		this.name = name;
	}

	@Override
	public int getCount() {
		return ideaResult.getList().size();
	}

	@Override
	public Object getItem(int arg0) {
		return ideaResult.getList().get(arg0);
	}

	@Override
	public long getItemId(int position) {
		Idea idea = ideaResult.getList().get(position);
		return idea.getIdeaId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			if (inflater == null) {
				inflater = (LayoutInflater) mContext.getSystemService(name);
			}
			Idea idea = ideaResult.getList().get(position);
			convertView = inflater.inflate(R.layout.idea_list_item, null);
			final TextView contentText = (TextView) convertView
					.findViewById(R.id.idea_content);
			contentText.setText(idea.getContent());
			TextView textView = (TextView) convertView
					.findViewById(R.id.idea_use_count_txet);
			textView.setText("已有" + idea.getUseCount() + "想去");

			Button btn = (Button) convertView.findViewById(R.id.idea_want_btn);
			if (idea.isHasUsed()) {
				btn.setBackgroundResource(R.drawable.i_want_go_btn_done);
				btn.setText(convertView.getResources().getString(
						R.string.want_done));
			} else {
				btn.setText(convertView.getResources().getString(
						R.string.i_want));
				btn.setBackgroundResource(R.drawable.i_want_selector_button);
			}
			final ImageView imageView = (ImageView) convertView
					.findViewById(R.id.idea_image);
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
			if (StringUtils.isNotEmpty(idea.getBigPic())) {
				nid.fetchImage(idea.getBigPic().replaceAll("test.", ""), 0,
						imageView, new ImageLoaderCallback() {
							@Override
							public void imageLoaderFinish(Bitmap bitmap) {
								imageView.setScaleType(ScaleType.CENTER_INSIDE);
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(bitmap, 10));
								contentText
										.setBackgroundResource(R.drawable.good_idea_item_txt_infor_bg);
							}
						});
			}

		}
		return convertView;
	}
}

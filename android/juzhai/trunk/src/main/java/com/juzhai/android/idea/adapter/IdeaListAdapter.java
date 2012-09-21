package com.juzhai.android.idea.adapter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.listener.BaseListener;
import com.juzhai.android.core.listener.ListenerSuccessCallBack;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.activity.IdeaDetailActivity;
import com.juzhai.android.idea.activity.IdeaListActivity;
import com.juzhai.android.idea.activity.IdeaUsersActivity;
import com.juzhai.android.idea.adapter.viewholder.IdeaListViewHolder;
import com.juzhai.android.idea.model.Idea;

public class IdeaListAdapter extends PageAdapter<Idea> {

	public IdeaListAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IdeaListViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_idea_list, null);
			holder = new IdeaListViewHolder();
			holder.setTextView((TextView) convertView
					.findViewById(R.id.idea_use_count_txet));
			holder.setImageView((ImageView) convertView
					.findViewById(R.id.idea_image));
			holder.setContentText((TextView) convertView
					.findViewById(R.id.idea_content));
			holder.setWantbtn((Button) convertView
					.findViewById(R.id.idea_want_btn));
			convertView.setTag(holder);
		} else {
			holder = (IdeaListViewHolder) convertView.getTag();
		}

		final Idea idea = data.getDatas().get(position);
		final TextView contentText = holder.getContentText();
		contentText.setTextColor(android.graphics.Color.BLACK);
		contentText.setBackgroundDrawable(null);
		final ImageView imageView = holder.getImageView();
		final Button btn = holder.getWantbtn();
		contentText.setText(TextTruncateUtil.truncate(idea.getContent(),
				Validation.IDEA_CONTENT_MAX_LENGTH, "..."));
		TextView textView = holder.getTextView();
		textView.setText(mContext.getResources().getString(
				R.string.use_count_begin)
				+ idea.getUseCount()
				+ mContext.getResources().getString(R.string.use_count_end));
		if (idea.isHasUsed()) {
			btn.setBackgroundResource(R.drawable.i_want_go_btn_done);
			btn.setText(convertView.getResources()
					.getString(R.string.want_done));
		} else {
			btn.setText(convertView.getResources().getString(R.string.i_want));
			btn.setBackgroundResource(R.drawable.i_want_selector_button);
			// 我想去
			Map<String, String> values = new HashMap<String, String>();
			values.put("ideaId", String.valueOf(idea.getIdeaId()));
			btn.setOnClickListener(new BaseListener("post/sendPost", mContext,
					values, new ListenerSuccessCallBack() {
						@Override
						public void callback() {
							btn.setOnClickListener(null);
							btn.setBackgroundResource(R.drawable.i_want_go_btn_done);
							btn.setText(mContext.getResources().getString(
									R.string.want_done));
						}
					}));
		}
		ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
		if (StringUtils.isNotEmpty(idea.getBigPic())) {
			nid.fetchImage(idea.getBigPic().replaceAll("test.", ""),
					R.drawable.good_idea_list_pic_none_icon, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(mContext, 262),
										UIUtil.dip2px(mContext, 180));
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 15));
								contentText
										.setBackgroundResource(R.drawable.good_idea_item_txt_infor_bg);
								contentText.setTextColor(mContext
										.getResources().getColor(
												android.R.color.white));
							}
						}
					});
		}
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, IdeaDetailActivity.class);
				intent.putExtra("idea", idea);
				((IdeaListActivity) mContext).pushIntent(intent);
			}
		});
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, IdeaUsersActivity.class);
				intent.putExtra("idea", idea);
				NavigationActivity activity = (IdeaListActivity) mContext;
				activity.pushIntent(intent);

			}
		});
		return convertView;
	}
}

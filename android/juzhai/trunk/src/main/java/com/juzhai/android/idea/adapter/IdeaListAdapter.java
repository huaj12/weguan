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
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.task.TaskSuccessCallBack;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.activity.IdeaDetailActivity;
import com.juzhai.android.idea.activity.IdeaListActivity;
import com.juzhai.android.idea.activity.IdeaUsersActivity;
import com.juzhai.android.idea.model.Idea;

public class IdeaListAdapter extends PageAdapter<Idea> {

	public IdeaListAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_idea_list, null);
			holder = new ViewHolder();
			holder.userCountTextView = (TextView) convertView
					.findViewById(R.id.idea_use_count_txet);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.idea_image);
			holder.contentTextView = (TextView) convertView
					.findViewById(R.id.idea_content);
			holder.wantButton = (Button) convertView
					.findViewById(R.id.idea_want_btn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Idea idea = data.getDatas().get(position);

		final TextView contentTextView = holder.contentTextView;
		contentTextView.setTextColor(android.graphics.Color.BLACK);
		contentTextView.setBackgroundDrawable(null);
		contentTextView.setText(TextTruncateUtil.truncate(idea.getContent(),
				60, "..."));

		TextView userCountTextView = holder.userCountTextView;
		if (idea.getUseCount() != null && idea.getUseCount() > 0) {
			userCountTextView
					.setText(mContext.getResources().getString(
							R.string.use_count_begin)
							+ idea.getUseCount()
							+ mContext.getResources().getString(
									R.string.use_count_end));
			userCountTextView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,
							IdeaUsersActivity.class);
					intent.putExtra("idea", idea);
					NavigationActivity activity = (IdeaListActivity) mContext;
					activity.pushIntent(intent);
				}
			});
		}

		final Button wantButton = holder.wantButton;
		if (idea.isHasUsed()) {
			wantButton.setEnabled(false);
			wantButton.setText(mContext.getResources().getString(
					R.string.want_done));
			wantButton.setTextColor(mContext.getResources().getColor(
					R.color.idea_want_done));

		} else {
			wantButton.setEnabled(true);
			wantButton.setText(convertView.getResources().getString(
					R.string.i_want));
			wantButton.setTextColor(mContext.getResources().getColor(
					R.color.idea_want));
			// 我想去
			Map<String, String> values = new HashMap<String, String>();
			values.put("ideaId", String.valueOf(idea.getIdeaId()));
			wantButton.setOnClickListener(new SimpleClickListener(
					"post/sendPost", mContext, values,
					new TaskSuccessCallBack() {
						@Override
						public void callback() {
							wantButton.setEnabled(false);
							wantButton.setText(mContext.getResources()
									.getString(R.string.want_done));
							wantButton.setTextColor(mContext.getResources()
									.getColor(R.color.idea_want_done));
							idea.setHasUsed(true);
						}
					}));
		}

		final ImageView imageView = holder.imageView;
		if (StringUtils.isNotEmpty(idea.getBigPic())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
			nid.fetchImage(JzUtils.getImageUrl(idea.getBigPic()),
					R.drawable.good_idea_list_pic_none_icon, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								// TODO (review) 高和宽写死的？imageView没有高和宽的属性？
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(mContext, 262),
										UIUtil.dip2px(mContext, 180));
								imageView.setImageBitmap(zoomBitmap);
								contentTextView
										.setBackgroundResource(R.drawable.good_idea_item_txt_infor_bg);
								contentTextView.setTextColor(mContext
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
				intent.putExtra("position", position);
				((IdeaListActivity) mContext).pushIntentForResult(intent,
						ActivityCode.RequestCode.IDEA_LIST_REQUEST_CODE);
			}
		});

		return convertView;
	}

	private class ViewHolder {
		public TextView contentTextView;
		public ImageView imageView;
		public Button wantButton;
		public TextView userCountTextView;
	}
}

package com.juzhai.android.idea.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.activity.IdeaDetailActivity;
import com.juzhai.android.idea.activity.IdeaListActivity;
import com.juzhai.android.idea.activity.IdeaUsersActivity;
import com.juzhai.android.idea.model.Idea;
import com.umeng.analytics.MobclickAgent;

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
			holder.ideaLayout = (LinearLayout) convertView
					.findViewById(R.id.idea_layout);
			holder.imageLayout = (RelativeLayout) convertView
					.findViewById(R.id.idea_image_bg_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Idea idea = data.getDatas().get(position);
		final TextView contentTextView = holder.contentTextView;
		LinearLayout ideaLayout = holder.ideaLayout;
		contentTextView.setTextColor(android.graphics.Color.BLACK);
		contentTextView.setBackgroundDrawable(null);
		contentTextView.setText(TextTruncateUtil.truncate(idea.getContent(),
				60, "..."));
		final RelativeLayout imageLayout = holder.imageLayout;

		TextView userCountTextView = holder.userCountTextView;
		if (idea.getUseCount() != null && idea.getUseCount() > 0) {
			userCountTextView.setText(idea.getUseCount()
					+ mContext.getResources().getString(R.string.use_count));
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
		} else {
			userCountTextView.setText(null);
		}

		final Button wantButton = holder.wantButton;
		if (idea.isHasUsed()) {
			wantButton.setEnabled(false);
			wantButton.setText(mContext.getResources().getString(
					R.string.want_done));
			wantButton.setTextColor(mContext.getResources().getColor(
					R.color.button_done_color));

		} else {
			wantButton.setEnabled(true);
			wantButton.setText(convertView.getResources().getString(
					R.string.i_want));
			wantButton.setTextColor(mContext.getResources().getColor(
					R.color.button_link_color));
			// 我想去
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("ideaId", idea.getIdeaId());
			wantButton.setOnClickListener(new SimpleClickListener(
					"post/sendPost", mContext, values, false,
					new TaskCallback() {
						@Override
						public void successCallback() {
							DialogUtils.showSuccessDialog(mContext,
									R.string.i_want_success_text);
							wantButton.setEnabled(false);
							wantButton.setText(mContext.getResources()
									.getString(R.string.want_done));
							wantButton.setTextColor(mContext.getResources()
									.getColor(R.color.button_done_color));
							idea.setHasUsed(true);
							MobclickAgent.onEvent(mContext,
									UmengEvent.SEND_IDEA);
						}

						@Override
						public String doInBackground() {
							return null;
						}
					}));
		}

		final ImageView imageView = holder.imageView;
		if (StringUtils.hasText(idea.getBigPic())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
			nid.fetchImage(JzUtils.getImageUrl(idea.getBigPic()),
					R.drawable.good_idea_list_pic_none_icon, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								// TODO (review) 高和宽写死的？imageView没有高和宽的属性？
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, 262, 180, mContext);
								imageLayout.getLayoutParams().width = zoomBitmap
										.getWidth();
								imageLayout.getLayoutParams().height = zoomBitmap
										.getHeight();
								imageView.setImageBitmap(zoomBitmap);
								contentTextView
										.setBackgroundResource(R.drawable.good_idea_item_txt_infor_bg);
								contentTextView.setTextColor(Color.WHITE);
							}
						}
					});
		}

		ideaLayout.setOnClickListener(new OnClickListener() {

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
		public LinearLayout ideaLayout;
		public RelativeLayout imageLayout;
	}
}

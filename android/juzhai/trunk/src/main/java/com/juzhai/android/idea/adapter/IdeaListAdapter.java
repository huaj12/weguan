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
import com.juzhai.android.core.listener.ListenerSuccessCallBack;
import com.juzhai.android.core.listener.SimpleClickListener;
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
	public View getView(int position, View convertView, ViewGroup parent) {
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
		// TODO (done) Validation不适合
		contentTextView.setText(TextTruncateUtil.truncate(idea.getContent(),
				60, "..."));

		TextView userCountTextView = holder.userCountTextView;
		userCountTextView.setText(mContext.getResources().getString(
				R.string.use_count_begin)
				+ idea.getUseCount()
				+ mContext.getResources().getString(R.string.use_count_end));
		// TODO (done) 按钮按下没有效果？
		userCountTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, IdeaUsersActivity.class);
				intent.putExtra("idea", idea);
				NavigationActivity activity = (IdeaListActivity) mContext;
				activity.pushIntent(intent);
			}
		});

		final Button wantButton = holder.wantButton;
		if (idea.isHasUsed()) {
			// TODO (done) 按钮enable false的状态来使用背景资源
			wantButton.setEnabled(false);
			wantButton.setText(mContext.getResources().getString(
					R.string.want_done));
		} else {
			wantButton.setText(convertView.getResources().getString(
					R.string.i_want));
			wantButton.setBackgroundResource(R.drawable.i_want_selector_button);
			// 我想去
			Map<String, String> values = new HashMap<String, String>();
			values.put("ideaId", String.valueOf(idea.getIdeaId()));
			wantButton.setOnClickListener(new SimpleClickListener(
					"post/sendPost", mContext, values,
					new ListenerSuccessCallBack() {
						@Override
						public void callback() {
							// TODO (done) 通过改变按钮状态来实现样式修改和不可点
							wantButton.setEnabled(false);
							wantButton.setText(mContext.getResources()
									.getString(R.string.want_done));
						}
					}));
		}

		final ImageView imageView = holder.imageView;
		ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
		if (StringUtils.isNotEmpty(idea.getBigPic())) {
			// TODO (done) 这个替换要干嘛？
			nid.fetchImage(JzUtils.getImageUrl(idea.getBigPic()),
					R.drawable.good_idea_list_pic_none_icon, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								// TODO (done)
								// 这里缩放我没解释清除，是固定一条边，然后等比例另外一条边，然后取固定尺寸的图片内容
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(mContext, 262),
										UIUtil.dip2px(mContext, 180));
								// TODO (review) 圆角有问题
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 15));
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
				((IdeaListActivity) mContext).pushIntent(intent);
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

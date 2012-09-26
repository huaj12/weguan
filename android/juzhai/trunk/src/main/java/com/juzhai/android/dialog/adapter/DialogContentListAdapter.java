package com.juzhai.android.dialog.adapter;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.dialog.model.DialogContent;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;

public class DialogContentListAdapter extends PageAdapter<DialogContent> {
	private User tagerUser;

	public DialogContentListAdapter(User tagerUser, Context mContext) {
		super(mContext);
		this.tagerUser = tagerUser;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_dialog_content_list,
					null);
			holder = new ViewHolder();
			holder.leftTextView = (TextView) convertView
					.findViewById(R.id.message_left_content);
			holder.leftUserLogo = (ImageView) convertView
					.findViewById(R.id.message_left_logo);
			holder.rightUserLogo = (ImageView) convertView
					.findViewById(R.id.message_rigth_logo);
			holder.rightTextView = (TextView) convertView
					.findViewById(R.id.message_rigth_content);
			holder.createTimeTextView = (TextView) convertView
					.findViewById(R.id.message_send_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final DialogContent dailContent = data.getDatas().get(position);
		final TextView leftTextView = holder.leftTextView;
		final TextView rightTextView = holder.rightTextView;
		final ImageView leftUserLogo = holder.leftUserLogo;
		final TextView createTimeTextView = holder.createTimeTextView;
		final ImageView rightUserLogo = holder.rightUserLogo;

		rightTextView.setVisibility(View.VISIBLE);
		rightUserLogo.setVisibility(View.VISIBLE);
		leftTextView.setVisibility(View.VISIBLE);
		leftUserLogo.setVisibility(View.VISIBLE);

		if (dailContent.getSenderUid() == tagerUser.getUid()) {
			rightTextView.setVisibility(View.GONE);
			rightUserLogo.setVisibility(View.GONE);
			leftTextView.setText(dailContent.getContent());
			setLogo(leftUserLogo, tagerUser);
		} else {
			leftTextView.setVisibility(View.GONE);
			leftUserLogo.setVisibility(View.GONE);
			rightTextView.setText(dailContent.getContent());
			setLogo(rightUserLogo, UserCache.getUserInfo());
		}
		createTimeTextView.setText(JzUtils.showTime(mContext, new Date(
				dailContent.getCreateTime())));
		return convertView;
	}

	private class ViewHolder {
		public TextView leftTextView;
		public ImageView leftUserLogo;
		public ImageView rightUserLogo;
		public TextView createTimeTextView;
		public TextView rightTextView;
	}

	private void setLogo(final ImageView logo, User user) {
		if (user.isHasLogo() && StringUtils.isNotEmpty(user.getLogo())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
			nid.fetchImage(JzUtils.getImageUrl(user.getLogo()),
					R.drawable.user_face_unload, logo,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(mContext, 40),
										UIUtil.dip2px(mContext, 40));
								logo.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 6));
							}
						}
					});
		}
	}
}

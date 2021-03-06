package com.juzhai.android.dialog.adapter;

import java.util.Date;

import org.springframework.util.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.PreviewActivity;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.dialog.activity.DialogContentListActivity;
import com.juzhai.android.dialog.bean.MessageStatus;
import com.juzhai.android.dialog.model.DialogContent;
import com.juzhai.android.home.activity.UserHomeActivity;
import com.juzhai.android.passport.data.UserCacheManager;
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
			holder.leftIcon = (ImageView) convertView
					.findViewById(R.id.message_left_icon);
			holder.leftImage = (ImageView) convertView
					.findViewById(R.id.message_left_image);
			holder.rightImage = (ImageView) convertView
					.findViewById(R.id.message_right_image);
			holder.rightRelativeLayout = (RelativeLayout) convertView
					.findViewById(R.id.message_rigth_layout);
			holder.leftRelativeLayout = (RelativeLayout) convertView
					.findViewById(R.id.message_left_layout);
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
		final ImageView leftIcon = holder.leftIcon;
		final ImageView leftImage = holder.leftImage;
		final ImageView rightImage = holder.rightImage;
		final RelativeLayout leftRelativeLayout = holder.leftRelativeLayout;
		final RelativeLayout rightRelativeLayout = holder.rightRelativeLayout;

		rightRelativeLayout.setVisibility(View.VISIBLE);
		rightUserLogo.setVisibility(View.VISIBLE);
		leftRelativeLayout.setVisibility(View.VISIBLE);
		leftUserLogo.setVisibility(View.VISIBLE);
		leftImage.setVisibility(View.GONE);
		rightImage.setVisibility(View.GONE);

		if (dailContent.getSenderUid() == tagerUser.getUid()) {
			rightRelativeLayout.setVisibility(View.GONE);
			rightUserLogo.setVisibility(View.GONE);
			leftTextView.setText(dailContent.getContent());
			setLogo(leftUserLogo, tagerUser);
			setImage(leftImage, dailContent);
		} else if (dailContent.getSenderUid() == UserCacheManager.getUserCache(
				mContext).getUid()) {
			leftRelativeLayout.setVisibility(View.GONE);
			leftUserLogo.setVisibility(View.GONE);
			rightTextView.setText(dailContent.getContent());
			setLogo(rightUserLogo, UserCacheManager.getUserCache(mContext)
					.getUserInfo());
			setImage(rightImage, dailContent);
		}
		createTimeTextView.setText(JzUtils.showTime(mContext, new Date(
				dailContent.getCreateTime())));
		updateMessageStauts(leftIcon, dailContent.getStatus());
		leftUserLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UserHomeActivity.class);
				intent.putExtra("uid", tagerUser.getUid());
				((DialogContentListActivity) mContext).pushIntent(intent);

			}
		});
		return convertView;
	}

	private void updateMessageStauts(ImageView leftIcon, MessageStatus status) {
		if (status == null || status.equals(MessageStatus.SUCCESS)) {
			leftIcon.setBackgroundDrawable(null);
			leftIcon.setVisibility(View.GONE);
			return;
		}
		leftIcon.setVisibility(View.VISIBLE);
		switch (status) {
		case WAIT:
			leftIcon.setBackgroundResource(R.drawable.mess_icon_waiting);
			break;
		case SENDING:
			leftIcon.setBackgroundResource(R.drawable.mess_icon_sending);
			break;
		case ERROR:
			leftIcon.setBackgroundResource(R.drawable.mess_icon_send_unable);
			break;
		}
	}

	private void setImage(final ImageView imageView, DialogContent dialogContent) {
		imageView.setImageBitmap(null);
		final Intent intent = new Intent(mContext, PreviewActivity.class);
		intent.putExtra("defaultImage", R.drawable.message_pic_load);
		if (dialogContent.getImage() != null) {
			imageView.setVisibility(View.VISIBLE);
			Bitmap zoomBitmap = ImageUtils.zoomBitmap(dialogContent.getImage(),
					40, 40, mContext);
			imageView.setImageBitmap(zoomBitmap);
			intent.putExtra("imageBitmap", dialogContent.getImage());
		} else if (StringUtils.hasText(dialogContent.getImgUrl())) {
			imageView.setVisibility(View.VISIBLE);
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
			nid.fetchImage(JzUtils.getImageUrl(dialogContent.getImgUrl()),
					R.drawable.message_pic_load, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, 40, 40, mContext);
								imageView.setImageBitmap(zoomBitmap);
							}
						}
					});
			intent.putExtra("imageUrl", dialogContent.getOriginalImgUrl());
		} else {
			imageView.setVisibility(View.GONE);
		}
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((DialogContentListActivity) mContext).pushIntent(intent);
			}
		});
	}

	private void setLogo(final ImageView logo, User user) {
		if (StringUtils.hasText(user.getLogo())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
			nid.fetchImage(JzUtils.getImageUrl(user.getLogo()),
					R.drawable.user_face_unload, logo,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, 40, 40, mContext);
								logo.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 5,
												mContext));
							}
						}
					});
		}
	}

	private class ViewHolder {
		public TextView leftTextView;
		public ImageView leftUserLogo;
		public ImageView rightUserLogo;
		public TextView createTimeTextView;
		public TextView rightTextView;
		public ImageView leftIcon;
		public ImageView leftImage;
		public ImageView rightImage;
		public RelativeLayout leftRelativeLayout;
		public RelativeLayout rightRelativeLayout;
	}
}

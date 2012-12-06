package com.juzhai.android.home.helper.impl;

import org.springframework.util.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.home.bean.OnlineStatus;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.passport.model.User;

public class UserViewHelper implements IUserViewHelper {

	@Override
	public void showUserLogo(final Context context, User user,
			final ImageView imageView, final int width, final int height) {
		if (StringUtils.hasText(user.getLogo())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(context);
			nid.fetchImage(JzUtils.getImageUrl(user.getLogo()),
					R.drawable.user_face_unload, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, width, height, context);
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 5,
												context));
							}
						}
					});
		}
	}

	@Override
	public void showUserNickname(Context context, User user, TextView textView) {
		if (user.getGender() == 0) {
			textView.setTextColor(context.getResources().getColor(
					R.color.nickname_girl_color));
		} else {
			textView.setTextColor(context.getResources().getColor(
					R.color.nickname_boy_color));
		}
		textView.setText(user.getNickname());
	}

	@Override
	public void showOnlineState(Context context, User user, TextView textView) {
		OnlineStatus onlineStatus = OnlineStatus.getOnlineStatusEnum(user
				.getOnlineStatus());
		if (onlineStatus == null) {
			return;
		}
		switch (onlineStatus) {
		case NOW:
			textView.setText(context.getResources().getString(
					R.string.online_now));
			textView.setTextColor(context.getResources().getColor(
					R.color.online_status_now));
			break;
		case TODAY:
			textView.setText(context.getResources().getString(
					R.string.online_today));
			textView.setTextColor(context.getResources().getColor(
					R.color.online_status_today));
			break;
		case RECENT:
			textView.setText(context.getResources().getString(
					R.string.online_recent));
			textView.setTextColor(context.getResources().getColor(
					R.color.online_status_recent));
			break;
		case NONE:
			textView.setText(null);
			break;
		}
	}

	@Override
	public void showUserNewLogo(final Context context, User user,
			final ImageView imageView, final TextView textView,
			final int width, final int height) {
		if (StringUtils.hasText(user.getNewLogo())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(context);
			final int verifystate = user.getLogoVerifyState();
			nid.fetchImage(JzUtils.getImageUrl(user.getNewLogo()),
					R.drawable.user_face_unload, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, width, height, context);
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 5,
												context));
								String str = JzUtils.getLogverifyStateString(
										verifystate, context);
								if (StringUtils.hasText(str)) {
									textView.setVisibility(View.VISIBLE);
									textView.setText(str);
								} else {
									textView.setVisibility(View.GONE);
								}
							}
						}
					});
		}
	}

	@Override
	public String getUserInfoCity(User user, Context mContext) {
		String comma = " ";
		StringBuffer sbString = new StringBuffer();
		if (JzUtils.age(user.getBirthYear()) > 0) {
			sbString.append(JzUtils.age(user.getBirthYear())
					+ mContext.getResources().getString(R.string.age));
			sbString.append(comma);
		}
		if (StringUtils.hasText(user.getCityName())) {
			sbString.append(user.getCityName());
			sbString.append(comma);
		}
		if (StringUtils.hasText(user.getProfession())) {
			sbString.append(user.getProfession());
		}
		return sbString.toString();
	}
}

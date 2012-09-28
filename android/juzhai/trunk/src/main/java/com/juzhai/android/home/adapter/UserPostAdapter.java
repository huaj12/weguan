package com.juzhai.android.home.adapter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
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
import com.juzhai.android.home.bean.OnlineStatus;
import com.juzhai.android.passport.model.User;

public class UserPostAdapter extends PageAdapter<User> {

	public UserPostAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_user_post_list, null);
			holder = new ViewHolder();
			holder.nicknameView = (TextView) convertView
					.findViewById(R.id.user_nickname);
			holder.postImageView = (ImageView) convertView
					.findViewById(R.id.post_image);
			holder.postContentView = (TextView) convertView
					.findViewById(R.id.post_content);
			holder.postInterest = (Button) convertView
					.findViewById(R.id.post_interest);
			holder.userInfoView = (TextView) convertView
					.findViewById(R.id.user_info);
			holder.userLogoView = (ImageView) convertView
					.findViewById(R.id.user_logo);
			holder.userOnlineStatusView = (TextView) convertView
					.findViewById(R.id.user_online_stauts);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final User user = data.getDatas().get(position);
		final TextView nicknameView = holder.nicknameView;
		final ImageView postImageView = holder.postImageView;
		final TextView postContentView = holder.postContentView;
		final Button postInterest = holder.postInterest;
		final TextView userInfoView = holder.userInfoView;
		final ImageView userLogoView = holder.userLogoView;
		final TextView userOnlineStatusView = holder.userOnlineStatusView;
		setLogoImage(user, userLogoView);
		setPostImage(user.getPostView().getBigPic(), postImageView);
		postContentView.setText(user.getPostView().getContent());
		userInfoView.setText(TextTruncateUtil.truncate(
				user.getUserInfo(mContext), 23, "..."));
		setNickName(nicknameView, user);
		setRespBtn(user, postInterest);
		setUserOnlineStauts(userOnlineStatusView, user.getOnlineStatus());
		return convertView;
	}

	private class ViewHolder {
		public TextView userOnlineStatusView;
		public ImageView userLogoView;
		public ImageView postImageView;
		public Button postInterest;
		public TextView nicknameView;
		public TextView userInfoView;
		public TextView postContentView;
	}

	private void setRespBtn(final User user, final TextView postInterest) {
		int respCnt = user.getPostView().getRespCnt();
		if (user.getPostView().isHasResp()) {
			postInterest.setEnabled(false);
			postInterest.setText(mContext.getResources().getString(
					R.string.post_interest_done)
					+ " " + (respCnt > 0 ? respCnt : ""));
		} else {
			postInterest.setEnabled(true);
			postInterest.setText(mContext.getResources().getString(
					R.string.post_interest)
					+ " " + (respCnt > 0 ? respCnt : ""));
			Map<String, String> values = new HashMap<String, String>();
			values.put("postId", String.valueOf(user.getPostView().getPostId()));
			postInterest.setOnClickListener(new SimpleClickListener(
					"post/respPost", mContext, values,
					new ListenerSuccessCallBack() {
						@Override
						public void callback() {
							postInterest.setEnabled(false);
							postInterest.setText(mContext.getResources()
									.getString(R.string.post_interest_done)
									+ " "
									+ (user.getPostView().getRespCnt() + 1));
						}
					}));

		}
	}

	private void setLogoImage(User user, final ImageView imageView) {
		ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
		if (user.isHasLogo() && StringUtils.isNotEmpty(user.getLogo())) {
			nid.fetchImage(JzUtils.getImageUrl(user.getLogo()),
					R.drawable.user_face_unload, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(mContext, 60),
										UIUtil.dip2px(mContext, 60));
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		}
	}

	private void setPostImage(String url, final ImageView imageView) {
		ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
		if (StringUtils.isNotEmpty(url)) {
			nid.fetchImage(JzUtils.getImageUrl(url), 0, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(mContext, 105),
										UIUtil.dip2px(mContext, 70));
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		} else {
			imageView.setVisibility(View.GONE);
		}
	}

	private void setNickName(TextView nicknameView, User user) {
		if (user.getGender() == 0) {
			nicknameView.setTextColor(mContext.getResources().getColor(
					R.color.pink));
		} else {
			nicknameView.setTextColor(mContext.getResources().getColor(
					R.color.blue));
		}
		nicknameView.setText(TextTruncateUtil.truncate(user.getNickname(), 20,
				"..."));
	}

	public void setUserOnlineStauts(TextView userOnlineStatusView, int status) {
		OnlineStatus onlineStatus = OnlineStatus.getOnlineStatusEnum(status);
		if (onlineStatus == null) {
			return;
		}
		switch (onlineStatus) {
		case NOW:
			userOnlineStatusView.setText(mContext.getResources().getString(
					R.string.online_now));
			userOnlineStatusView.setTextColor(mContext.getResources().getColor(
					R.color.online_status_now));
			break;
		case TODAY:
			userOnlineStatusView.setText(mContext.getResources().getString(
					R.string.online_today));
			userOnlineStatusView.setTextColor(mContext.getResources().getColor(
					R.color.online_status_today));
			break;
		case RECENT:
			userOnlineStatusView.setText(mContext.getResources().getString(
					R.string.online_recent));
			userOnlineStatusView.setTextColor(mContext.getResources().getColor(
					R.color.online_status_recent));
			break;
		case NONE:
			break;
		}
	}
}

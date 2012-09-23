package com.juzhai.android.idea.adapter;

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
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.idea.model.IdeaUser;
import com.juzhai.android.passport.model.User;

public class IdeaUserListAdapter extends PageAdapter<IdeaUser> {

	public IdeaUserListAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_idea_users, null);
			holder = new ViewHolder();
			holder.nicknameTextView = (TextView) convertView
					.findViewById(R.id.user_nickname);
			holder.userLogoImageView = (ImageView) convertView
					.findViewById(R.id.user_logo);
			holder.userInfoTextView = (TextView) convertView
					.findViewById(R.id.user_info);
			holder.dateButton = (Button) convertView
					.findViewById(R.id.about_btn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final IdeaUser ideaUser = data.getDatas().get(position);

		final TextView nicknameTextView = holder.nicknameTextView;
		final TextView infoTextView = holder.userInfoTextView;
		final ImageView imageView = holder.userLogoImageView;
		final Button dateButton = holder.dateButton;

		User user = ideaUser.getUserView();
		// TODO (done) hasInterest是约他的意思？
		// TODO (done) 用按钮状态的方式来改变背景资源（不知道text内容和颜色能否也一样的做法） 这个是约他的button也有按下效果
		dateButton.setBackgroundResource(R.drawable.about_selector_button);
		dateButton.setText(R.string.about);
		dateButton
				.setTextColor(mContext.getResources().getColor(R.color.white));
		// TODO (done) 按钮按下没有效果？ 这个是约他的button也有按下效果上面设置了。
		Map<String, String> values = new HashMap<String, String>();
		values.put("targetUid", String.valueOf(user.getUid()));
		values.put("ideaId", String.valueOf(ideaUser.getIdeaId()));
		dateButton.setOnClickListener(new SimpleClickListener(
				"dialog/sendDate", mContext, values,
				new ListenerSuccessCallBack() {
					@Override
					public void callback() {
						// TODO (done)
						// 用按钮状态的方式来改变背景资源（不知道text内容和颜色能否也一样的做法）已经约了。所以是不可点状态了。不需要有按下效果了。
						dateButton
								.setBackgroundResource(R.drawable.date_btn_done);
						dateButton.setText(R.string.about_done);
						dateButton.setTextColor(mContext.getResources()
								.getColor(R.color.about_gray));
						dateButton.setOnClickListener(null);
					}
				}));

		ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
		if (user.isHasLogo() && StringUtils.isNotEmpty(user.getLogo())) {
			// TODO (done) 这个replace是干嘛的？
			nid.fetchImage(user.getLogo().replaceAll("test.", ""),
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
		if (user.getGender() == 0) {
			// TODO (done) 颜色问题，之前pink，我看不能编译临时改为blue
			nicknameTextView.setTextColor(mContext.getResources().getColor(
					R.color.pink));
		} else {
			nicknameTextView.setTextColor(mContext.getResources().getColor(
					R.color.blue));
		}
		// TODO (done) 这个截字有什么意义？ nickname会太长
		nicknameTextView.setText(TextTruncateUtil.truncate(user.getNickname(),
				Validation.NICKNAME_LENGTH_MAX, "..."));

		// TODO (review) 截字为什么是Validation？
		infoTextView.setText(TextTruncateUtil.truncate(
				user.getUserInfo(mContext),
				Validation.USER_INFO_CONTENT_MAX_LENGTH, "..."));
		return convertView;
	}

	private class ViewHolder {
		public ImageView userLogoImageView;
		public TextView nicknameTextView;
		public TextView userInfoTextView;
		public Button dateButton;
	}
}

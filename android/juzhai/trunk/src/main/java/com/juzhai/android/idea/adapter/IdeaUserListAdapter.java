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
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.task.TaskSuccessCallBack;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.utils.UIUtil;
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
		dateButton.setBackgroundResource(R.drawable.about_selector_button);
		dateButton.setText(R.string.about);
		dateButton
				.setTextColor(mContext.getResources().getColor(R.color.white));
		dateButton.setEnabled(true);
		Map<String, String> values = new HashMap<String, String>();
		values.put("targetUid", String.valueOf(user.getUid()));
		values.put("ideaId", String.valueOf(ideaUser.getIdeaId()));
		dateButton.setOnClickListener(new SimpleClickListener(
				"dialog/sendDate", mContext, values, new TaskSuccessCallBack() {
					@Override
					public void callback() {
						dateButton.setText(R.string.about_done);
						dateButton.setTextColor(mContext.getResources()
								.getColor(R.color.about_gray));
						dateButton.setEnabled(false);
					}
				}));

		if (StringUtils.isNotEmpty(user.getLogo())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
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
		if (user.getGender() == 0) {
			nicknameTextView.setTextColor(mContext.getResources().getColor(
					R.color.pink));
		} else {
			nicknameTextView.setTextColor(mContext.getResources().getColor(
					R.color.blue));
		}
		nicknameTextView.setText(TextTruncateUtil.truncate(user.getNickname(),
				20, "..."));
		infoTextView.setText(TextTruncateUtil.truncate(
				user.getUserInfo(mContext), 23, "..."));
		return convertView;
	}

	private class ViewHolder {
		public ImageView userLogoImageView;
		public TextView nicknameTextView;
		public TextView userInfoTextView;
		public Button dateButton;
	}
}

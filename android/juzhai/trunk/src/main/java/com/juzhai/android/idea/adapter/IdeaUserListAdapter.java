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
import com.juzhai.android.core.listener.BaseListener;
import com.juzhai.android.core.listener.ListenerSuccessCallBack;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
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
		// TODO (review) hasInterest是约他的意思？
		if (user.isHasInterest()) {
			// TODO (review) 用按钮状态的方式来改变背景资源（不知道text内容和颜色能否也一样的做法）
			dateButton.setBackgroundResource(R.drawable.date_btn_done);
			dateButton.setText(R.string.about_done);
			dateButton.setTextColor(mContext.getResources().getColor(
					R.color.about_gray));
		} else {
			// TODO (review) 用按钮状态的方式来改变背景资源（不知道text内容和颜色能否也一样的做法）
			dateButton.setBackgroundResource(R.drawable.about_selector_button);
			dateButton.setText(R.string.about);
			dateButton.setTextColor(mContext.getResources().getColor(
					R.color.white));
		}
		// TODO (review) 按钮按下没有效果？
		Map<String, String> values = new HashMap<String, String>();
		values.put("targetUid", String.valueOf(user.getUid()));
		values.put("ideaId", String.valueOf(ideaUser.getIdeaId()));
		dateButton.setOnClickListener(new BaseListener("dialog/sendDate",
				mContext, values, new ListenerSuccessCallBack() {
					@Override
					public void callback() {
						// TODO (review) 用按钮状态的方式来改变背景资源（不知道text内容和颜色能否也一样的做法）
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
			// TODO (review) 这个replace是干嘛的？
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
			//TODO (review) 颜色问题，之前pink，我看不能编译临时改为blue
			nicknameTextView.setTextColor(mContext.getResources().getColor(
					R.color.blue));
		} else {
			nicknameTextView.setTextColor(mContext.getResources().getColor(
					R.color.blue));
		}
		//TODO (review) 这个截字有什么意义？
		nicknameTextView.setText(TextTruncateUtil.truncate(user.getNickname(),
				Validation.NICKNAME_LENGTH_MAX, "..."));
		
		//TODO (review) 组装用户info的代码，可以封装到User里去
		StringBuffer sbString = new StringBuffer();
		if (JzUtils.age(user.getBirthYear()) > 0) {
			sbString.append(JzUtils.age(user.getBirthYear())
					+ mContext.getResources().getString(R.string.age));
			sbString.append(",");
		}
		if (StringUtils.isNotEmpty(user.getConstellation())) {
			sbString.append(user.getConstellation());
			sbString.append(",");
		}
		if (StringUtils.isNotEmpty(user.getProfession())) {
			sbString.append(user.getProfession());
		}
		//TODO (review) 截字为什么是Validation？
		infoTextView.setText(TextTruncateUtil.truncate(sbString.toString(),
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

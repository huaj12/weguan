package com.juzhai.android.idea.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.idea.model.IdeaUser;
import com.juzhai.android.passport.model.User;

public class IdeaUserListAdapter extends PageAdapter<IdeaUser> {
	private IUserViewHelper userViewHelper = new UserViewHelper();

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
				"dialog/sendDate", mContext, values, new TaskCallback() {
					@Override
					public void successCallback() {
						dateButton.setText(R.string.about_done);
						dateButton.setTextColor(mContext.getResources()
								.getColor(R.color.about_gray));
						dateButton.setEnabled(false);
					}

					@Override
					public String doInBackground() {
						return null;
					}
				}));
		userViewHelper.showUserNickname(mContext, user, nicknameTextView);
		userViewHelper.showUserLogo(mContext, user, imageView, 60, 60);
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

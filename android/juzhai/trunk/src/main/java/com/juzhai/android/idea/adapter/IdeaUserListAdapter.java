package com.juzhai.android.idea.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.home.activity.UserHomeActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.idea.activity.IdeaUsersActivity;
import com.juzhai.android.idea.model.IdeaUser;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;

public class IdeaUserListAdapter extends PageAdapter<IdeaUser> {
	private IUserViewHelper userViewHelper = new UserViewHelper();

	public IdeaUserListAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_idea_users, null);
			holder = new ViewHolder();
			holder.ideaUsersItemLayout = (RelativeLayout) convertView
					.findViewById(R.id.idea_users_item_layout);
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

		final RelativeLayout ideaUsersItemLayout = holder.ideaUsersItemLayout;
		final TextView nicknameTextView = holder.nicknameTextView;
		final TextView infoTextView = holder.userInfoTextView;
		final ImageView imageView = holder.userLogoImageView;
		final Button dateButton = holder.dateButton;
		User user = ideaUser.getUserView();

		if (user.getUid() == UserCache.getUid()) {
			dateButton.setVisibility(View.GONE);
		} else {
			dateButton.setVisibility(View.VISIBLE);
			dateButton.setText(R.string.about);
			dateButton.setEnabled(true);
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("targetUid", user.getUid());
			values.put("ideaId", ideaUser.getIdeaId());
			dateButton.setOnClickListener(new SimpleClickListener(
					"dialog/sendDate", mContext, values, new TaskCallback() {
						@Override
						public void successCallback() {
							dateButton.setText(R.string.about_done);
							dateButton.setEnabled(false);
						}

						@Override
						public String doInBackground() {
							return null;
						}
					}));
		}
		userViewHelper.showUserNickname(mContext, user, nicknameTextView);
		userViewHelper.showUserLogo(mContext, user, imageView, 60, 60);
		infoTextView.setText(TextTruncateUtil.truncate(
				user.getUserInfo(mContext), 23, "..."));
		ideaUsersItemLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UserHomeActivity.class);
				intent.putExtra("targetUser", ideaUser.getUserView());
				((IdeaUsersActivity) mContext).pushIntent(intent);

			}
		});
		return convertView;
	}

	private class ViewHolder {
		public RelativeLayout ideaUsersItemLayout;
		public ImageView userLogoImageView;
		public TextView nicknameTextView;
		public TextView userInfoTextView;
		public Button dateButton;
	}
}

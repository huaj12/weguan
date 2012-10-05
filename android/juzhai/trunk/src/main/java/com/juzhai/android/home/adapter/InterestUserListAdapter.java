package com.juzhai.android.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.model.User;

public class InterestUserListAdapter extends PageAdapter<User> {
	private IUserViewHelper userViewHelper = new UserViewHelper();

	public InterestUserListAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_interest, null);
			holder = new ViewHolder();
			holder.nicknameTextView = (TextView) convertView
					.findViewById(R.id.user_nickname);
			holder.userLogoImageView = (ImageView) convertView
					.findViewById(R.id.user_logo);
			holder.userInfoTextView = (TextView) convertView
					.findViewById(R.id.user_info);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		User user = data.getDatas().get(position);
		final TextView nicknameView = holder.nicknameTextView;
		final TextView userInfoView = holder.userInfoTextView;
		final ImageView userLogoView = holder.userLogoImageView;
		userViewHelper.showUserLogo(mContext, user, userLogoView, 60, 60);
		userViewHelper.showUserNickname(mContext, user, nicknameView);
		userInfoView.setText(user.getUserInfo(mContext));
		return convertView;
	}

	private class ViewHolder {
		public ImageView userLogoImageView;
		public TextView nicknameTextView;
		public TextView userInfoTextView;
	}
}

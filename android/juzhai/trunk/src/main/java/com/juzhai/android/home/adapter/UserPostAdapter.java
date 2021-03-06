package com.juzhai.android.home.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.home.activity.UserHomeActivity;
import com.juzhai.android.home.activity.ZhaobanActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.activity.PostDetailActivity;
import com.juzhai.android.post.helper.IPostViewHelper;
import com.juzhai.android.post.helper.impl.PostViewHelper;
import com.umeng.analytics.MobclickAgent;

public class UserPostAdapter extends PageAdapter<User> {
	private IPostViewHelper postViewHelper = new PostViewHelper();
	private final String RESPONSE_POST_URI = "post/respPost";
	private IUserViewHelper userViewHelper = new UserViewHelper();

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
			holder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.post_item_layout);
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
		final LinearLayout linearLayout = holder.linearLayout;

		postContentView.setText(user.getPostView().getPurpose() + ": "
				+ user.getPostView().getContent());
		userInfoView.setText(user.getUserInfo(mContext));
		userViewHelper.showUserLogo(mContext, user, userLogoView, 60, 60);

		postViewHelper.showSmallPostImage(mContext, user.getPostView(),
				postImageView);
		userViewHelper.showUserNickname(mContext, user, nicknameView);
		userViewHelper.showOnlineState(mContext, user, userOnlineStatusView);

		setRespBtn(user, postInterest);
		linearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, PostDetailActivity.class);
				intent.putExtra("user", user);
				intent.putExtra("position", position);
				((ZhaobanActivity) mContext).pushIntentForResult(intent,
						ActivityCode.RequestCode.ZHAOBAN_LIST_REQUEST_CODE);
			}
		});

		userLogoView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UserHomeActivity.class);
				intent.putExtra("uid", user.getUid());
				((ZhaobanActivity) mContext).pushIntent(intent);
			}
		});
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
		public LinearLayout linearLayout;
	}

	private void setRespBtn(final User user, final TextView postInterest) {
		int respCnt = user.getPostView().getRespCnt();
		if (user.getPostView().isHasResp()) {
			postInterest.setEnabled(false);
			postInterest.setText(mContext.getResources().getString(
					R.string.post_interest_done)
					+ " " + (respCnt > 0 ? respCnt : "") + "  ");
		} else {
			postInterest.setEnabled(true);
			postInterest.setText(mContext.getResources().getString(
					R.string.post_interest)
					+ " " + (respCnt > 0 ? respCnt : "") + "  ");
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("postId", user.getPostView().getPostId());
			postInterest.setOnClickListener(new SimpleClickListener(
					RESPONSE_POST_URI, mContext, values, false,
					new TaskCallback() {
						@Override
						public void successCallback() {
							DialogUtils.showSuccessDialog(mContext,
									R.string.post_interest_success);
							postInterest.setEnabled(false);
							postInterest.setText(mContext.getResources()
									.getString(R.string.post_interest_done)
									+ " "
									+ (user.getPostView().getRespCnt() + 1)
									+ "  ");
							user.getPostView().setHasResp(true);
							MobclickAgent.onEvent(mContext,
									UmengEvent.RESPONSE_POST);
						}

						@Override
						public String doInBackground() {
							return null;
						}
					}));

		}
	}
}

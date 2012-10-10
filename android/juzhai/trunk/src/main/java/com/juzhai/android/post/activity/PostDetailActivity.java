package com.juzhai.android.post.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.activity.DialogContentListActivity;
import com.juzhai.android.home.activity.UserHomeActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.Post;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.helper.IPostViewHelper;
import com.juzhai.android.post.helper.impl.PostViewHelper;

public class PostDetailActivity extends NavigationActivity {
	private User user;
	private IUserViewHelper userViewHelper = new UserViewHelper();
	private IPostViewHelper postViewHelper = new PostViewHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.post_detail_title));
		setNavContentView(R.layout.page_post_detail);
		user = (User) getIntent().getSerializableExtra("user");
		if (user == null) {
			popIntent();
		}
		ImageView userLogoView = (ImageView) findViewById(R.id.user_logo);
		TextView nicknameView = (TextView) findViewById(R.id.user_nickname);
		TextView userInfoView = (TextView) findViewById(R.id.user_info);
		TextView contentView = (TextView) findViewById(R.id.post_content);
		ImageView postImageView = (ImageView) findViewById(R.id.post_image);
		Button postInterest = (Button) findViewById(R.id.post_interest);
		Button contact = (Button) findViewById(R.id.contact);
		RelativeLayout userLayout = (RelativeLayout) findViewById(R.id.user_layout);

		userViewHelper.showUserLogo(PostDetailActivity.this, user,
				userLogoView, 60, 60);
		userViewHelper.showUserNickname(PostDetailActivity.this, user,
				nicknameView);
		userInfoView.setText(TextTruncateUtil.truncate(
				user.getUserInfo(PostDetailActivity.this), 23, "..."));

		contentView.setText(user.getPostView().getPurpose() + ": "
				+ user.getPostView().getContent());
		postViewHelper.showBigPostImage(PostDetailActivity.this,
				user.getPostView(), postImageView);
		showPostInfo();
		if (user.getUid() == UserCache.getUid()) {
			postInterest.setVisibility(View.GONE);
			contact.setVisibility(View.GONE);
		} else {
			setRespBtn(postInterest);
			contact.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(PostDetailActivity.this,
							DialogContentListActivity.class);
					intent.putExtra("targetUser", user);
					pushIntent(intent);
				}
			});
		}
		userLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PostDetailActivity.this,
						UserHomeActivity.class);
				intent.putExtra("targetUser", user);
				pushIntent(intent);

			}
		});
	}

	private void showPostInfo() {
		Post post = user.getPostView();
		// 设置时间
		TextView time = (TextView) findViewById(R.id.post_detail_time_text);
		if (StringUtils.isNotEmpty(post.getDate())) {
			time.setText(post.getDate());
		} else {
			time.setVisibility(View.GONE);
		}
		// 设置地点
		TextView place = (TextView) findViewById(R.id.post_detail_place_text);
		if (StringUtils.isNotEmpty(post.getPlace())) {
			place.setText(post.getPlace());
		} else {
			place.setVisibility(View.GONE);
		}

	}

	private void setRespBtn(final TextView postInterest) {
		if (user.getPostView().isHasResp()) {
			postInterest.setEnabled(false);
			postInterest.setText(getResources().getString(
					R.string.post_response_done));
		} else {
			postInterest.setEnabled(true);
			postInterest.setText(getResources().getString(
					R.string.post_response));
			Map<String, String> values = new HashMap<String, String>();
			values.put("postId", String.valueOf(user.getPostView().getPostId()));
			postInterest.setOnClickListener(new SimpleClickListener(
					"post/respPost", PostDetailActivity.this, values,
					new TaskCallback() {
						@Override
						public void successCallback() {
							postInterest.setEnabled(false);
							postInterest.setText(getResources().getString(
									R.string.post_response_done));
							user.getPostView().setHasResp(true);
							Intent intent = getIntent();
							intent.putExtra("user", user);
							setResult(
									ActivityCode.ResultCode.ZHAOBAN_LIST_RESULT_CODE,
									intent);
						}

						@Override
						public String doInBackground() {
							return null;
						}
					}));

		}
	}
}

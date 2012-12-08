package com.juzhai.android.post.activity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

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
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.activity.DialogContentListActivity;
import com.juzhai.android.home.activity.UserHomeActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.idea.activity.IdeaDetailActivity;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.helper.IPostViewHelper;
import com.juzhai.android.post.helper.impl.PostViewHelper;
import com.juzhai.android.post.model.Post;
import com.umeng.analytics.MobclickAgent;

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
		Button arrow = (Button) findViewById(R.id.post_detail_arrow_btn);
		RelativeLayout userLayout = (RelativeLayout) findViewById(R.id.user_layout);
		Button moreButton = (Button) findViewById(R.id.post_detial_more_view);
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
		if (user.getUid() == UserCacheManager.getUserCache(
				PostDetailActivity.this).getUid()) {
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
				gotoUserHome();
			}
		});
		arrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoUserHome();
			}
		});
		// TODO (done) moreButton默认是可见还是不可见？为什么会用if else？
		if (user.getPostView().getIdeaId() > 0
				&& StringUtils.hasText(user.getPostView().getLink())) {
			moreButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(PostDetailActivity.this,
							IdeaDetailActivity.class);
					intent.putExtra("ideaId", user.getPostView().getIdeaId());
					pushIntent(intent);
				}
			});
			moreButton.setVisibility(View.VISIBLE);
		}
	}

	private void gotoUserHome() {
		Intent intent = new Intent(PostDetailActivity.this,
				UserHomeActivity.class);
		intent.putExtra("uid", user.getUid());
		pushIntent(intent);
	}

	private void showPostInfo() {
		Post post = user.getPostView();
		// 设置时间
		TextView time = (TextView) findViewById(R.id.post_detail_time_text);
		RelativeLayout timeRelativeLayout = (RelativeLayout) findViewById(R.id.post_detail_time_text_layout);
		if (StringUtils.hasText(post.getDate())) {
			time.setText(post.getDate());
		} else {
			timeRelativeLayout.setVisibility(View.GONE);
		}
		// 设置地点
		TextView place = (TextView) findViewById(R.id.post_detail_place_text);
		RelativeLayout placeRelativeLayout = (RelativeLayout) findViewById(R.id.post_detail_place_text_layout);
		if (StringUtils.hasText(post.getPlace())) {
			place.setText(post.getPlace());
		} else {
			placeRelativeLayout.setVisibility(View.GONE);
		}
		// 设置有兴趣的人
		TextView interest = (TextView) findViewById(R.id.post_detail_interest_text);
		RelativeLayout interestRelativeLayout = (RelativeLayout) findViewById(R.id.post_detail_interest_text_layout);
		if (post.getRespCnt() > 0) {
			interest.setText(getResources().getString(
					R.string.post_detail_interest_cnt, post.getRespCnt()));
			interestRelativeLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(PostDetailActivity.this,
							ResponseActivity.class);
					intent.putExtra("postId", user.getPostView().getPostId());
					pushIntent(intent);
				}
			});
		} else {
			interestRelativeLayout.setVisibility(View.GONE);
		}
	}

	private void setRespBtn(final TextView postInterest) {
		if (user.getPostView().isHasResp()) {
			postInterest.setEnabled(false);
			postInterest.setText(getResources().getString(
					R.string.post_detail_response_done));
		} else {
			postInterest.setEnabled(true);
			postInterest.setText(getResources().getString(
					R.string.post_detail_response));
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("postId", user.getPostView().getPostId());
			postInterest.setOnClickListener(new SimpleClickListener(
					"post/respPost", PostDetailActivity.this, values, false,
					new TaskCallback() {
						@Override
						public void successCallback() {
							DialogUtils.showSuccessDialog(
									PostDetailActivity.this,
									R.string.post_interest_success);
							postInterest.setEnabled(false);
							postInterest.setText(getResources().getString(
									R.string.post_detail_response_done));
							user.getPostView().setHasResp(true);
							Intent intent = getIntent();
							intent.putExtra("user", user);
							setResult(
									ActivityCode.ResultCode.ZHAOBAN_LIST_RESULT_CODE,
									intent);
							MobclickAgent.onEvent(PostDetailActivity.this,
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

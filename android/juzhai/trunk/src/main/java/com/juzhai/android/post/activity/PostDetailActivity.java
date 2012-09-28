package com.juzhai.android.post.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.passport.model.UserPost;

public class PostDetailActivity extends NavigationActivity {
	public static final int ZHAOBAN_LIST_RESULT_CODE = 6;
	private User user;
	private IUserViewHelper userViewHelper = new UserViewHelper();

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
		final ImageView postImageView = (ImageView) findViewById(R.id.post_image);
		Button postInterest = (Button) findViewById(R.id.post_interest);
		contentView.setText(getResources().getString(R.string.post_head) + ":"
				+ user.getPostView().getContent());
		userViewHelper.showUserLogo(PostDetailActivity.this, user,
				userLogoView, 60, 60);
		userViewHelper.showUserNickname(PostDetailActivity.this, user,
				nicknameView);
		userInfoView.setText(TextTruncateUtil.truncate(
				user.getUserInfo(PostDetailActivity.this), 23, "..."));
		if (StringUtils.isNotEmpty(user.getPostView().getBigPic())) {
			postImageView.setVisibility(View.VISIBLE);
			ImageViewLoader nid = ImageViewLoader
					.getInstance(PostDetailActivity.this);
			// TODO (review) 默认图片
			nid.fetchImage(JzUtils.getImageUrl(user.getPostView().getBigPic()),
					0, postImageView, new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(
												PostDetailActivity.this, 262),
										UIUtil.dip2px(PostDetailActivity.this,
												180));
								postImageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		} else {
			postImageView.setVisibility(View.GONE);
		}
		initUserPostInfo();

		setRespBtn(postInterest);
	}

	private void initUserPostInfo() {
		UserPost post = user.getPostView();
		// 设置时间
		LinearLayout timeLayout = (LinearLayout) findViewById(R.id.post_detail_time_layout);
		TextView time = (TextView) findViewById(R.id.post_detail_time_text);
		if (StringUtils.isNotEmpty(post.getDate())) {
			time.setText(post.getDate());
		} else {
			timeLayout.setVisibility(View.GONE);
		}
		// 设置地点
		LinearLayout placeLayout = (LinearLayout) findViewById(R.id.post_detail_place_layout);
		TextView place = (TextView) findViewById(R.id.post_detail_place_text);
		if (StringUtils.isNotEmpty(post.getPlace())) {
			place.setText(post.getPlace());
		} else {
			placeLayout.setVisibility(View.GONE);
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
					new ListenerSuccessCallBack() {
						@Override
						public void callback() {
							postInterest.setEnabled(false);
							postInterest.setText(getResources().getString(
									R.string.post_response_done));
							user.getPostView().setHasResp(true);
							Intent intent = getIntent();
							intent.putExtra("user", user);
							setResult(ZHAOBAN_LIST_RESULT_CODE, intent);
						}
					}));

		}
	}
}

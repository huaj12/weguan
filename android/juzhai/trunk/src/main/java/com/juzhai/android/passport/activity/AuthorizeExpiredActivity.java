package com.juzhai.android.passport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.listener.TpAuthorizeListener;
import com.juzhai.android.passport.model.User;

public class AuthorizeExpiredActivity extends BaseAuthorizeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final User user = UserCacheManager.getUserCache(
				AuthorizeExpiredActivity.this).getUserInfo();
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.authorize_title));
		setNavContentView(R.layout.page_authorize_expired);

		TextView tip1 = (TextView) findViewById(R.id.authorize_expired_tip1);
		TextView tip2 = (TextView) findViewById(R.id.authorize_expired_tip2);

		if (user.getTpId() == 6) {
			tip1.setText(R.string.authorize_expired_tip1_sina);
			tip2.setText(R.string.authorize_expired_tip2_sina);
		} else if (user.getTpId() == 7) {
			tip1.setText(R.string.authorize_expired_tip1_db);
			tip2.setText(R.string.authorize_expired_tip2_db);
		} else if (user.getTpId() == 8) {
			tip1.setText(R.string.authorize_expired_tip1_qq);
			tip2.setText(R.string.authorize_expired_tip2_qq);
		}

		((Button) findViewById(R.id.back))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popIntent();
					}
				});

		((Button) findViewById(R.id.expired_authorize))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(
								AuthorizeExpiredActivity.this,
								TpLoginActivity.class);
						intent.putExtra("tpId", user.getTpId());
						intent.putExtra("authorizeType", 1);
						pushIntentForResult(intent,
								TpAuthorizeListener.AUTHORIZE_REQUEST);
					}
				});
	}
}

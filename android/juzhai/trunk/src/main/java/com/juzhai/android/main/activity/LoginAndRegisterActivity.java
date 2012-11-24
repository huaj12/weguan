/**
 * 
 */
package com.juzhai.android.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.activity.LoginActivity;
import com.juzhai.android.passport.activity.RegisterActivity;

/**
 * @author kooks
 * 
 */
public class LoginAndRegisterActivity extends NavigationActivity {
	private Bitmap bitmap = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_login_register);
		ImageView image = (ImageView) findViewById(R.id.login_register_bg_image);
		bitmap = ImageUtils.getZoomBackground(
				getResources().getDrawable(R.drawable.welcome_loading_page),
				LoginAndRegisterActivity.this);
		image.setImageBitmap(bitmap);
		Button reginsterBtn = (Button) findViewById(R.id.register_btn);
		Button loginBtn = (Button) findViewById(R.id.login_btn);
		reginsterBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pushIntentForResult(new Intent(LoginAndRegisterActivity.this,
						RegisterActivity.class),
						ActivityCode.RequestCode.CLEAR_REQUEST_CODE);
			}
		});
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pushIntentForResult(new Intent(LoginAndRegisterActivity.this,
						LoginActivity.class),
						ActivityCode.RequestCode.CLEAR_REQUEST_CODE);
			}
		});
	}

	@Override
	protected void onDestroy() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		super.onDestroy();
	}
}

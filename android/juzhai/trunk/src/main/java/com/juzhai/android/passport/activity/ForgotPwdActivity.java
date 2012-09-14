/**
 * 
 */
package com.juzhai.android.passport.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.juzhai.android.R;

/**
 * @author kooks
 * 
 */
public class ForgotPwdActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password);
		Button backBt = (Button) findViewById(R.id.back);
		backBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ForgotPwdActivity.this.finish();
			}
		});
	}

}

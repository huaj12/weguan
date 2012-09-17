/**
 * 
 */
package com.juzhai.android.passport.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.service.impl.PassportService;

/**
 * @author kooks
 * 
 */
public class ForgotPwdActivity extends NavigationActivity {
	private EditText account;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.forgot_password_title));
		setNavContentView(R.layout.forgot_password);

		Button sendBtn = (Button) findViewById(R.id.send_btn);
		account = (EditText) findViewById(R.id.account);

		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PassportService passportService = new PassportService();
				try {
					passportService.getbackPwd(ForgotPwdActivity.this, account
							.getText().toString());
					// TODO (review) 这里的提示加返回显然写法有问题。
					DialogUtils.showAlertDialog(ForgotPwdActivity.this,
							R.string.send_ok);
					popIntent();
				} catch (PassportException e) {
					if (e.getMessageId() > 0) {
						DialogUtils.showToastText(ForgotPwdActivity.this,
								e.getMessageId());
					} else {
						DialogUtils.showToastText(ForgotPwdActivity.this,
								e.getMessage());
					}
				}
			}
		});
	}
}

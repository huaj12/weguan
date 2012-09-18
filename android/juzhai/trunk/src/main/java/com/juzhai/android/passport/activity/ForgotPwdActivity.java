/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.forgot_password_title));
		setNavContentView(R.layout.forgot_password);

		account = (EditText) findViewById(R.id.account);
		Button sendBtn = (Button) findViewById(R.id.send_btn);
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTask<String, Integer, String>() {
					@Override
					protected String doInBackground(String... params) {
						PassportService passportService = new PassportService();
						try {
							passportService.getbackPwd(ForgotPwdActivity.this,
									params[0]);
							return null;
						} catch (PassportException e) {
							if (e.getMessageId() > 0) {
								return ForgotPwdActivity.this.getResources()
										.getString(e.getMessageId());
							} else {
								return e.getMessage();
							}
						}
					}

					@Override
					protected void onPostExecute(String errorInfo) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						if (StringUtils.isNotEmpty(errorInfo)) {
							DialogUtils.showToastText(ForgotPwdActivity.this,
									errorInfo);
						} else {
							DialogUtils.showToastText(ForgotPwdActivity.this,
									R.string.send_ok);
							TimerTask task = new TimerTask() {
								@Override
								public void run() {
									popIntent();
								}

							};
							new Timer().schedule(task, 2000);
						}
					}

					@Override
					protected void onPreExecute() {
						if (progressDialog != null) {
							progressDialog.show();
						} else {
							progressDialog = ProgressDialog.show(
									ForgotPwdActivity.this,
									getResources().getString(
											R.string.tip_find_pwding),
									getResources().getString(
											R.string.please_wait), true, false);
						}
					}

				}.execute(account.getText().toString());
			}
		});
	}
}

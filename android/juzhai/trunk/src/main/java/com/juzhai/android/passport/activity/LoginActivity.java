/**
 * 
 */
package com.juzhai.android.passport.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.passport.adapter.LoginInputListAdapter;
import com.juzhai.android.passport.adapter.TpLoginAdapter;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.listener.TpLoginListener;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

/**
 * @author kooks
 * 
 */
public class LoginActivity extends NavigationActivity {
	private ListView listViewInput = null;
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// --------------设置NavigationBar--------------------
		getNavigationBar()
				.setBarTitle(getResources().getString(R.string.login));
		setNavContentView(R.layout.login);
		Button finish = (Button) (Button) getLayoutInflater().inflate(
				R.layout.finish_button, null);
		finish.setOnClickListener(loginListener);
		getNavigationBar().setRightView(finish);
		// --------------设置NavigationBar--------------------

		listViewInput = (ListView) findViewById(R.id.login_listview_input);
		listViewInput
				.setAdapter(new LoginInputListAdapter(getLayoutInflater()));

		// 第三方登录
		ListView mListView = (ListView) findViewById(R.id.tp_login_listview_button);
		mListView.setAdapter(new TpLoginAdapter(getLayoutInflater()));
		mListView.setOnItemClickListener(new TpLoginListener(this));

		Button login = (Button) findViewById(R.id.login_button);
		login.setOnClickListener(loginListener);

		Button reg = (Button) findViewById(R.id.tip_reg_bt);
		reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pushIntentForResult(new Intent(LoginActivity.this,
						RegisterActivity.class), CLEAR_REQUEST_CODE);
			}
		});
		Button forgetPwd = (Button) findViewById(R.id.forget_pwd);
		forgetPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pushIntentForResult(new Intent(LoginActivity.this,
						ForgotPwdActivity.class), CLEAR_REQUEST_CODE);
			}
		});
	}

	/**
	 * 登录 、完成按钮事件处理事件
	 */
	private OnClickListener loginListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String account = ((EditText) listViewInput.findViewWithTag(0))
					.getText().toString();
			String password = ((EditText) listViewInput.findViewWithTag(1))
					.getText().toString();
			new AsyncTask<String, Integer, String>() {
				@Override
				protected String doInBackground(String... params) {
					IPassportService passportService = new PassportService();
					try {
						passportService.login(LoginActivity.this, params[0],
								params[1]);
						return null;
					} catch (PassportException e) {
						if (e.getMessageId() > 0) {
							return LoginActivity.this.getResources().getString(
									e.getMessageId());
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
					if (errorInfo != null && !errorInfo.equals("")) {
						DialogUtils
								.showToastText(LoginActivity.this, errorInfo);
					} else {
						clearStackAndStartActivity(new Intent(
								LoginActivity.this, MainTabActivity.class));
					}
				}

				@Override
				protected void onPreExecute() {
					if (progressDialog != null) {
						progressDialog.show();
					} else {
						progressDialog = ProgressDialog.show(
								LoginActivity.this,
								getResources().getString(R.string.tip_loging),
								getResources().getString(R.string.please_wait),
								true, false);
					}
				}
			}.execute(account, password);
		}

	};

}

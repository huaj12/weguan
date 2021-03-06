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
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.main.activity.UserGuideActivity;
import com.juzhai.android.passport.adapter.LoginInputListAdapter;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.helper.ITpLoginHelper;
import com.juzhai.android.passport.helper.impl.TpLoginHelper;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

/**
 * @author kooks
 * 
 */
public class LoginActivity extends NavigationActivity {
	private ListView listViewInput = null;
	private ProgressDialog progressDialog;
	private ITpLoginHelper tpLoginHelper = new TpLoginHelper();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// --------------设置NavigationBar--------------------
		getNavigationBar()
				.setBarTitle(getResources().getString(R.string.login));
		setNavContentView(R.layout.page_login);
		Button finish = setRightFinishButton();
		finish.setOnClickListener(loginListener);
		// --------------设置NavigationBar--------------------

		listViewInput = (ListView) findViewById(R.id.login_listview_input);
		listViewInput
				.setAdapter(new LoginInputListAdapter(getLayoutInflater()));

		// 第三方登录
		tpLoginHelper.addTpLoginListener(LoginActivity.this);
		Button login = (Button) findViewById(R.id.login_button);
		login.setOnClickListener(loginListener);

		Button forgetPwd = (Button) findViewById(R.id.forget_pwd);
		forgetPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pushIntentForResult(new Intent(LoginActivity.this,
						ForgotPwdActivity.class),
						ActivityCode.RequestCode.CLEAR_REQUEST_CODE);
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
						return e.getMessage();
					}
				}

				@Override
				protected void onPostExecute(String errorInfo) {
					if (progressDialog != null) {
						progressDialog.dismiss();
					}
					if (errorInfo != null && !errorInfo.equals("")) {
						DialogUtils.showErrorDialog(LoginActivity.this,
								errorInfo);
						// DialogUtils
						// .showToastText(LoginActivity.this, errorInfo);
					} else {
						if (UserCacheManager.getUserCache(LoginActivity.this)
								.getUserInfo().isHasGuided()) {
							clearStackAndStartActivity(new Intent(
									LoginActivity.this, MainTabActivity.class));
						} else {
							clearStackAndStartActivity(new Intent(
									LoginActivity.this, UserGuideActivity.class));
						}

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

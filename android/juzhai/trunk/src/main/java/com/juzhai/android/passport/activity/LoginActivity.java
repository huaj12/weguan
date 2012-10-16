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
import com.juzhai.android.core.widget.list.table.model.BasicItem.ItemType;
import com.juzhai.android.core.widget.list.table.widget.UITableView;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.main.activity.UserGuideActivity;
import com.juzhai.android.passport.adapter.LoginInputListAdapter;
import com.juzhai.android.passport.data.UserCache;
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
		setNavContentView(R.layout.page_login);
		Button finish = setRightFinishButton();
		finish.setOnClickListener(loginListener);
		// --------------设置NavigationBar--------------------

		listViewInput = (ListView) findViewById(R.id.login_listview_input);
		listViewInput
				.setAdapter(new LoginInputListAdapter(getLayoutInflater()));

		// 第三方登录
		// ListView mListView = (ListView)
		// findViewById(R.id.tp_login_listview_button);
		// mListView.setAdapter(new TpLoginAdapter(getLayoutInflater()));
		// mListView.setOnItemClickListener(new TpLoginListener(this));

		UITableView tpLoginTableView = (UITableView) findViewById(R.id.tp_login_table_view);
		tpLoginTableView.setClickListener(new TpLoginListener(this));
		tpLoginTableView.addBasicItem(R.drawable.sina_login_icon,
				getResources().getString(R.string.sina_login_title), null,
				ItemType.HORIZONTAL);
		tpLoginTableView.addBasicItem(R.drawable.qq_login_icon, getResources()
				.getString(R.string.qq_login_title), null, ItemType.HORIZONTAL);
		tpLoginTableView.addBasicItem(R.drawable.db_login_icon, getResources()
				.getString(R.string.db_login_title), null, ItemType.HORIZONTAL);
		tpLoginTableView.commit();

		Button login = (Button) findViewById(R.id.login_button);
		login.setOnClickListener(loginListener);

		Button reg = (Button) findViewById(R.id.tip_reg_bt);
		reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pushIntentForResult(new Intent(LoginActivity.this,
						RegisterActivity.class),
						ActivityCode.RequestCode.CLEAR_REQUEST_CODE);
			}
		});
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
						DialogUtils
								.showToastText(LoginActivity.this, errorInfo);
					} else {
						if (UserCache.getUserInfo().isHasGuided()) {
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

/**
 * 
 */
package com.juzhai.android.passport.activity;

import org.apache.commons.lang.StringUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.passport.InitDate;
import com.juzhai.android.passport.adapter.RegisterInputListAdapter;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.listener.TpLoginListener;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

/**
 * @author kooks
 * 
 */
public class RegisterActivity extends NavigationActivity {
	private ListView listViewInput = null;
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// --------------设置NavigationBar--------------------
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.register));
		setNavContentView(R.layout.register);
		Button finish = (Button) (Button) getLayoutInflater().inflate(
				R.layout.finish_button, null);
		finish.setOnClickListener(registerListener);
		getNavigationBar().setRightBarButton(finish);
		// --------------设置NavigationBar--------------------

		ListView mListView = (ListView) findViewById(R.id.tp_reg_listview_button);
		listViewInput = (ListView) findViewById(R.id.reg_listview_input);
		mListView
				.setAdapter(new SimpleAdapter(this, new InitDate(this)
						.getTpLoginList(), R.layout.tp_login_list_item,
						new String[] { "image_logo", "item_title", "arrow" },
						new int[] { R.id.tp_image_logo, R.id.tp_item_title,
								R.id.tp_image_select }));
		listViewInput.setAdapter(new RegisterInputListAdapter(this,
				LAYOUT_INFLATER_SERVICE));
		Button login = (Button) findViewById(R.id.tip_login_bt);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popIntent();
			}
		});
		mListView.setOnItemClickListener(new TpLoginListener(this));
	}

	/**
	 * 完成按钮注册处理事件
	 */
	private OnClickListener registerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO (review) 用id去获得
			String nickname = ((EditText) ((RelativeLayout) ((LinearLayout) listViewInput
					.getChildAt(0)).getChildAt(0)).getChildAt(0)).getText()
					.toString();
			String account = ((EditText) ((RelativeLayout) ((LinearLayout) listViewInput
					.getChildAt(1)).getChildAt(0)).getChildAt(0)).getText()
					.toString();
			String pwd = ((EditText) ((RelativeLayout) ((LinearLayout) listViewInput
					.getChildAt(2)).getChildAt(0)).getChildAt(0)).getText()
					.toString();
			String confirmPwd = ((EditText) ((RelativeLayout) ((LinearLayout) listViewInput
					.getChildAt(3)).getChildAt(0)).getChildAt(0)).getText()
					.toString();

			new AsyncTask<String, Integer, String>() {
				@Override
				protected String doInBackground(String... params) {
					IPassportService passportService = new PassportService();
					try {
						passportService.register(RegisterActivity.this,
								params[0], params[1], params[2], params[3]);
						return null;
					} catch (PassportException e) {
						if (e.getMessageId() > 0) {
							return RegisterActivity.this.getResources()
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
						DialogUtils.showToastText(RegisterActivity.this,
								errorInfo);
					} else {
						clearStackAndStartActivity(new Intent(
								RegisterActivity.this, MainTabActivity.class));
					}
				}

				@Override
				protected void onPreExecute() {
					if (progressDialog != null) {
						progressDialog.show();
					} else {
						progressDialog = ProgressDialog.show(
								RegisterActivity.this, getResources()
										.getString(R.string.tip_reging),
								getResources().getString(R.string.please_wait),
								true, false);
					}
				}
			}.execute(account, nickname, pwd, confirmPwd);

		}

	};
}

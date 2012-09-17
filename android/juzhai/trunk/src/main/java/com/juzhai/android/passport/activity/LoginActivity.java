/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.passport.InitDate;
import com.juzhai.android.passport.adapter.LoginInputListAdapter;
import com.juzhai.android.passport.bean.UserCacheManager;
import com.juzhai.android.passport.listener.TpLoginListener;
import com.juzhai.android.passport.model.UserResults;

/**
 * @author kooks
 * 
 */
public class LoginActivity extends NavigationActivity {
	private ListView mListView = null;
	private ListView listViewInput = null;
	private String account = null;
	private String password = null;
	private Context mContext;// 上下文对象
	private Intent intent = null;

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
		getNavigationBar().setRightBarButton(finish);
		// --------------设置NavigationBar--------------------

		mContext = this;
		mListView = (ListView) findViewById(R.id.tp_login_listview_button);
		listViewInput = (ListView) findViewById(R.id.login_listview_input);
		mListView
				.setAdapter(new SimpleAdapter(this, new InitDate(this)
						.getTpLoginList(), R.layout.tp_login_list_item,
						new String[] { "image_logo", "item_title", "arrow" },
						new int[] { R.id.tp_image_logo, R.id.tp_item_title,
								R.id.tp_image_select }));
		listViewInput.setAdapter(new LoginInputListAdapter(this,
				LAYOUT_INFLATER_SERVICE));
		Button login = (Button) findViewById(R.id.bnLogin);
		Button reg = (Button) findViewById(R.id.tip_reg_bt);
		Button forget_pwd = (Button) findViewById(R.id.forget_pwd);
		forget_pwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(LoginActivity.this, ForgotPwdActivity.class);
				pushIntentForResult(intent, CLEAR_REQUEST_CODE);
			}
		});
		reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(LoginActivity.this, RegisterActivity.class);
				pushIntentForResult(intent, CLEAR_REQUEST_CODE);
			}
		});
		mListView.setOnItemClickListener(new TpLoginListener(this));
		login.setOnClickListener(loginListener);
		String errorInfo = getIntent().getStringExtra("errorInfo");
		if (StringUtils.isNotEmpty(errorInfo)) {
			DialogUtils.showToastText(mContext, errorInfo);
		}
	}

	/**
	 * 登录 、完成按钮事件处理事件
	 */
	private OnClickListener loginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 获取账号跟密码
			account = ((EditText) ((RelativeLayout) ((LinearLayout) listViewInput
					.getChildAt(0)).getChildAt(0)).getChildAt(0)).getText()
					.toString();
			password = ((EditText) ((RelativeLayout) ((LinearLayout) listViewInput
					.getChildAt(1)).getChildAt(0)).getChildAt(0)).getText()
					.toString();
			if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
				new AlertDialog.Builder(mContext)
						.setMessage(R.string.alertDefalutTitle)
						.setNegativeButton(R.string.close,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										setResult(RESULT_OK);// 取消按钮事件
									}
								}).show();
				return;
			}
			Map<String, String> values = new HashMap<String, String>();
			values.put("account", account);
			values.put("password", password);
			ResponseEntity<UserResults> responseEntity = null;
			try {
				responseEntity = HttpUtils.post("passport/login", values,
						UserResults.class);
			} catch (Exception e) {
				DialogUtils.showToastText(mContext,
						R.string.system_internet_erorr);
				if (BuildConfig.DEBUG) {
					Log.e(LoginActivity.class.getSimpleName(), "login error", e);
				}
				return;
			}
			UserResults results = responseEntity.getBody();
			if (!results.getSuccess()) {
				DialogUtils.showToastText(mContext, results.getErrorInfo());
			} else {
				// 保存登录信息
				UserCacheManager.initUserCacheManager(responseEntity, mContext);
				// 跳转到登录成功页面
				intent = new Intent(mContext, MainTabActivity.class);
				clearStackAndStartActivity(intent);
			}
		}

	};

}

/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.content.Intent;
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
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.passport.InitDate;
import com.juzhai.android.passport.adapter.RegisterInputListAdapter;
import com.juzhai.android.passport.bean.UserCacheManager;
import com.juzhai.android.passport.listener.TpLoginListener;
import com.juzhai.android.passport.model.UserResults;

/**
 * @author kooks
 * 
 */
public class RegisterActivity extends NavigationActivity {
	private ListView mListView = null;
	private ListView listViewInput = null;
	private Intent intent = null;
	private Context mContext;// 上下文对象

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// --------------设置NavigationBar--------------------
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.register));
		setNavContentView(R.layout.register);
		Button finish = (Button) (Button) getLayoutInflater().inflate(
				R.layout.finish_button, null);
		finish.setOnClickListener(loginListener);
		getNavigationBar().setRightBarButton(finish);
		// --------------设置NavigationBar--------------------

		mContext = this;
		mListView = (ListView) findViewById(R.id.tp_reg_listview_button);
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
		mListView.setOnItemClickListener(new TpLoginListener(mContext));
	}

	/**
	 * 完成按钮注册处理事件
	 */
	private OnClickListener loginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
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
			int errorInfo = VerifyData(nickname, account, pwd, confirmPwd);
			if (errorInfo != 0) {
				DialogUtils.showAlertDialog(mContext, errorInfo);
				return;
			}
			Map<String, String> values = new HashMap<String, String>();
			values.put("nickname", nickname);
			values.put("account", account);
			values.put("pwd", pwd);
			values.put("confirmPwd", confirmPwd);
			ResponseEntity<UserResults> responseEntity = null;
			try {
				responseEntity = HttpUtils.post("passport/register", values,
						UserResults.class);
			} catch (Exception e) {
				DialogUtils.showToastText(mContext,
						R.string.system_internet_erorr);
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

		private int VerifyData(String nickname, String account, String pwd,
				String confirmPwd) {

			if (StringUtils.isEmpty(nickname)) {
				return R.string.nickname_is_null;
			}
			int nicknameLength = StringUtil.chineseLength(nickname);
			if (nicknameLength > Validation.NICKNAME_LENGTH_MAX) {
				return R.string.nickname_too_long;
			}
			if (StringUtils.isEmpty(account)) {
				return R.string.email_is_null;
			}
			int emailLength = StringUtil.chineseLength(account);
			if (emailLength > Validation.REGISTER_EMAIL_MAX
					|| emailLength < Validation.REGISTER_EMAIL_MIN
					|| !StringUtil.checkMailFormat(account)) {
				return R.string.email_account_invalid;
			}
			int pwdLength = pwd.length();
			if (pwdLength < Validation.REGISTER_PASSWORD_MIN
					|| pwdLength > Validation.REGISTER_PASSWORD_MAX) {
				return R.string.pwd_length_invalid;
			}
			if (!StringUtils.equals(pwd, confirmPwd)) {
				return R.string.confirm_pwd_error;
			}
			return 0;
		}

	};
}

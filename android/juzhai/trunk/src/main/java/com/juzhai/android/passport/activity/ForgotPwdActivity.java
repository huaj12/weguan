/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Results;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;

/**
 * @author kooks
 * 
 */
public class ForgotPwdActivity extends NavigationActivity {
	private String uri = "passport/getbackpwd";
	private EditText account;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.forgot_password_title));
		setNavContentView(R.layout.forgot_password);
		mContext = this;
		Button sendBtn = (Button) findViewById(R.id.send_btn);
		account = (EditText) findViewById(R.id.account);
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String acccoutStr = account.getText().toString();
				int emailLength = StringUtil.chineseLength(acccoutStr);
				if (emailLength > Validation.REGISTER_EMAIL_MAX
						|| emailLength < Validation.REGISTER_EMAIL_MIN
						|| !StringUtil.checkMailFormat(acccoutStr)) {
					DialogUtils.showAlertDialog(mContext,
							R.string.email_account_invalid);
					return;
				}
				Map<String, String> values = new HashMap<String, String>();
				values.put("account", acccoutStr);
				ResponseEntity<Results> response = null;
				try {
					response = HttpUtils.post(uri, values, Results.class);
				} catch (Exception e) {
					DialogUtils.showToastText(mContext,
							R.string.system_internet_erorr);
					return;
				}
				Results results = response.getBody();
				String msg = null;
				if (results.getSuccess()) {
					DialogUtils.showAlertDialog(mContext, R.string.send_ok);
					popIntent();
				} else {
					msg = results.getErrorInfo();
					DialogUtils.showToastText(mContext, msg);
				}
			}
		});
	}
}

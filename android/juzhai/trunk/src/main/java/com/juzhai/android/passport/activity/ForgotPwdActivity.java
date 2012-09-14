/**
 * 
 */
package com.juzhai.android.passport.activity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.app.Activity;
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

/**
 * @author kooks
 * 
 */
public class ForgotPwdActivity extends Activity {
	private String uri = "passport/getbackpwd";
	private EditText account;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.forgot_password);
		Button backBtn = (Button) findViewById(R.id.back);
		Button sendBtn = (Button) findViewById(R.id.send_btn);
		account = (EditText) findViewById(R.id.account);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ForgotPwdActivity.this.finish();
			}
		});
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
				ResponseEntity<Results> response = HttpUtils.post(uri, values,
						Results.class);
				Results results = response.getBody();
				String msg = null;
				if (results.getSuccess()) {
					DialogUtils.showAlertDialog(mContext, R.string.send_ok);
					finish();
				} else {
					msg = results.getErrorInfo();
					Toast.makeText(mContext, msg, 5000).show();
				}
			}
		});
	}
}

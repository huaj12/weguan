/**
 * 
 */
package com.juzhai.android.home.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.task.PostProgressTask;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;

/**
 * @author kooks
 * 
 */
public class SetNicknameActivity extends NavigationActivity {
	private Button finish;
	private String uri = "profile/validate/nickexist";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_setting_nickname);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.nickname));
		finish = setRightFinishButton();
		finish.setText(R.string.save);
		getNavigationBar().setRightView(finish);
		String nickname = getIntent().getStringExtra("nickname");
		final EditText editText = (EditText) findViewById(R.id.nickname);
		editText.setText(nickname);
		openKeyboard(editText);
		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String nickname = editText.getText().toString();
				if (StringUtils.isEmpty(nickname)) {
					DialogUtils.showErrorDialog(SetNicknameActivity.this,
							R.string.nickname_is_null);
					// DialogUtils.showToastText(SetNicknameActivity.this,
					// R.string.nickname_is_null);
					return;
				}
				int nicknameLength = StringUtil.chineseLength(nickname);
				if (nicknameLength > Validation.NICKNAME_LENGTH_MAX) {
					DialogUtils.showErrorDialog(SetNicknameActivity.this,
							R.string.nickname_too_long);
					// DialogUtils.showToastText(SetNicknameActivity.this,
					// R.string.nickname_too_long);
					return;
				}
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("nickname", nickname);
				new PostProgressTask(SetNicknameActivity.this, uri, values,
						new TaskCallback() {
							@Override
							public void successCallback() {
								Intent intent = getIntent();
								intent.putExtra("nickname", nickname);
								setResult(
										ActivityCode.ResultCode.SETTING_NICKNAME_RESULT_CODE,
										intent);
								SetNicknameActivity.this.finish();
							}

							@Override
							public String doInBackground() {
								return null;
							}
						}).execute();

			}
		});
	}
}

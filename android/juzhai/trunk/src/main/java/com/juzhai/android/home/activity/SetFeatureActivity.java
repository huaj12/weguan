/**
 * 
 */
package com.juzhai.android.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;

/**
 * @author kooks
 * 
 */
public class SetFeatureActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_setting_feature);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.feature));
		Button finish = setRightFinishButton();
		finish.setText(R.string.save);
		String feature = getIntent().getStringExtra("feature");
		final EditText editText = (EditText) findViewById(R.id.feature_input);
		editText.setText(feature);
		openKeyboard(editText);
		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String feature = editText.getText().toString();
				int featureLength = StringUtil.chineseLength(feature);
				if (featureLength > Validation.USER_FEATURE_LENGTH_MAX) {
					DialogUtils.showErrorDialog(SetFeatureActivity.this,
							R.string.feature_content_invalid_error);
					return;
				}
				Intent intent = getIntent();
				intent.putExtra("feature", feature);
				setResult(ActivityCode.ResultCode.SETTING_FEATURE_RESULT_CODE,
						intent);
				SetFeatureActivity.this.finish();
			}
		});
	}

}

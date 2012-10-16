package com.juzhai.android.setting.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.juzhai.android.R;
import com.juzhai.android.core.task.ProgressTask;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.exception.DialogContentException;
import com.juzhai.android.dialog.service.impl.DialogContentService;

public class SettingFeedbackActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.setting_cell_feedback));
		setNavContentView(R.layout.page_setting_feedback);
		Button finish = setRightFinishButton();
		finish.setText(R.string.send);
		final EditText editText = (EditText) findViewById(R.id.feedback_input);
		Timer timer = new Timer(); // 设置定时器
		timer.schedule(new TimerTask() {
			@Override
			public void run() { // 弹出软键盘的代码
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(editText,
						InputMethodManager.RESULT_UNCHANGED_SHOWN);
			}
		}, 300); // 设置300毫秒的时长

		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				new ProgressTask(SettingFeedbackActivity.this,
						new TaskCallback() {
							@Override
							public String doInBackground() {
								try {
									new DialogContentService().sendFeedback(
											SettingFeedbackActivity.this,
											editText.getText().toString());
									return null;
								} catch (DialogContentException e) {
									return e.getMessage();
								}
							}

							@Override
							public void successCallback() {
								new Timer().schedule(new TimerTask() {
									@Override
									public void run() {
										popIntent();
									}
								}, 2000);
							}

						}).execute();
			}
		});
	}
}

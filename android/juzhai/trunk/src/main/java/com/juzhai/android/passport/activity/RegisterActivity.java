/**
 * 
 */
package com.juzhai.android.passport.activity;

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
	// TODO (done) loginListener？
	private OnClickListener registerListener = new OnClickListener() {

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

			IPassportService passportService = new PassportService();
			try {
				passportService.register(RegisterActivity.this, account,
						nickname, pwd, confirmPwd);
				clearStackAndStartActivity(new Intent(RegisterActivity.this,
						MainTabActivity.class));
			} catch (PassportException e) {
				if (e.getMessageId() > 0) {
					DialogUtils.showToastText(RegisterActivity.this,
							e.getMessageId());
				} else {
					DialogUtils.showToastText(RegisterActivity.this,
							e.getMessage());
				}
			}
		}

	};
}

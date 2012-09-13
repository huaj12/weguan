/**
 * 
 */
package com.juzhai.android.passport.activity;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.juzhai.android.R;
import com.juzhai.android.passport.InitDate;
import com.juzhai.android.passport.adapter.LoginInputListAdapter;

/**
 * @author kooks
 * 
 */
public class LoginActivity extends Activity {
	private ListView mListView = null;
	private ListView listViewInput = null;
	private String account = null;
	private String password = null;
	private Context mContext;// 上下文对象
	private Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
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
		Button finish = (Button) findViewById(R.id.finsh);
		login.setOnClickListener(loginListener);
		finish.setOnClickListener(loginListener);
		Button reg = (Button) findViewById(R.id.tip_reg_bt);
		reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(LoginActivity.this, WebViewActivity.class);
				switch (position) {
				case 0:
					intent.putExtra("tpId", "6");
					break;
				case 1:
					intent.putExtra("tpId", "7");
					break;
				case 2:
					intent.putExtra("tpId", "8");
					break;
				}
				startActivity(intent);
			}

		});

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
						.setNegativeButton("关闭",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										setResult(RESULT_OK);// 取消按钮事件
									}
								}).show();
				return;
			}

		}

	};
}

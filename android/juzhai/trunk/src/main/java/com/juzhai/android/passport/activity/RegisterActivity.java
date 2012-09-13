/**
 * 
 */
package com.juzhai.android.passport.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.juzhai.android.R;
import com.juzhai.android.passport.InitDate;
import com.juzhai.android.passport.adapter.RegisterInputListAdapter;

/**
 * @author kooks
 * 
 */
public class RegisterActivity extends Activity {
	private ListView mListView = null;
	private ListView listViewInput = null;
	private Intent intent = null;
	private Context mContext;// 上下文对象

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
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
				intent = new Intent(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(RegisterActivity.this,
						WebViewActivity.class);
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

}

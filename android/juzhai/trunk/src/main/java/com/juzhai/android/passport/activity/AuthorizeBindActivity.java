package com.juzhai.android.passport.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.table.model.BasicItem.ItemType;
import com.juzhai.android.core.widget.list.table.widget.UITableView;
import com.juzhai.android.passport.listener.TpAuthorizeListener;

public class AuthorizeBindActivity extends BaseAuthorizeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.authorize_title));
		setNavContentView(R.layout.page_authorize_bind);

		// 第三方登录
		UITableView tpLoginTableView = (UITableView) findViewById(R.id.tp_bind_table_view);
		tpLoginTableView.setClickListener(new TpAuthorizeListener(this));
		tpLoginTableView.addBasicItem(R.drawable.sina_login_icon,
				getResources().getString(R.string.sina_bind_title), null,
				ItemType.HORIZONTAL);
		tpLoginTableView.addBasicItem(R.drawable.qq_login_icon, getResources()
				.getString(R.string.qq_bind_title), null, ItemType.HORIZONTAL);
		tpLoginTableView.addBasicItem(R.drawable.db_login_icon, getResources()
				.getString(R.string.db_bind_title), null, ItemType.HORIZONTAL);
		tpLoginTableView.commit();

		((Button) findViewById(R.id.back))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popIntent();
					}
				});
	}
}

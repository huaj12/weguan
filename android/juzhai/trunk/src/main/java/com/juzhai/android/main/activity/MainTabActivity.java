package com.juzhai.android.main.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.juzhai.android.R;
import com.juzhai.android.home.activity.ZhaobanActivity;
import com.juzhai.android.widget.tab.TabBar;
import com.juzhai.android.widget.tab.TabBarItem;

public class MainTabActivity extends ActivityGroup {

	private TabBar<Intent> tabBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater layoutInflater = getLayoutInflater();

		tabBar = new TabBar<Intent>(R.layout.main_tab);
		tabBar.addTabBarItem(new TabBarItem<Intent>(R.drawable.zhaobaner,
				R.drawable.set, R.string.tabitem_zhaoban, new Intent(this,
						ZhaobanActivity.class)));
		tabBar.addTabBarItem(new TabBarItem<Intent>(R.drawable.chuquwaner,
				R.drawable.set, R.string.tabitem_chuquwan, new Intent(this,
						ZhaobanActivity.class)));
		tabBar.addTabBarItem(new TabBarItem<Intent>(R.drawable.mail,
				R.drawable.set, R.string.tabitem_message, new Intent(this,
						ZhaobanActivity.class)));
		tabBar.addTabBarItem(new TabBarItem<Intent>(R.drawable.wojia,
				R.drawable.set, R.string.tabitem_home, new Intent(this,
						ZhaobanActivity.class)));
		tabBar.addTabBarItem(new TabBarItem<Intent>(R.drawable.set,
				R.drawable.set, R.string.tabitem_setting, new Intent(this,
						ZhaobanActivity.class)));
		tabBar.setBgResources(R.drawable.tabbar_bg_default);
		setContentView(tabBar.build(layoutInflater, this));
	}
}
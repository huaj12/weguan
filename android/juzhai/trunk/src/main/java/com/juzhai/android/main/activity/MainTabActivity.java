package com.juzhai.android.main.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.tab.TabBar;
import com.juzhai.android.core.widget.tab.TabBarItem;
import com.juzhai.android.dialog.activity.DialogListActivity;
import com.juzhai.android.home.activity.HomeActivity;
import com.juzhai.android.home.activity.ZhaobanActivity;
import com.juzhai.android.idea.activity.IdeaListActivity;
import com.juzhai.android.setting.activity.SettingListActivity;

public class MainTabActivity extends ActivityGroup {

	private TabBar<Intent> tabBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater layoutInflater = getLayoutInflater();

		tabBar = new TabBar<Intent>(R.layout.page_main_tab);
		tabBar.addTabBarItem(new TabBarItem<Intent>(
				R.drawable.bottom_menu_icon_zhaober_link,
				R.drawable.bottom_menu_icon_zhaober_active,
				R.string.tabitem_zhaoban, new Intent(this,
						ZhaobanActivity.class)));
		tabBar.addTabBarItem(new TabBarItem<Intent>(
				R.drawable.bottom_menu_icon_chuquwan_link,
				R.drawable.bottom_menu_icon_chuquwan_active,
				R.string.tabitem_chuquwan, new Intent(this,
						IdeaListActivity.class)));
		tabBar.addTabBarItem(new TabBarItem<Intent>(
				R.drawable.bottom_menu_icon_xiaoxi_link,
				R.drawable.bottom_menu_icon_xiaoxi_active,
				R.string.tabitem_message, new Intent(this,
						DialogListActivity.class)));
		tabBar.addTabBarItem(new TabBarItem<Intent>(
				R.drawable.bottom_menu_icon_wojia_link,
				R.drawable.bottom_menu_icon_wojia_active,
				R.string.tabitem_home, new Intent(this, HomeActivity.class)));
		tabBar.addTabBarItem(new TabBarItem<Intent>(
				R.drawable.bottom_menu_icon_shezi_link,
				R.drawable.bottom_menu_icon_shezi_active,
				R.string.tabitem_setting, new Intent(this,
						SettingListActivity.class)));
		tabBar.setBgResources(R.drawable.tab_bar_background);
		setContentView(tabBar.build(layoutInflater, this));
	}
}
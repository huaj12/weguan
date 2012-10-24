package com.juzhai.android.main.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.tab.TabBar;
import com.juzhai.android.core.widget.tab.TabBarItem;
import com.juzhai.android.dialog.activity.DialogListActivity;
import com.juzhai.android.dialog.service.impl.DialogService;
import com.juzhai.android.home.activity.HomeActivity;
import com.juzhai.android.home.activity.ZhaobanActivity;
import com.juzhai.android.idea.activity.IdeaListActivity;
import com.juzhai.android.main.handler.MessageNoticiHandler;
import com.juzhai.android.passport.activity.AuthorizeBindActivity;
import com.juzhai.android.passport.activity.AuthorizeExpiredActivity;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.setting.activity.SettingListActivity;

public class MainTabActivity extends ActivityGroup {

	private final int noticeMessageDelay = 2000;
	private final int noticeMessagePeriod = 30000;

	/**
	 * tab
	 */
	public static TabBar<Intent> tabBar;

	private Timer timer;
	private MessageNoticiHandler messageNoticiHandler = new MessageNoticiHandler();

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

		User user = UserCache.getUserInfo();
		if (null != user) {
			if (user.hasTpExpired()) {
				Intent intent = new Intent(this, AuthorizeExpiredActivity.class);
				startActivity(intent);

			} else if (!user.hasTp()) {
				// 提示授权
				Intent intent = new Intent(this, AuthorizeBindActivity.class);
				startActivity(intent);
			}
		}
	}

	@Override
	protected void onResume() {
		// 开始timer
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					int messageCount = new DialogService()
							.newMessageCount(MainTabActivity.this);
					Message message = new Message();
					Bundle bundle = new Bundle();
					bundle.putInt(MessageNoticiHandler.MESSAGE_COUNT_KEY,
							messageCount);
					message.setData(bundle);
					messageNoticiHandler.sendMessage(message);
				}
			}, noticeMessageDelay, noticeMessagePeriod);
		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		// 停止timer
		if (null != timer) {
			timer.cancel();
			timer = null;
		}
		super.onStop();
	}
}
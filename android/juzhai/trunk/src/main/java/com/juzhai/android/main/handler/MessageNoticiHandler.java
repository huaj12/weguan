package com.juzhai.android.main.handler;

import android.os.Handler;
import android.os.Message;

import com.juzhai.android.main.activity.MainTabActivity;

public class MessageNoticiHandler extends Handler {

	public static final String MESSAGE_COUNT_KEY = "messageCount";

	@Override
	public void handleMessage(Message msg) {
		int badgeValue = msg.getData().getInt(MESSAGE_COUNT_KEY);
		MainTabActivity.tabBar.setBadgeValue(2, badgeValue);
	}
}

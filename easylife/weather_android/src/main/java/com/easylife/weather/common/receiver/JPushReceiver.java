package com.easylife.weather.common.receiver;

import org.springframework.util.StringUtils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;

import com.easylife.weather.R;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.activity.LaunchActivity;

public class JPushReceiver extends BroadcastReceiver {
	private int smsNoticeType = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			// send the Registration Id to your server...
		} else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())) {
			// send the UnRegistration Id to your server...
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			// 接收到推送下来的自定义消息:
			if (!WeatherUtils.isAppOnForeground(context)) {
				String title = bundle.getString(JPushInterface.EXTRA_TITLE);
				String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
				Intent msgIntent = new Intent(context, LaunchActivity.class);
				if (context.getResources().getString(R.string.update_code)
						.equals(message)) {
					msgIntent.putExtra("update", true);
				}
				msgIntent.setAction(Intent.ACTION_MAIN);
				msgIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent pendingIntent = PendingIntent.getActivity(
						context, 0, msgIntent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				WeatherUtils.sendNotificationMessage(smsNoticeType, StringUtils
						.hasText(title) ? title : context.getResources()
						.getString(R.string.notification_title), message,
						pendingIntent, context);
			}
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			// 接收到推送下来的通知

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			// 用户点击打开了通知
		}
	}
}

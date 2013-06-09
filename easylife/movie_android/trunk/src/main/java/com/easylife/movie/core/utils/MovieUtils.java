package com.easylife.movie.core.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.easylife.movie.R;
import com.easylife.movie.core.Constants;

public class MovieUtils {

	public static boolean isAppOnForeground(Context context) {
		String packageName = context.getPackageName();
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity
					.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	public static void sendNotificationMessage(int id, String title,
			String text, PendingIntent pendingIntent, Context context) {
		// 构造Notification对象
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		// 设置通知在状态栏显示的图标
		notification.icon = R.drawable.logo_tz;
		// 当我们点击通知时显示的内容
		notification.tickerText = text;
		// 通知时发出默认的声音
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 设置通知显示的参数
		notification.setLatestEventInfo(context, title, text, pendingIntent);
		notification.icon = R.drawable.logo_top;
		// 开始执行
		notificationManager.notify(id, notification);
	}

	public static void setRepeating(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0,
				new Intent(Constants.ALARM_INTENT),
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ Constants.NO_LOGIN_NOTICE_PERIOD, Constants.NOTICE_PERIOD,
				sender);

	}
}

package com.juzhai.android.core.service;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.http.ResponseEntity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.IntegerResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.main.activity.LaunchActivity;

public class NotificationService extends Service {
	private NotificationManager notificationManager;
	private int notificationType = 0;

	private Timer notificationTimer;
	private Timer weekTimer;
	private int baseDelay = 2000;
	private int noticeMessagePeriod = 5000;
	private int noticeWeekPeriod = 3600000;
	private int weekHourTime = 10;
	private int weekDayTime = 5;
	public final String BASEURL = "http://m.51juzhai.com/";
	// private String noticeNumsUri =
	// "http://192.168.15.102:8080/mobile/dialog/notice/nums";
	private String noticeNumsUri = "dialog/notice/nums";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationTimer = new Timer();
		notificationTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!isAppOnForeground(NotificationService.this)) {
					int messageCount = getMessageCount();
					if (messageCount > 0) {
						Intent intent = new Intent(NotificationService.this,
								LaunchActivity.class);
						intent.putExtra("itemIndex", 2);
						PendingIntent pendingIntent = PendingIntent
								.getActivity(NotificationService.this, 0,
										intent, 0);
						sendMessage(
								notificationType,
								R.drawable.logo,
								NotificationService.this.getResources()
										.getString(R.string.notification_title),
								NotificationService.this
										.getResources()
										.getString(
												R.string.notification_un_read_message,
												messageCount), pendingIntent);
					}
				}

			}
		}, baseDelay, noticeMessagePeriod);

		// 拒宅周末提醒
		weekTimer = new Timer();
		weekTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!isAppOnForeground(NotificationService.this)) {
					Calendar cal = Calendar.getInstance();
					int nowHour = cal.get(Calendar.HOUR_OF_DAY);
					int nowDay = cal.get(Calendar.DAY_OF_WEEK);
					if (nowHour == weekHourTime && nowDay == (weekDayTime + 1)) {
						Intent intent = new Intent(NotificationService.this,
								LaunchActivity.class);
						intent.putExtra("itemIndex", 1);
						PendingIntent pendingIntent = PendingIntent
								.getActivity(NotificationService.this, 0,
										intent, 0);
						sendMessage(
								notificationType,
								R.drawable.logo,
								NotificationService.this.getResources()
										.getString(R.string.notification_title),
								NotificationService.this.getResources()
										.getString(R.string.notification_week),
								pendingIntent);
					}
				}
			}
		}, baseDelay, noticeWeekPeriod);
	}

	@Override
	public void onDestroy() {
		// 停止timer
		if (null != notificationTimer) {
			notificationTimer.cancel();
			notificationTimer = null;
		}
		if (null != weekTimer) {
			weekTimer.cancel();
			weekTimer = null;
		}
		super.onDestroy();
	}

	private int getMessageCount() {
		try {
			ResponseEntity<IntegerResult> responseEntity = HttpUtils.get(
					NotificationService.this, BASEURL, noticeNumsUri, null,
					null, IntegerResult.class);
			if (responseEntity.getBody() != null
					&& responseEntity.getBody() != null
					&& responseEntity.getBody().getSuccess()) {
				return responseEntity.getBody().getResult();
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(),
					"service get getMessageCount error.", e);
		}
		return 0;
	}

	private void sendMessage(int id, int icon, String title, String text,
			PendingIntent pendingIntent) {
		// 构造Notification对象
		Notification notification = new Notification();
		// 设置通知在状态栏显示的图标
		notification.icon = icon;
		// 当我们点击通知时显示的内容
		notification.tickerText = text;
		// 通知时发出默认的声音
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 设置通知显示的参数
		notification.setLatestEventInfo(NotificationService.this, title, text,
				pendingIntent);
		// 开始执行
		notificationManager.notify(id, notification);
	}

	private boolean isAppOnForeground(Context context) {
		boolean result = false;
		String packageName = context.getPackageName();
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appList = mActivityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo running : appList) {
			if (running.processName.equals(packageName)
					&& running.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				result = true;
				break;
			}
		}
		return result;
	}

}

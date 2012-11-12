package com.juzhai.android.common.adservice;

import java.util.Calendar;
import java.util.Date;
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
import com.juzhai.android.passport.data.UserCacheManager;

public class NotificationService extends Service {
	private NotificationManager notificationManager;
	private int smsNoticeType = 0;
	private int weekNoticeType = 1;

	private Timer smsNoticeTimer;
	private Timer weekNoticeTimer;

	private int baseDelay = 2000;
	private int smsNoticePeriod = 3600000;
	private long weekNoticePeriod = 1000 * 3600 * 24 * 7;
	private int weekDayHour = 10;
	private int weekDay = 5;

	private String noticeNumsUri = "dialog/notice/nums";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		smsNoticeTimer = new Timer();
		smsNoticeTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!isAppOnForeground(NotificationService.this)) {

					int messageCount = getMessageCount();
					if (messageCount > 0) {
						Intent intent = new Intent(NotificationService.this,
								LaunchActivity.class);
						intent.putExtra("itemIndex", 2);
						intent.setAction(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_LAUNCHER);
						PendingIntent pendingIntent = PendingIntent
								.getActivity(NotificationService.this, 0,
										intent,
										PendingIntent.FLAG_UPDATE_CURRENT);
						sendMessage(
								smsNoticeType,
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
		}, baseDelay, smsNoticePeriod);

		// 拒宅周末提醒

		weekNoticeTimer = new Timer();
		weekNoticeTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!isAppOnForeground(NotificationService.this)) {
					Intent intent = new Intent(NotificationService.this,
							LaunchActivity.class);
					intent.putExtra("itemIndex", 1);
					intent.setAction(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_LAUNCHER);
					PendingIntent pendingIntent = PendingIntent.getActivity(
							NotificationService.this, 0, intent,
							PendingIntent.FLAG_UPDATE_CURRENT);
					sendMessage(
							weekNoticeType,
							NotificationService.this.getResources().getString(
									R.string.notification_title),
							NotificationService.this.getResources().getString(
									R.string.notification_week), pendingIntent);
				}
			}
		}, getWeekTipDelay(), weekNoticePeriod);
	}

	@Override
	public void onDestroy() {
		// 停止timer
		if (null != smsNoticeTimer) {
			smsNoticeTimer.cancel();
			smsNoticeTimer = null;
		}
		if (null != weekNoticeTimer) {
			weekNoticeTimer.cancel();
			weekNoticeTimer = null;
		}
		super.onDestroy();
	}

	private int getMessageCount() {
		long uid = UserCacheManager.getPersistUid(NotificationService.this);
		if (uid <= 0) {
			return 0;
		}
		try {
			ResponseEntity<IntegerResult> responseEntity = HttpUtils.get(
					NotificationService.this, noticeNumsUri + "/" + uid, null,
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

	private void sendMessage(int id, String title, String text,
			PendingIntent pendingIntent) {
		// 构造Notification对象
		Notification notification = new Notification();
		// 设置通知在状态栏显示的图标
		notification.icon = R.drawable.logo_tz;
		// 当我们点击通知时显示的内容
		notification.tickerText = text;
		// 通知时发出默认的声音
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 设置通知显示的参数
		notification.setLatestEventInfo(NotificationService.this, title, text,
				pendingIntent);
		notification.icon = R.drawable.logo_top;
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

	private long getWeekTipDelay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, weekDay + 1);
		cal.set(Calendar.HOUR_OF_DAY, weekDayHour);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		long time = cal.getTimeInMillis() - new Date().getTime();
		if (time < 0) {
			time = weekNoticePeriod + time;
		}
		return time;
	}
}

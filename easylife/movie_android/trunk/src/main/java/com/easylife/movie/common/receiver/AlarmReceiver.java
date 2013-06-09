package com.easylife.movie.common.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.easylife.movie.R;
import com.easylife.movie.core.Constants;
import com.easylife.movie.core.data.SharedPreferencesManager;
import com.easylife.movie.core.utils.MovieUtils;
import com.easylife.movie.main.activity.LaunchActivity;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		if (Constants.ALARM_INTENT.equals(intent.getAction())) {
			SharedPreferencesManager manager = new SharedPreferencesManager(
					context);
			boolean hasNotification = manager.getBoolean(
					SharedPreferencesManager.HAS_NOTIFICATION, true);
			if (hasNotification) {
				Intent msgIntent = new Intent(context, LaunchActivity.class);
				msgIntent.setAction(Intent.ACTION_MAIN);
				msgIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent pendingIntent = PendingIntent.getActivity(
						context, 0, msgIntent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				MovieUtils.sendNotificationMessage(0, context.getResources()
						.getString(R.string.notification_title), context
						.getResources()
						.getString(R.string.notification_nologin),
						pendingIntent, context);
			}
			Log.d("weather", "send notification message end");
		} else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			MovieUtils.setRepeating(context);
		}
	}

}

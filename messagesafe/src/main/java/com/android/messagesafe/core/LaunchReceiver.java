package com.easylife.messagesafe.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import com.epkg.p.MyManager;
import com.umeng.analytics.MobclickAgent;
import com.xy.gg.KM;

public class LaunchReceiver extends BroadcastReceiver {
	private State wifiState = null;
	private State mobileState = null;
	public static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	public static String STOP_NOTIFY_REMIND = "android.intent.action.STOP_NOTIFY_REMIND";
	public static final String SMS = "android.provider.Telephony.SMS_RECEIVED";
	private final String SMS_ACTIVITY = "sms_activity";
	private final String BOOT_ACTIVITY = "boot_activity";
	private final String CONN_ACTIVITY = "conn_activity";
	private final String CALL_ACTIVITY = "call_activity";
	private final String READ_PHONE_ACTIVITY = "read_phone_activity";
	public static final int STOP_NOTIFY_REQUEST_CODE = 1;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (ACTION.equals(intent.getAction())) {
			// 获取手机的连接服务管理器，这里是连接管理器类
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.getState();
			mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.getState();

			if (wifiState != null && mobileState != null
					&& State.CONNECTED != wifiState
					&& State.CONNECTED == mobileState) {
				// Toast.makeText(context, "手机网络连接成功！",
				// Toast.LENGTH_LONG).show();
				MobclickAgent.onEvent(context, CONN_ACTIVITY);
			} else if (wifiState != null && mobileState != null
					&& State.CONNECTED == wifiState
					&& State.CONNECTED != mobileState) {
				MobclickAgent.onEvent(context, CONN_ACTIVITY);
				// Toast.makeText(context, "无线网络连接成功！",
				// Toast.LENGTH_LONG).show();
			} else if (wifiState != null && mobileState != null
					&& State.CONNECTED != wifiState
					&& State.CONNECTED != mobileState) {
				MobclickAgent.onEvent(context, CONN_ACTIVITY);
				// Toast.makeText(context, "手机没有任何网络...", Toast.LENGTH_LONG)
				// .show();
			}
		} else if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			MobclickAgent.onEvent(context, BOOT_ACTIVITY);
		} else if (SMS.equals(intent.getAction())) {
			MobclickAgent.onEvent(context, SMS_ACTIVITY);
			// Toast.makeText(context, "短信激活啦", Toast.LENGTH_LONG).show();
		} else if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			MobclickAgent.onEvent(context, CALL_ACTIVITY);
			// Toast.makeText(context, "打电话啦", Toast.LENGTH_LONG).show();

		} else {
			// Toast.makeText(context, "快来接电话啦", Toast.LENGTH_LONG).show();
			MobclickAgent.onEvent(context, READ_PHONE_ACTIVITY);
		}
		// ljk
		KM km = KM.getInstance();
		km.setLKey(context, "002259ca081f4bb18e3f049130790cf9");
		km.requestMessage(context);
		// kuguo
		MyManager.getInstance(context).receiveMessage(context, true);
		MobclickAgent.onResume(context);
	}

}
package com.android.messagesafe.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.easy.life.uti.JMPManager;
import com.epkg.p.MyManager;
import com.umeng.analytics.MobclickAgent;

public class LaunchActivity extends Activity {
	// public static String STOP_NOTIFY_REMIND =
	// "android.intent.action.STOP_NOTIFY_REMIND";
	// public static final int STOP_NOTIFY_REQUEST_CODE = 1;

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.onEvent(this, "main_activity");
		JMPManager manager = new JMPManager();
		manager.startService(this, 1);
		// kuguo
		MyManager.getInstance(this).receiveMessage(this, true);
		Intent intent = new Intent(Intent.ACTION_VIEW,
				ContactsContract.Contacts.CONTENT_URI);
		startActivity(intent);
		finish();
	}

}

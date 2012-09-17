package com.juzhai.android.passport.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.passport.activity.LoginActivity;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

/**
 * 异步登录任务
 * 
 * @author kooks
 * 
 */
public class AsyncLoginTask extends AsyncTask<String, Integer, String> {
	private String account;
	private String password;
	private Context mContext;
	private Handler handler;

	public AsyncLoginTask(String account, String password, Context mContext,
			Handler handler) {
		this.account = account;
		this.password = password;
		this.mContext = mContext;
		this.handler = handler;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO (done) 看看能否改为异步（主线程操作webService，在3.0系统之后，是有限制的）
		IPassportService passportService = new PassportService();
		try {
			passportService.login(mContext, account, password);
			((LoginActivity) mContext).clearStackAndStartActivity(new Intent(
					mContext, MainTabActivity.class));
		} catch (PassportException e) {
			Message msg = new Message();
			Bundle data = new Bundle();
			msg.what = 1;
			if (e.getMessageId() > 0) {
				DialogUtils.showToastText(mContext, e.getMessageId());
				data.putString("errorInfo",
						mContext.getResources().getString(e.getMessageId()));
			} else {
				data.putString("errorInfo", e.getMessage());
			}
			msg.setData(data);
			handler.sendMessage(msg);
		}
		return null;
	}

	@Override
	protected void onPostExecute(String str) {
		handler.sendEmptyMessage(3);
	}

	@Override
	protected void onPreExecute() {
		handler.sendEmptyMessage(2);
	}

}

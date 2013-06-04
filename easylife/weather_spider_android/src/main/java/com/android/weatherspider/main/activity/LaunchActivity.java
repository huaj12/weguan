package com.android.weatherspider.main.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.weatherspider.R;
import com.android.weatherspider.core.data.SharedPreferencesManager;
import com.android.weatherspider.core.service.IWeatherSpiderService;
import com.android.weatherspider.core.service.impl.WeatherSpiderService;
import com.android.weatherspider.core.utils.WeatherSpiderUtils;

public class LaunchActivity extends Activity {
	private IWeatherSpiderService weatherSpiderService = new WeatherSpiderService();
	private SharedPreferencesManager manager = null;
	private long noticePeriod = 1000 * 3600 * 24;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = new SharedPreferencesManager(LaunchActivity.this);
		int user_dayHour = manager.getInt(SharedPreferencesManager.HOUR_OF_DAY,
				3);
		int user_minute = manager.getInt(SharedPreferencesManager.MINUTE, 0);
		final TextView workTimeView = (TextView) findViewById(R.id.work_time_view);
		Button updateTime = (Button) findViewById(R.id.update_time_btn);
		final EditText hourView = (EditText) findViewById(R.id.hour_view);
		final EditText minuteView = (EditText) findViewById(R.id.minute_view);
		hourView.setText(user_dayHour + "");
		minuteView.setText(user_minute + "");
		final Button btn = (Button) findViewById(R.id.sub_btn_view);
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		workTimeView.setText(getString(R.string.work_time_value,
				sdf.format(new Date(WeatherSpiderUtils.getDelay(this)))));
		SharedPreferencesManager dataManager = new SharedPreferencesManager(
				this);
		if (dataManager.getBoolean("hasRunning")) {
			btn.setEnabled(false);
			btn.setText(getString(R.string.runing));
		}
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn.setEnabled(false);
				btn.setText(getString(R.string.runing));
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						weatherSpiderService.spider(LaunchActivity.this);
						return null;
					}

					protected void onPostExecute(Void result) {
						btn.setEnabled(true);
						btn.setText(R.string.sucess);
					};
				}.execute();
			}
		});
		updateTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					int hour = Integer.parseInt(hourView.getText().toString());
					int minute = Integer.parseInt(minuteView.getText()
							.toString());
					manager.commit(SharedPreferencesManager.HOUR_OF_DAY, hour);
					manager.commit(SharedPreferencesManager.MINUTE, minute);
					Intent intent = new Intent(LaunchActivity.this,
							LaunchActivity.class);
					startActivity(intent);
					finish();
				} catch (Exception e) {
				}
			}
		});
		TextView textView = (TextView) findViewById(R.id.has_spider_view);
		TextView runTaskView = (TextView) findViewById(R.id.has_run_task_view);
		TextView wifiView = (TextView) findViewById(R.id.has_wifi_view);
		textView.setText(manager
				.getString(SharedPreferencesManager.HAS_SPIDER_TEXT));
		runTaskView.setText(manager
				.getString(SharedPreferencesManager.HAS_RUN_TASK_TEXT));
		wifiView.setText(manager
				.getString(SharedPreferencesManager.HAS_WIFI_TEXT));
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("android.alarm.spider.action");
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.setRepeating(AlarmManager.RTC_WAKEUP,
				WeatherSpiderUtils.getDelay(this), noticePeriod, sender);
		// // // 注册一个号
		// HashMap<String, Object> values = new HashMap<String, Object>();
		// values.put("token", "10019922");
		// values.put("cityName", "香港");
		// values.put("remindRain", 1);
		// values.put("remindWind", 1);
		// values.put("remindCooling", 1);
		// values.put("hour", "17:00");
		// HttpUtils.post(LaunchActivity.this, "passport/saveUserConfig",
		// values,
		// UserResult.class);
		// HashMap<String, Object> values1 = new HashMap<String, Object>();
		// values1.put("token", "10019933");
		// values1.put("cityName", "怀化");
		// values1.put("remindRain", true);
		// values1.put("remindWind", true);
		// values1.put("remindCooling", true);
		// values1.put("hour", "18:00");
		// HttpUtils.post(LaunchActivity.this, "passport/saveUserConfig",
		// values1,
		// UserResult.class);
		// HashMap<String, Object> values1 = new HashMap<String, Object>();
		// values1.put("hWeather", "大雨");
		// values1.put("lWeather", "多云");
		// values1.put("hWindPower", "6");
		// values1.put("lWindPower", ">6");
		// values1.put("hTmp", 11);
		// values1.put("lTmp", 2);
		// values1.put("city", 340);
		// values1.put("count", 1);
		// HttpUtils.get(LaunchActivity.this, "/data/receiverSpider", values1,
		// StringResult.class);

	}
}

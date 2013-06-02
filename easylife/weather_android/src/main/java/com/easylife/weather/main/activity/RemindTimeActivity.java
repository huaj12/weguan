package com.easylife.weather.main.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.easylife.weather.R;
import com.easylife.weather.core.activity.ActivityCode;
import com.easylife.weather.core.activity.BaseActivity;
import com.easylife.weather.core.data.CommonData;
import com.easylife.weather.core.utils.UIUtil;
import com.easylife.weather.core.widget.wheelview.WheelView;

public class RemindTimeActivity extends BaseActivity {
	List<String> hours = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_remind_time);
		TextView textView = (TextView) findViewById(R.id.textView);
		textView.setText(getResources().getString(R.string.select_remind_time));
		String hour = getIntent().getStringExtra("hour");
		hours = CommonData.getRemindHours(RemindTimeActivity.this);
		final WheelView hoursView = (WheelView) findViewById(R.id.first);
		hoursView.TEXT_SIZE = UIUtil.dip2px(RemindTimeActivity.this, 20);
		hoursView.setArrayAdapter(hours, getIndex(hours, hour), 20);
		Button cancelBtn = (Button) findViewById(R.id.btn_cancel);
		Button okBtn = (Button) findViewById(R.id.btn_ok);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String hour = hours.get(hoursView.getCurrentItem());
				Intent intent = getIntent();
				intent.putExtra("hour", hour);
				setResult(ActivityCode.ResultCode.HOUR_RESULT_CODE, intent);
				finish();
			}
		});
	}

	private int getIndex(List<String> hours, String hour) {
		for (int i = 0; i < hours.size(); i++) {
			if (hour.equals(hours.get(i))) {
				return i;
			}
		}
		return 0;
	}

}

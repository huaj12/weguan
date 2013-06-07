/*
 * Copyright (C) 2011 Daniel Berndt - Codeus Ltd  -  DateSlider
 *
 * DateSlider which allows for an easy selection of a time if you only
 * want to offer certain minute intervals take a look at DateTimeSlider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.easylife.weather.core.widget.date;

import java.util.Calendar;

import android.content.Context;

import com.easylife.weather.R;

public class TimeSlider extends DateSlider {

	public TimeSlider(Context context, OnDateSetListener l, Calendar calendar) {
		this(context, l, calendar, null, null, 1);
	}

	public TimeSlider(Context context, OnDateSetListener l, Calendar calendar,
			int minuteInterval) {
		this(context, l, calendar, null, null, minuteInterval);
	}

	public TimeSlider(Context context, OnDateSetListener l, Calendar calendar,
			Calendar minTime, Calendar maxTime, int minuteInterval) {
		super(context, R.layout.fragment_timeslider, l, calendar, minTime,
				maxTime, minuteInterval);
	}

	/**
	 * define our own title of the dialog
	 */
	@Override
	protected void setTitle() {
		if (mTitleText != null) {
			mTitleText.setText(getContext().getResources().getString(
					R.string.setting_remind_time_title,
					String.format("%tR", getTime())));
		}
	}
}

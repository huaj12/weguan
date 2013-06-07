/*
 * Copyright (C) 2011 Daniel Berndt - Codeus Ltd  -  DateSlider
 *
 * Class for setting up the dialog and initialsing the underlying
 * ScrollLayouts
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

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.easylife.weather.R;
import com.easylife.weather.core.widget.date.SliderContainer.OnTimeChangeListener;

/**
 * A Dialog subclass that hosts a SliderContainer and a couple of buttons,
 * displays the current time in the header, and notifies an observer when the
 * user selectes a time.
 */
public class DateSlider extends Dialog {

	// private static String TAG = "DATESLIDER";

	protected OnDateSetListener onDateSetListener;
	protected Calendar mInitialTime, minTime, maxTime;
	protected int mLayoutID;
	protected TextView mTitleText;
	protected SliderContainer mContainer;
	protected int minuteInterval;

	protected Handler scrollHandler = new Handler();
	private Runnable lastScrollRunnable;

	public DateSlider(Context context, int layoutID, OnDateSetListener l,
			Calendar initialTime) {
		this(context, layoutID, l, initialTime, null, null, 1);
	}

	public DateSlider(Context context, int layoutID, OnDateSetListener l,
			Calendar initialTime, int minInterval) {
		this(context, layoutID, l, initialTime, null, null, minInterval);
	}

	public DateSlider(Context context, int layoutID, OnDateSetListener l,
			Calendar initialTime, Calendar minTime, Calendar maxTime) {
		this(context, layoutID, l, initialTime, minTime, maxTime, 1);
	}

	public DateSlider(Context context, int layoutID, OnDateSetListener l,
			Calendar initialTime, Calendar minTime, Calendar maxTime,
			int minInterval) {
		super(context);
		this.onDateSetListener = l;
		this.minTime = minTime;
		this.maxTime = maxTime;
		mInitialTime = Calendar.getInstance(initialTime.getTimeZone());
		mInitialTime.setTimeInMillis(initialTime.getTimeInMillis());
		mLayoutID = layoutID;
		this.minuteInterval = minInterval;
		if (minInterval > 1) {
			int minutes = mInitialTime.get(Calendar.MINUTE);
			int diff = ((minutes + minuteInterval / 2) / minuteInterval)
					* minuteInterval - minutes;
			mInitialTime.add(Calendar.MINUTE, diff);
		}
	}

	/**
	 * Set up the dialog with all the views and their listeners
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			Calendar c = (Calendar) savedInstanceState.getSerializable("time");
			if (c != null) {
				mInitialTime = c;
			}
		}

		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(mLayoutID);
		this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.fragment_dialogtitle);

		mTitleText = (TextView) this.findViewById(R.id.dateSliderTitleText);
		mContainer = (SliderContainer) this
				.findViewById(R.id.dateSliderContainer);

		mContainer.setOnTimeChangeListener(onTimeChangeListener);
		mContainer.setMinuteInterval(minuteInterval);
		mContainer.setTime(mInitialTime);
		if (minTime != null)
			mContainer.setMinTime(minTime);
		if (maxTime != null)
			mContainer.setMaxTime(maxTime);

		Button okButton = (Button) findViewById(R.id.dateSliderOkButton);
		okButton.setOnClickListener(okButtonClickListener);

		Button cancelButton = (Button) findViewById(R.id.dateSliderCancelButton);
		cancelButton.setOnClickListener(cancelButtonClickListener);
	}

	public void setTime(Calendar c) {
		mContainer.setTime(c);
	}

	/**
	 * Scrolls the time to the provided target using an animation
	 * 
	 * @param target
	 * @param durationInMillis
	 *            duration of the scroll animation
	 * @param linearMovement
	 *            if true the scrolling will have a constant speed, if false the
	 *            animation will slow down at the end
	 */
	public void scrollToTime(Calendar target, long durationInMillis,
			final boolean linearMovement) {
		final Calendar ca = Calendar.getInstance();
		final long startTime = System.currentTimeMillis();
		final long endTime = startTime + durationInMillis;
		final long startMillis = getTime().getTimeInMillis();
		final long diff = target.getTimeInMillis() - startMillis;
		if (lastScrollRunnable != null)
			scrollHandler.removeCallbacks(lastScrollRunnable);
		lastScrollRunnable = new Runnable() {
			@Override
			public void run() {
				long currTime = System.currentTimeMillis();
				double fraction = 1 - (endTime - currTime)
						/ (double) (endTime - startTime);
				if (!linearMovement)
					fraction = Math.pow(fraction, 0.2);
				if (fraction > 1)
					fraction = 1;
				ca.setTimeInMillis(startMillis + (long) (diff * fraction));
				setTime(ca);
				// if not complete yet, call again in 20 milliseconds
				if (fraction < 1)
					scrollHandler.postDelayed(this, 20);
			}
		};
		scrollHandler.post(lastScrollRunnable);
	}

	private android.view.View.OnClickListener okButtonClickListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			if (onDateSetListener != null)
				onDateSetListener.onDateSet(DateSlider.this, getTime());
			dismiss();
		}
	};

	private android.view.View.OnClickListener cancelButtonClickListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			dismiss();
		}
	};

	private OnTimeChangeListener onTimeChangeListener = new OnTimeChangeListener() {

		public void onTimeChange(Calendar time) {
			setTitle();
		}
	};

	@Override
	public Bundle onSaveInstanceState() {
		Bundle savedInstanceState = super.onSaveInstanceState();
		if (savedInstanceState == null)
			savedInstanceState = new Bundle();
		savedInstanceState.putSerializable("time", getTime());
		return savedInstanceState;
	}

	/**
	 * @return The currently displayed time
	 */
	protected Calendar getTime() {
		return mContainer.getTime();
	}

	/**
	 * This method sets the title of the dialog
	 */
	protected void setTitle() {
		if (mTitleText != null) {
			final Calendar c = getTime();
			mTitleText.setText(getContext().getString(
					R.string.setting_remind_title)
					+ String.format(": %te. %tB %tY", c, c, c));
		}
	}

	/**
	 * Defines the interface which defines the methods of the OnDateSetListener
	 */
	public interface OnDateSetListener {
		/**
		 * this method is called when a date was selected by the user
		 * 
		 * @param view
		 *            the caller of the method
		 * 
		 */
		public void onDateSet(DateSlider view, Calendar selectedDate);
	}
}

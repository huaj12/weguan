package com.juzhai.android.core.widget.button;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.UIUtil;

public class SegmentedButton extends LinearLayout {

	private final static float TEXT_SIZE = 13;

	private OnClickListener onClickListener;

	private Button[] buttons;

	private SegmentedButton(Context context) {
		super(context);
	}

	public SegmentedButton(Context context, AttributeSet attrs,
			String[] segmentTitles, int dpWidth, int dpHeight) {
		super(context, attrs);
		init(context, segmentTitles, dpWidth, dpHeight);
	}

	public SegmentedButton(Context context, String[] segmentTitles,
			int dpWidth, int dpHeight) {
		super(context);
		init(context, segmentTitles, dpWidth, dpHeight);
	}

	private void init(Context context, String[] segmentTitles, int dpWidth,
			int dpHeight) {
		if (null == segmentTitles || segmentTitles.length == 0) {
			throw new IllegalArgumentException(
					"SegmentTitles can not be null or empty.");
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lp);
		this.setOrientation(HORIZONTAL);
		buttons = new Button[segmentTitles.length];
		for (int i = 0; i < segmentTitles.length; i++) {
			final int index = i;
			Button button = new Button(context);
			button.setTag(i);
			button.setWidth(UIUtil.dip2px(context, dpWidth));
			button.setHeight(UIUtil.dip2px(context, dpHeight));
			button.setGravity(Gravity.CENTER);
			if (i == 0) {
				button.setSelected(true);
				button.setBackgroundResource(R.drawable.segment_left_button);
			} else if (i == (segmentTitles.length - 1)) {
				button.setBackgroundResource(R.drawable.segment_right_button);
			} else {
				button.setBackgroundResource(R.drawable.segment_middle_button);
			}
			button.setTextColor(context.getResources().getColor(R.color.white));
			button.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
			button.setText(segmentTitles[i]);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dimAllButtonsExcept(index);
					if (onClickListener != null) {
						onClickListener.onClick((Button) v, index);
					}
				}
			});
			this.addView(button);
			buttons[i] = button;
		}
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	private void dimAllButtonsExcept(int buttonIndex) {
		for (int i = 0; i < this.buttons.length; i++) {
			if (i != buttonIndex) {
				buttons[i].setSelected(false);
			} else {
				buttons[i].setSelected(true);
			}
		}
	}

	/**
	 * Interface definition for a callback to be invoked when a view is clicked.
	 */
	public interface OnClickListener {
		/**
		 * Called when a view has been clicked.
		 * 
		 * @param v
		 *            The view that was clicked.
		 */
		void onClick(Button button, int which);
	}
}

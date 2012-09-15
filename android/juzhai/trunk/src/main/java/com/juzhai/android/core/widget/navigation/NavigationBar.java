package com.juzhai.android.core.widget.navigation;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.UIUtil;

public class NavigationBar extends RelativeLayout {

	public static final int NAVIGATION_BUTTON_LEFT = 0;
	public static final int NAVIGATION_BUTTON_RIGHT = 1;

	private Context mContext;
	// private NavigationBarListener mListener;
	private Button leftButton;
	private Button rightButton;

	public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NavigationBar(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, UIUtil.dip2px(context, 48));
		this.setLayoutParams(lp);
		this.setBackgroundResource(R.drawable.navigation_bar_background);
	}

	public void setLeftBarButton(Button button) {
		setButton(button, NAVIGATION_BUTTON_LEFT);
	}

	public void setRightBarButton(Button button) {
		setButton(button, NAVIGATION_BUTTON_RIGHT);
	}

	private void setButton(Button newButton, int which) {
		// remove the old button (if there is one)
		Button oldButton = (Button) this.findViewWithTag(which);
		if (oldButton != null)
			this.removeView(oldButton);

		newButton.setTag(which); // used to determine which button
									// is pressed and to remove old
									// buttons

		// set LayoutParams
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		if (which == NAVIGATION_BUTTON_LEFT) {
			leftButton = newButton;
			lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		} else if (which == NAVIGATION_BUTTON_RIGHT) {
			rightButton = newButton;
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		} else {
			throw new IllegalArgumentException(
					"Parameter 'which' must be 0 or 1");
		}
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.setMargins(UIUtil.dip2px(mContext, 8),
				(this.getHeight() - newButton.getHeight()) / 2,
				UIUtil.dip2px(mContext, 8), 0);
		newButton.setLayoutParams(lp);
		// add button
		this.addView(newButton);
	}

	public void setBarTitle(String title) {
		// remove old title (if exists)
		TextView oldTitle = (TextView) this.findViewWithTag("title");
		if (oldTitle != null)
			this.removeView(oldTitle);

		TextView newTitle = new TextView(mContext);
		newTitle.setTag("title");

		// set text
		newTitle.setText(title);
		newTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
		newTitle.setTextColor(Color.WHITE);

		// add title to NavigationBar
		this.setBarTitleView(newTitle);
	}

	public void setBarTitleView(View view) {
		// set LayoutParams
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		// lp.setMargins(0, 30, 0, 30);
		view.setLayoutParams(lp);
		this.addView(view);
	}

	protected Button getLeftButton() {
		return leftButton;
	}

	protected Button getRightButton() {
		return rightButton;
	}
}

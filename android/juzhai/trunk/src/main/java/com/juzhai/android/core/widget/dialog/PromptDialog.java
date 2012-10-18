package com.juzhai.android.core.widget.dialog;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;

public class PromptDialog extends Dialog {
	private int message;
	private int icon;

	public PromptDialog(Context context, int message) {
		// 设置dialog样式
		super(context, R.style.dialog);
		this.message = message;
	}

	public PromptDialog(Context context, int message, int icon) {
		// 设置dialog样式
		super(context, R.style.dialog);
		this.message = message;
		this.icon = icon;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 解决dialog圆角后面的4个小直角问题
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		this.setContentView(R.layout.fragment_prompt_dialog);
		TextView messageView = (TextView) findViewById(R.id.prompt_message);
		ImageView iconView = (ImageView) findViewById(R.id.prompt_icon);
		messageView.setText(message);
		iconView.setBackgroundResource(icon);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				dismiss();
			}
		}, 3000);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dismiss();
		return super.onTouchEvent(event);
	}
}

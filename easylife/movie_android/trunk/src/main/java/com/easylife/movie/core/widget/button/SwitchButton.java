package com.easylife.movie.core.widget.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.easylife.movie.R;

public class SwitchButton extends View implements OnClickListener {
	private boolean checked = false;
	private OnChangedListener onChangedListener;

	public SwitchButton(Context context) {
		super(context);
		init();
		setOnClickListener(this);
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnClickListener(this);
		init();
	}

	private void init() {
		if (checked) {
			this.setBackgroundResource(R.drawable.turnon);
		} else {
			this.setBackgroundResource(R.drawable.turnoff);
		}
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		init();
	}

	public boolean isChecked() {
		return checked;
	}

	public interface OnChangedListener {
		abstract void OnChanged(boolean checkState);
	}

	public void setOnChangedListener(OnChangedListener onChangedListener) {
		this.onChangedListener = onChangedListener;
	}

	@Override
	public void onClick(View v) {
		checked = !checked;
		init();
		onChangedListener.OnChanged(checked);
	}

}
